package com.kkimleang.rrms.entity;

import com.kkimleang.rrms.enums.property.*;
import com.kkimleang.rrms.payload.request.property.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.*;
import java.util.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("Properties")
@Getter
@Setter
@ToString
@Entity
@Table(name = "properties", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"picture_cover"}, name = "unq_picture_cover"),
        @UniqueConstraint(columnNames = {"address_proof"}, name = "unq_address_proof"),
        @UniqueConstraint(columnNames = {"email"}, name = "unq_email"),
        @UniqueConstraint(columnNames = {"contact"}, name = "unq_contact"),
})
public class Property extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    private String description;

    @NotNull
    @Column(name = "picture_cover", nullable = false)
    private String pictureCover;

    @NotNull
    @Column(name = "address_proof", nullable = false)
    private String addressProof;

    private String village;
    private String commune;
    private String district;
    private String province;
    private String email;
    private String contact;
    private String zipCode;
    @NotNull
    @Column(name = "address_gmap", nullable = false)
    private String addressGMap;
    private Double latitude;
    private Double longitude;

    @NotNull
    @Column(name = "property_status")
    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus = PropertyStatus.PENDING;

    @Column(name = "penalized_reason")
    private String penalizedReason;

    @NotNull
    @Column(name = "property_type")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType = PropertyType.HOUSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "properties_characteristics",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"property_id", "characteristic_id"}, name = "unq_property_characteristic")}
    )
    private Set<PropertyCharacteristic> propertyCharacteristics = new HashSet<>();

    public static Property fromCreationRequest(CreatePropertyRequest request) {
        Property property = new Property();
        property.setName(request.getName());
        property.setEmail(request.getEmail());
        property.setContact(request.getContact());
        property.setDescription(request.getDescription());
        property.setPictureCover(request.getPictureCover());
        property.setAddressProof(request.getAddressProof());
        property.setVillage(request.getVillage());
        property.setCommune(request.getCommune());
        property.setDistrict(request.getDistrict());
        property.setProvince(request.getProvince());
        property.setZipCode(request.getZipCode());
        property.setAddressGMap(request.getAddressGMap());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setPropertyStatus(PropertyStatus.valueOf(request.getStatus()));
        property.setPropertyType(PropertyType.valueOf(request.getType()));
        return property;
    }
}
