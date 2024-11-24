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

    public static void updatePropertyContactFromEditPropertyContactRequest(Property property, EditPropertyContactRequest request) {
        try {
            if (request == null) {
                return;
            }
            if (request.getEmail() != null) {
                property.setEmail(request.getEmail());
            }
            if (request.getContact() != null) {
                property.setContact(request.getContact());
            }
            if (request.getWebsite() != null) {
                property.setWebsite(request.getWebsite());
            }
        } catch (Exception e) {
            property.setContact(null);
        }
    }

    public static void updatePropertyInfoFromEditPropertyInfoRequest(Property property, EditPropertyInfoRequest request) {
        try {
            if (request == null) {
                return;
            }
            if (request.getName() != null) {
                property.setName(request.getName());
            }
            if (request.getDescription() != null) {
                property.setDescription(request.getDescription());
            }
            if (request.getPictureCover() != null) {
                property.setPictureCover(request.getPictureCover());
            }
            if (request.getAddressProof() != null) {
                property.setAddressProof(request.getAddressProof());
            }
            if (request.getVillage() != null) {
                property.setVillage(request.getVillage());
            }
            if (request.getCommune() != null) {
                property.setCommune(request.getCommune());
            }
            if (request.getDistrict() != null) {
                property.setDistrict(request.getDistrict());
            }
            if (request.getProvince() != null) {
                property.setProvince(request.getProvince());
            }
            if (request.getZipCode() != null) {
                property.setZipCode(request.getZipCode());
            }
            if (request.getAddressGMap() != null) {
                property.setAddressGMap(request.getAddressGMap());
            }
            if (request.getLatitude() != null) {
                property.setLatitude(request.getLatitude());
            }
            if (request.getLongitude() != null) {
                property.setLongitude(request.getLongitude());
            }
            if (request.getStatus() != null) {
                property.setPropertyStatus(PropertyStatus.valueOf(request.getStatus()));
            }
            if (request.getType() != null) {
                property.setPropertyType(PropertyType.valueOf(request.getType()));
            }
        } catch (Exception e) {
            property.setPropertyStatus(property.getPropertyStatus());
            property.setPropertyType(property.getPropertyType());
        }
    }
}
