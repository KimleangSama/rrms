package com.kkimleang.rrms.payload.response.property;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.enums.property.*;

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
    private String website;
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

    // User Info
    private UUID landlordId;
    private String landlordFullname;
    private String profilePicture;
    private Boolean hasPrivilege = false;

    public static PropertyResponse fromProperty(Property property) {
        PropertyResponse propertyResponse = mappingProperty(property);
        try {
            propertyResponse.setStatus(property.getPropertyStatus().name());
            propertyResponse.setType(property.getPropertyType().name());
        } catch (Exception e) {
            propertyResponse.setStatus(PropertyStatus.PENDING.name());
            propertyResponse.setType(PropertyType.HOUSE.name());
        }
        propertyResponse.setCharacteristics(
                CharacteristicResponse.fromCharacteristics(property.getPropertyCharacteristics())
        );
        return propertyResponse;
    }

    public static PropertyResponse fromProperty(User user, Property property) {
        PropertyResponse propertyResponse = mappingProperty(property);
        if (user.getId().equals(property.getUser().getId())) {
            propertyResponse.setHasPrivilege(true);
        }
        try {
            propertyResponse.setStatus(property.getPropertyStatus().name());
            propertyResponse.setType(property.getPropertyType().name());
        } catch (Exception e) {
            propertyResponse.setStatus(PropertyStatus.PENDING.name());
            propertyResponse.setType(PropertyType.HOUSE.name());
        }
        propertyResponse.setCharacteristics(
                CharacteristicResponse.fromCharacteristics(property.getPropertyCharacteristics())
        );
        return propertyResponse;
    }

    public static List<PropertyResponse> fromProperties(List<Property> properties) {
        List<PropertyResponse> propertyResponses = new ArrayList<>();
        for (Property property : properties) {
            propertyResponses.add(fromProperty(property));
        }
        return propertyResponses;
    }

    private static PropertyResponse mappingProperty(Property property) {
        PropertyResponse propertyResponse = new PropertyResponse();
        propertyResponse.setId(property.getId());
        propertyResponse.setName(property.getName());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setEmail(property.getEmail());
        propertyResponse.setContact(property.getContact());
        propertyResponse.setWebsite(property.getWebsite());
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
        propertyResponse.setLandlordId(property.getUser().getId());
        propertyResponse.setLandlordFullname(property.getUser().getFullname());
        propertyResponse.setProfilePicture(property.getUser().getProfilePicture());
        return propertyResponse;
    }
}
