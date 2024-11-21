package com.kkimleang.rrms.payload.request;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
@Setter
@ToString
public class LoginRequest {
    private String email;
    private String password;
    @JsonProperty("device")
    private DeviceManagementRequest deviceManagement;
}

