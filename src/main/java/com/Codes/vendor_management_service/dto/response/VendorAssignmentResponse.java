package com.Codes.vendor_management_service.dto.response;

import com.Codes.vendor_management_service.entity.VendorEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VendorAssignmentResponse {

    private Long id;
    private VendorEntity vendorEntity;
    private LocalDateTime assignedDate;
    private LocalDateTime completionDate;
}
