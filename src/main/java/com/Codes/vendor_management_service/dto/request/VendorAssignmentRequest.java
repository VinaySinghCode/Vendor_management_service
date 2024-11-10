package com.Codes.vendor_management_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VendorAssignmentRequest {

    private Long vendorId;
    private LocalDateTime assignedDate;
    private LocalDateTime completionDate;
}
