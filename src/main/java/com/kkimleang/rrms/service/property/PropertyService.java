package com.kkimleang.rrms.service.property;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.exception.*;
import com.kkimleang.rrms.payload.request.property.*;
import com.kkimleang.rrms.payload.response.property.*;
import com.kkimleang.rrms.repository.property.*;
import com.kkimleang.rrms.service.user.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyResponse createProperty(CustomUserDetails user, CreatePropertyRequest request) {
        try {
            if (user == null || user.getUser() == null) {
                throw new ResourceCreationException("Unauthorized", request);
            }
            Property property = Property.fromCreationRequest(request);
            if (propertyRepository.existsByName(property.getName())) {
                throw new ResourceCreationException("Property already exists", request);
            }
            property.setUser(user.getUser());
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
}
