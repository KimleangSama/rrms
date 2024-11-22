package com.kkimleang.rrms.payload.response.user;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.enums.user.*;
import java.io.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private UUID id;
    private String fullname;
    private String username;
    private String email;
    private String gender;
    private String phoneNumber;
    private String profilePicture;
    private AuthProvider provider;
    private Set<RoleResponse> roles;
    private AuthStatus userStatus;
    private Instant lastLoginAt;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setFullname(user.getFullname());
        userResponse.setGender(user.getGender().name());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setProfilePicture(user.getProfilePicture());
        userResponse.setProvider(user.getProvider());
        userResponse.setRoles(RoleResponse.fromRoles(user.getRoles(), false));
        userResponse.setUserStatus(user.getUserStatus());
        userResponse.setLastLoginAt(user.getLastLoginAt());
        return userResponse;
    }

    public static List<UserResponse> fromUsers(List<User> users) {
        return users.stream()
                .map(UserResponse::fromUser)
                .toList();
    }
}
