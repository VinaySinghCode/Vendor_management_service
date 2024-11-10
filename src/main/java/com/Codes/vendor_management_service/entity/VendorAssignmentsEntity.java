package com.Codes.vendor_management_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "tb_vendor_assignment")
public class VendorAssignmentsEntity extends BaseEntity {



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", nullable = false)
    private VendorEntity vendorEntity;

    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;
}
