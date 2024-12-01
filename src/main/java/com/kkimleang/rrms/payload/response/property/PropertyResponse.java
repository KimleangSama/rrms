package com.kkimleang.rrms.payload.response.property;

import com.kkimleang.rrms.config.ModelMapperConfig;
import com.kkimleang.rrms.entity.Property;
import com.kkimleang.rrms.entity.User;
import com.kkimleang.rrms.exception.ResourceDeletionException;
import com.kkimleang.rrms.payload.response.file.FileResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Data
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
    private String propertyStatus;
    private String propertyType;
    private Set<CharacteristicResponse> characteristics;
    private Set<FileResponse> propertyPictures;
    private UUID landlordId;
    private String landlordFullname;
    private String profilePicture;
    private Boolean hasPrivilege = false;

    private static final ModelMapper modelMapper = ModelMapperConfig.modelMapper();
    private static final String DELETION_LOG = "Property {} is deleted at {}";

    public static PropertyResponse fromProperty(Property property) {
        validateProperty(property);
        return createPropertyResponse(property);
    }

    public static PropertyResponse fromProperty(User user, Property property) {
        PropertyResponse response = fromProperty(property);
        response.setHasPrivilege(user.getId().equals(property.getUser().getId()));
        return response;
    }

    public static List<PropertyResponse> fromProperties(List<Property> properties) {
        return properties.stream()
                .map(property -> {
                    try {
                        return fromProperty(property);
                    } catch (ResourceDeletionException e) {
                        log.info(DELETION_LOG, property.getId(), property.getDeletedAt());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public static List<PropertyResponse> fromProperties(User user, List<Property> properties) {
        return properties.stream()
                .map(property -> {
                    try {
                        return fromProperty(user, property);
                    } catch (ResourceDeletionException e) {
                        log.info(DELETION_LOG, property.getId(), property.getDeletedAt());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private static void validateProperty(Property property) {
        if (property.getDeletedBy() != null || property.getDeletedAt() != null) {
            throw new ResourceDeletionException("Property", property.getDeletedAt().toString());
        }
    }

    private static PropertyResponse createPropertyResponse(Property property) {
        PropertyResponse response = new PropertyResponse();
        ModelMapperConfig.modelMapper().map(property, response);
        User user = property.getUser();
        response.setLandlordId(user.getId());
        response.setLandlordFullname(user.getFullname());
        response.setProfilePicture(user.getProfilePicture());
        response.setCharacteristics(CharacteristicResponse.fromCharacteristics(property.getPropertyChars()));
        response.setPropertyPictures(FileResponse.fromPropRoomPictures(property.getPropRoomPictures()));
        return response;
    }
}