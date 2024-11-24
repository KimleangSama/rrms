package com.kkimleang.rrms.payload.response.property;

import com.kkimleang.rrms.entity.Property;
import com.kkimleang.rrms.entity.User;
import com.kkimleang.rrms.enums.property.PropertyStatus;
import com.kkimleang.rrms.enums.property.PropertyType;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PropertyOverviewResponse implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String pictureCover;
    private String addressProof;
    private String addressGMap;
    private String status;
    private String type;
    private Set<CharacteristicResponse> characteristics;

    private Boolean hasPrivilege = false;

    public static PropertyOverviewResponse fromProperty(User user, Property property) {
        PropertyOverviewResponse propertyResponse = new PropertyOverviewResponse();
        propertyResponse.setId(property.getId());
        propertyResponse.setName(property.getName());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setPictureCover(property.getPictureCover());
        propertyResponse.setAddressProof(property.getAddressProof());
        propertyResponse.setAddressGMap(property.getAddressGMap());
        if (user != null && user.getId().equals(property.getUser().getId())) {
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

    public static List<PropertyOverviewResponse> fromProperties(User user, List<Property> properties) {
        List<PropertyOverviewResponse> propertyResponses = new ArrayList<>();
        for (Property property : properties) {
            propertyResponses.add(fromProperty(user, property));
        }
        return propertyResponses;
    }

    public static PropertyOverviewResponse fromPropertyResponse(PropertyResponse propertyResponse) {
        PropertyOverviewResponse propertyOverview = new PropertyOverviewResponse();
        propertyOverview.setId(propertyResponse.getId());
        propertyOverview.setName(propertyResponse.getName());
        propertyOverview.setPictureCover(propertyResponse.getPictureCover());
        propertyOverview.setDescription(propertyResponse.getDescription());
        propertyOverview.setAddressGMap(propertyResponse.getAddressGMap());
        propertyOverview.setAddressProof(propertyResponse.getAddressProof());
        propertyOverview.setStatus(propertyResponse.getStatus());
        propertyOverview.setType(propertyResponse.getType());
        propertyOverview.setCharacteristics(propertyResponse.getCharacteristics());
        propertyOverview.setHasPrivilege(propertyResponse.getHasPrivilege());
        return propertyOverview;
    }
}
