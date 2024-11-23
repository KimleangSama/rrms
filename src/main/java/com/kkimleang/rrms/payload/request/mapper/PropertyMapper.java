package com.kkimleang.rrms.payload.request.mapper;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.enums.property.*;
import com.kkimleang.rrms.payload.request.property.*;

public class PropertyMapper {
    public static void createPropertyFromCreatePropertyRequest(Property property, CreatePropertyRequest request) {
        try {
            if (request == null) {
                return;
            }
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
        } catch (Exception e) {
            property.setPropertyStatus(PropertyStatus.PENDING);
            property.setPropertyType(PropertyType.HOUSE);
        }
    }
}
