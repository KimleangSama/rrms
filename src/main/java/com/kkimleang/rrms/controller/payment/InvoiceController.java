package com.kkimleang.rrms.controller.payment;

import com.kkimleang.rrms.annotation.CurrentUser;
import com.kkimleang.rrms.controller.GlobalControllerServiceCall;
import com.kkimleang.rrms.payload.Response;
import com.kkimleang.rrms.payload.request.payment.CreateInvoiceRequest;
import com.kkimleang.rrms.payload.response.payment.InvoiceResponse;
import com.kkimleang.rrms.service.payment.InvoiceService;
import com.kkimleang.rrms.service.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final GlobalControllerServiceCall service;

    @GetMapping("/of-room/{roomId}")
    public Response<List<InvoiceResponse>> getInvoicesOfRoom(
            @CurrentUser CustomUserDetails user,
            @PathVariable("roomId") UUID roomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return service.executeServiceCall(() -> {
            List<InvoiceResponse> response = invoiceService.getInvoicesOfRoom(user, roomId, page, size);
            log.info("User {} fetched invoices of room {}", user.getUsername(), roomId);
            return response;
        }, "Failed to fetch invoices of room");
    }

    @GetMapping("/of-room-assignment/{roomAssignmentId}")
    public Response<List<InvoiceResponse>> getInvoicesOfRoomAssignment(
            @CurrentUser CustomUserDetails user,
            @PathVariable("roomAssignmentId") UUID roomAssignmentId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return service.executeServiceCall(() -> {
            List<InvoiceResponse> response = invoiceService.getInvoicesOfRoomAssignment(user, roomAssignmentId, page, size);
            log.info("User {} fetched invoices of room assignment {}", user.getUsername(), roomAssignmentId);
            return response;
        }, "Failed to fetch invoices of room assignment");
    }

    @GetMapping("/of-property/{propertyId}")
    public Response<List<InvoiceResponse>> getInvoicesOfProperty(
            @CurrentUser CustomUserDetails user,
            @PathVariable("propertyId") UUID propertyId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return service.executeServiceCall(() -> {
            List<InvoiceResponse> response = invoiceService.getInvoicesOfProperty(user, propertyId, page, size);
            log.info("User {} fetched invoices of property {}", user.getUsername(), propertyId);
            return response;
        }, "Failed to fetch invoices of room assignment");
    }

    @PostMapping("/create")
    public Response<InvoiceResponse> createInvoice(
            @CurrentUser CustomUserDetails user,
            @RequestBody CreateInvoiceRequest request
    ) {
        return service.executeServiceCall(() -> {
            InvoiceResponse response = invoiceService.createInvoice(user, request);
            log.info("User {} created invoice {}", user.getUsername(), response.getId());
            return response;
        }, "Failed to create invoice");
    }
}