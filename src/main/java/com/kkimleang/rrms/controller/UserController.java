package com.kkimleang.rrms.controller;

import com.kkimleang.rrms.annotation.*;
import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.payload.*;
import com.kkimleang.rrms.payload.response.*;
import com.kkimleang.rrms.service.user.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public Response<UserResponse> getCurrentUser(@CurrentUser CustomUserDetails currentUser) {
        try {
            User user = userService.findByEmail(currentUser.getEmail());
            UserResponse userResponse = UserResponse.fromUser(user);
            log.info("User: {}", userResponse);
            return Response.<UserResponse>ok()
                    .setPayload(userResponse);
        } catch (Exception e) {
            return Response.<UserResponse>exception()
                    .setErrors(e.getMessage());
        }
    }
}
