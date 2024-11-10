package com.Codes.vendor_management_service.dto.response;

import com.Codes.vendor_management_service.constant.ServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorResponse {
    private Long id;
    private String vendorName;
    private String contactDetails;
    private ServiceType serviceType;
    private String address;
    private Long phoneNumber;
}
