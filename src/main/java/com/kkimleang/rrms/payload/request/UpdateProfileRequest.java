package com.kkimleang.rrms.payload.request;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class UpdateProfileRequest {
    private UUID id;
    @JsonProperty("profile_url")
    private String profileURL;
}
