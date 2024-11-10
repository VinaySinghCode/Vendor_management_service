package com.Codes.vendor_management_service.entity;

import com.Codes.vendor_management_service.constant.ServiceType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tb_vendors")
public class VendorEntity extends BaseEntity{

    private String vendorName;
    private String contactDetails;
    private ServiceType serviceType;
    private String address;
    private Long phoneNumber;

}
