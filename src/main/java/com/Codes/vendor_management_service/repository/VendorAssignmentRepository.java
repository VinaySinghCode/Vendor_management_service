package com.Codes.vendor_management_service.repository;

import com.Codes.vendor_management_service.entity.VendorAssignmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorAssignmentRepository extends JpaRepository<VendorAssignmentsEntity,Long> {
}
