package com.kkimleang.rrms.controller.property;

import com.kkimleang.rrms.annotation.*;
import com.kkimleang.rrms.exception.*;
import com.kkimleang.rrms.payload.*;
import com.kkimleang.rrms.payload.request.property.*;
import com.kkimleang.rrms.payload.response.property.*;
import com.kkimleang.rrms.service.property.*;
import com.kkimleang.rrms.service.user.*;
import java.util.*;
import lombok.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @PostMapping("/create")
    public Response<PropertyResponse> createProperty(@CurrentUser CustomUserDetails user, @RequestBody CreatePropertyRequest request) {
        try {
            PropertyResponse propertyResponse = propertyService.createProperty(user, request);
            return Response.<PropertyResponse>created()
                    .setPayload(propertyResponse);
        } catch (ResourceCreationException e) {
            return Response.<PropertyResponse>badRequest()
                    .setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<PropertyResponse>exception()
                    .setErrors(e.getMessage());
        }
    }

    @GetMapping("/all")
    public Response<List<PropertyResponse>> getAllProperties(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            List<PropertyResponse> propertyResponses = propertyService.getPagingProperties(page, size);
            return Response.<List<PropertyResponse>>ok()
                    .setPayload(propertyResponses);
        } catch (ResourceNotFoundException e) {
            return Response.<List<PropertyResponse>>notFound()
                    .setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<List<PropertyResponse>>exception()
                    .setErrors(e.getMessage());
        }
    }
}
