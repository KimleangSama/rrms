package com.kkimleang.rrms.service.user;

import com.fasterxml.jackson.databind.*;
import com.kkimleang.rrms.entity.User;
import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.enums.*;
import com.kkimleang.rrms.exception.*;
import com.kkimleang.rrms.payload.request.*;
import com.kkimleang.rrms.payload.response.*;
import com.kkimleang.rrms.repository.*;
import com.kkimleang.rrms.util.*;
import jakarta.servlet.http.*;
import jakarta.transaction.*;
import jakarta.validation.constraints.*;
import java.io.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;
    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;

    @Cacheable(value = "user", key = "#email")
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found."));
        return filterIsDeleted(user);
    }

    public boolean isExistingByEmail(@NotBlank @Email String email) {
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error("Cannot check existing email: {}", email);
            throw new RuntimeException("Cannot check existing user for email: " + email);
        }
    }

    public User createUser(SignUpRequest signUpRequest) {
        try {
            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(signUpRequest.getPassword());
            // Setup roles following the request, if empty role, set default role to ROLE_USER
            if (signUpRequest.getRoles().isEmpty()) {
                Role userRole = roleService.findByName(AuthRole.NORMAL.name());
                user.getRoles().add(userRole);
            } else {
                signUpRequest.getRoles().forEach(role -> {
                    try {
                        List<Role> roles = roleService.findByNames(List.of(role));
                        user.getRoles().addAll(roles);
                    } catch (ResourceNotFoundException e) {
                        log.error("Role not found: {} with message: {}", role, e.getMessage(), e);
                    }
                });
            }
            user.setProvider(AuthProvider.LOCAL);
            user.setUserStatus(AuthStatus.PENDING);
            user.setVerifyCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Cannot create user with email: {}", signUpRequest.getEmail(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            if (authentication == null) {
                throw new BadCredentialsException("Username or password is incorrect.");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail());
            }
            return new AuthResponse(
                    accessToken,
                    refreshToken,
                    user.getUsername(),
                    tokenProvider.getExpirationDateFromToken(accessToken)
            );
        } catch (Exception e) {
            String message = "Cannot authenticate user. Please check email and password.";
            log.error(message, e);
            throw new BadCredentialsException(message);
        }
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = tokenProvider.getUserEmailFromToken(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
            if (tokenProvider.isTokenValid(refreshToken, user)) {
                var accessToken = tokenProvider.createAccessToken(user);
                var authResponse = new AuthResponse(
                        accessToken,
                        refreshToken,
                        user.getUsername(),
                        tokenProvider.getExpirationDateFromToken(accessToken)
                );
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public Optional<User> findById(UUID id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
            return Optional.of(filterIsDeleted(user));
        } catch (Exception e) {
            log.error("Cannot find user with id: {}", id, e);
            throw new RuntimeException("Error while finding user with id: " + id + " with message: " + e.getMessage());
        }
    }

    @CachePut(value = "user", key = "#user.email")
    @Transactional
    public User updateUserProfile(User user) {
        try {
            Integer updated = userRepository.updateAvatar(user.getId(), user.getProfilePicture());
            if (updated == 1) {
                return user;
            } else {
                throw new RuntimeException("Cannot update user profile with id: " + user.getId());
            }
        } catch (Exception e) {
            log.error("Cannot update user with id: {}", user.getId(), e);
            throw new ResourceNotFoundException("User", "id", user.getId());
        }
    }

    @CachePut(value = "user", key = "#user.email")
    @Transactional
    public User updateAuthStatus(User user, AuthStatus status) {
        try {
            Integer success = userRepository.updateAuthStatus(user.getId(), status.name());
            if (success == 1) {
                log.info("User with id: {} is verified: {}", user.getId(), status.name());
                return user;
            } else {
                throw new RuntimeException("Cannot update user verification with id: " + user.getId());
            }
        } catch (Exception e) {
            log.error("Cannot save user with id: {}", user.getId(), e);
            throw new RuntimeException("User with id: " + user.getId() + " cannot be saved.");
        }
    }

    public User verifyActivateCode(String activateCode) {
        try {
            User user = userRepository.findByVerifyCode(activateCode)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "activate code", activateCode));
            return filterIsDeleted(user);
        } catch (Exception e) {
            log.error("Cannot verify user with activate code: {}", activateCode);
            throw new ResourceNotFoundException("User", "activate code", activateCode);
        }
    }

    private User filterIsDeleted(User user) {
        if (user.getUserStatus().equals(AuthStatus.CLOSED)) {
            throw new ResourceNotFoundException("User", "Id", user.getId());
        }
        return user;
    }
}
