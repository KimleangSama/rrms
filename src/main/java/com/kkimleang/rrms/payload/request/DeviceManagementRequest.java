package com.kkimleang.rrms.payload.request;

import java.time.*;
import lombok.*;

@Getter
@Setter
public class DeviceManagementRequest {
    private String userAgent;
    private String deviceOS;
    private String deviceVersion;
    private String location;
    private Instant loginTime;
}
