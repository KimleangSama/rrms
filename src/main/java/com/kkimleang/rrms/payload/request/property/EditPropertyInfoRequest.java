package com.kkimleang.rrms.payload.request.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditPropertyInfoRequest {
    private String name;
    private String description;
    private String pictureCover;
    private String addressProof;
    private String village;
    private String commune;
    private String district;
    private String province;
    private String zipCode;
    private String addressGMap;
    private Double latitude;
    private Double longitude;
    private String status;
    private String type;
}
