package com.kkimleang.rrms.payload.request.user;

import jakarta.validation.constraints.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class SignUpRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private Set<String> roles;
}