package com.Codes.vendor_management_service.dto.request;

import com.Codes.vendor_management_service.constant.ServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRequest {
    private String vendorName;
    private String contactDetails;
    private ServiceType serviceType;
    private String address;
    private Long phoneNumber;
}
