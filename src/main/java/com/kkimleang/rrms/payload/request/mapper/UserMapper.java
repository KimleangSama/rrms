package com.kkimleang.rrms.payload.request.mapper;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.payload.request.user.*;
import java.time.*;

public class UserMapper {
    public static void updateUserFromEditContactRequest(User entity, EditContactRequest contactRequest) {
        if (contactRequest == null) {
            return;
        }
        if (contactRequest.getEmail() != null) {
            entity.setEmail(contactRequest.getEmail());
        }
        if (contactRequest.getPhoneNumber() != null) {
            entity.setPhoneNumber(contactRequest.getPhoneNumber());
        }
        if (contactRequest.getEmergencyContact() != null) {
            entity.setEmergencyContact(contactRequest.getEmergencyContact());
        }
        if (contactRequest.getEmergencyRelationship() != null) {
            entity.setEmergencyRelationship(contactRequest.getEmergencyRelationship());
        }
        if (contactRequest.getAddressProof() != null) {
            entity.setAddressProof(contactRequest.getAddressProof());
        }
    }

    public static void updateUserFromEditBasicRequest(User targetUser, EditBasicRequest request) {
        if (request == null) {
            return;
        }
        if (request.getFullname() != null) {
            targetUser.setFullname(request.getFullname());
        }
        if (request.getGender() != null) {
            targetUser.setGender(request.getGender());
        }
        if (request.getDateOfBirth() != null) {
            targetUser.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        }
        if (request.getProfilePicture() != null) {
            targetUser.setProfilePicture(request.getProfilePicture());
        }
        if (request.getPreferredLanguage() != null) {
            targetUser.setPreferredLanguage(request.getPreferredLanguage());
        }
        if (request.getPreferredLocation() != null) {
            targetUser.setPreferredLocation(request.getPreferredLocation());
        }
    }
}
