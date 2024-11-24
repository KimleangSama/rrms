package com.kkimleang.rrms.controller.property;

import com.kkimleang.rrms.annotation.*;
import com.kkimleang.rrms.controller.GlobalControllerExceptionHandler;
import com.kkimleang.rrms.exception.*;
import com.kkimleang.rrms.payload.*;
import com.kkimleang.rrms.payload.request.property.*;
import com.kkimleang.rrms.payload.response.property.*;
import com.kkimleang.rrms.service.property.*;
import com.kkimleang.rrms.service.user.*;

import java.util.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to create property {}", e.getMessage(), e);
            return Response.<PropertyResponse>exception()
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

    @GetMapping("/{id}/overview")
    public Response<PropertyOverviewResponse> getPropertyOverView(@CurrentUser CustomUserDetails user, @PathVariable("id") UUID propertyId) {
        try {
            PropertyResponse propertyResponse = propertyService.findPropertyOverviewById(user, propertyId);
            PropertyOverviewResponse property = PropertyOverviewResponse.fromPropertyResponse(propertyResponse);
            return Response.<PropertyOverviewResponse>ok()
                    .setPayload(property);
        } catch (ResourceNotFoundException e) {
            return Response.<PropertyOverviewResponse>notFound().setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<PropertyOverviewResponse>exception().setErrors(e.getMessage());
        }
    }

    @GetMapping("/{id}/view")
    public Response<PropertyResponse> getPropertyView(@CurrentUser CustomUserDetails user, @PathVariable("id") UUID propertyId) {
        try {
            PropertyResponse property = propertyService.findPropertyById(user, propertyId);
            return Response.<PropertyResponse>ok()
                    .setPayload(property);
        } catch (ResourceNotFoundException e) {
            return Response.<PropertyResponse>notFound().setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<PropertyResponse>exception().setErrors(e.getMessage());
        }
    }

    @GetMapping("/landlord/all")
    public Response<List<PropertyOverviewResponse>> getAllLandlordProperties(
            @CurrentUser CustomUserDetails user,
            @RequestParam("landlordId") UUID landlordId
    ) {
        try {
            List<PropertyOverviewResponse> propertyResponses = propertyService.getLandlordProperties(user, landlordId);
            return Response.<List<PropertyOverviewResponse>>ok()
                    .setPayload(propertyResponses);
        } catch (ResourceNotFoundException e) {
            return Response.<List<PropertyOverviewResponse>>notFound()
                    .setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<List<PropertyOverviewResponse>>exception()
                    .setErrors(e.getMessage());
        }
    }

    @PatchMapping("/{id}/edit-contact")
    public Response<PropertyResponse> editProperty(
            @CurrentUser CustomUserDetails user,
            @PathVariable("id") UUID propertyId,
            @RequestBody EditPropertyContactRequest request
    ) {
        try {
            PropertyResponse propertyResponse = propertyService.editPropertyContact(user, propertyId, request);
            return Response.<PropertyResponse>ok()
                    .setPayload(propertyResponse);
        } catch (ResourceNotFoundException e) {
            return Response.<PropertyResponse>notFound()
                    .setErrors(e.getMessage());
        } catch (ResourceCreationException e) {
            return Response.<PropertyResponse>badRequest()
                    .setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<PropertyResponse>exception()
                    .setErrors(e.getMessage());
        }
    }

    @PatchMapping("/{id}/edit-info")
    public Response<PropertyResponse> editPropertyInfo(
            @CurrentUser CustomUserDetails user,
            @PathVariable("id") UUID propertyId,
            @RequestBody EditPropertyInfoRequest request
    ) {
        try {
            PropertyResponse propertyResponse = propertyService.editPropertyInfo(user, propertyId, request);
            return Response.<PropertyResponse>ok()
                    .setPayload(propertyResponse);
        } catch (ResourceNotFoundException e) {
            return Response.<PropertyResponse>notFound()
                    .setErrors(e.getMessage());
        } catch (ResourceCreationException e) {
            return Response.<PropertyResponse>badRequest()
                    .setErrors(e.getMessage());
        } catch (Exception e) {
            return Response.<PropertyResponse>exception()
                    .setErrors(e.getMessage());
        }
    }
}
