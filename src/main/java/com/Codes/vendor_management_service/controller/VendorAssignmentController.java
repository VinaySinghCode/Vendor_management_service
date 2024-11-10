package com.Codes.vendor_management_service.controller;

import com.Codes.vendor_management_service.dto.request.VendorAssignmentRequest;
import com.Codes.vendor_management_service.dto.response.PaginatedResp;
import com.Codes.vendor_management_service.dto.response.VendorAssignmentResponse;
import com.Codes.vendor_management_service.services.VendorAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor-assignments")
public class VendorAssignmentController {

    private final VendorAssignmentService vendorAssignmentService;

    public VendorAssignmentController(VendorAssignmentService vendorAssignmentService) {
        this.vendorAssignmentService = vendorAssignmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<VendorAssignmentResponse> createVendorAssignment(@RequestBody VendorAssignmentRequest request) {
        VendorAssignmentResponse response = vendorAssignmentService.createVendorAssignment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorAssignmentResponse> getVendorAssignmentById(@PathVariable Long id) {
        VendorAssignmentResponse response = vendorAssignmentService.getVendorAssignmentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorAssignmentResponse> updateVendorAssignment(@PathVariable Long id, @RequestBody VendorAssignmentRequest request) {
        VendorAssignmentResponse response = vendorAssignmentService.updateVendorAssignment(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorAssignment(@PathVariable Long id) {
        vendorAssignmentService.deleteVendorAssignment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResp<VendorAssignmentResponse>> getAllVendorAssignments(@RequestParam (defaultValue = "0" )int page,
                                                                                           @RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "createdTime") String sortBy,
                                                                                           @RequestParam(defaultValue = "desc") String sortDirection) {
        PaginatedResp<VendorAssignmentResponse> response = vendorAssignmentService.getAllVendorAssignments(page,size,sortBy,sortDirection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
