package com.kkimleang.rrms.payload.response.property;

import com.kkimleang.rrms.entity.*;
import java.io.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponse implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String email;
    private String contact;
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
    private Set<CharacteristicResponse> characteristics;

    public static PropertyResponse fromProperty(Property property) {
        PropertyResponse propertyResponse = new PropertyResponse();
        propertyResponse.setId(property.getId());
        propertyResponse.setName(property.getName());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setEmail(property.getEmail());
        propertyResponse.setContact(property.getContact());
        propertyResponse.setPictureCover(property.getPictureCover());
        propertyResponse.setAddressProof(property.getAddressProof());
        propertyResponse.setVillage(property.getVillage());
        propertyResponse.setCommune(property.getCommune());
        propertyResponse.setDistrict(property.getDistrict());
        propertyResponse.setProvince(property.getProvince());
        propertyResponse.setZipCode(property.getZipCode());
        propertyResponse.setAddressGMap(property.getAddressGMap());
        propertyResponse.setLatitude(property.getLatitude());
        propertyResponse.setLongitude(property.getLongitude());
        propertyResponse.setStatus(property.getPropertyStatus().name());
        propertyResponse.setType(property.getPropertyType().name());
        propertyResponse.setCharacteristics(
                CharacteristicResponse.fromCharacteristics(property.getPropertyCharacteristics())
        );
        return propertyResponse;
    }
}
