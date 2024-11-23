package com.kkimleang.rrms.service.property;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.exception.*;
import com.kkimleang.rrms.payload.request.mapper.*;
import com.kkimleang.rrms.payload.request.property.*;
import com.kkimleang.rrms.payload.response.property.*;
import com.kkimleang.rrms.repository.property.*;
import com.kkimleang.rrms.service.user.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyResponse createProperty(CustomUserDetails user, CreatePropertyRequest request) {
        try {
            if (user == null || user.getUser() == null) {
                throw new ResourceCreationException("Unauthorized to create property", request);
            }
            User currentUser = user.getUser();
            if (propertyRepository.existsByUserIdAndName(currentUser.getId(), request.getName())) {
                throw new ResourceCreationException("Property with name " + request.getName() + " already exists in your assets of ", currentUser.getUsername());
            }
            Property property = new Property();
            PropertyMapper.createPropertyFromCreatePropertyRequest(property, request);
            property.setUser(currentUser);
            property.setCreatedBy(currentUser.getId());
            property = propertyRepository.save(property);
            return PropertyResponse.fromProperty(property);
        } catch (ResourceCreationException e) {
            log.error("Failed to create property", e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to create property", e);
            throw new RuntimeException("Failed to create property", e);
        }
    }

    public List<PropertyResponse> getPagingProperties(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Property> properties = propertyRepository.findAll(pageable);
            if (properties.isEmpty()) {
                throw new ResourceNotFoundException("Property", "of size " + size + " at page " + page, properties);
            }
            List<Property> propertyList = properties.getContent();
            return PropertyResponse.fromProperties(propertyList);
        } catch (ResourceNotFoundException e) {
            log.error("Failed to get all properties", e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to get all properties", e);
            throw new RuntimeException("Failed to get all properties", e);
        }
    }
}
