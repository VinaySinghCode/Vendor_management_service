package com.Codes.vendor_management_service.controller;

import com.Codes.vendor_management_service.dto.request.VendorRequest;
import com.Codes.vendor_management_service.dto.response.PaginatedResp;
import com.Codes.vendor_management_service.dto.response.VendorResponse;
import com.Codes.vendor_management_service.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor-service")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/create")
    public ResponseEntity<VendorResponse> createVendor (@RequestBody VendorRequest request){
        VendorResponse response = vendorService.createVendor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable Long id){
        VendorResponse response = vendorService.getVendorById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable Long id ,@RequestBody VendorRequest request){
        VendorResponse response = vendorService.updateVendor(id,request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResp<VendorResponse>> getAllVendors(@RequestParam (defaultValue = "0" )int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(defaultValue = "createdTime") String sortBy,
                                                                       @RequestParam(defaultValue = "desc") String sortDirection){
        PaginatedResp<VendorResponse> vendorPaginatedResp = vendorService.getAllVendors(page,size,sortBy,sortDirection);
        return new ResponseEntity<>(vendorPaginatedResp,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id){
        vendorService.deleteVendor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
