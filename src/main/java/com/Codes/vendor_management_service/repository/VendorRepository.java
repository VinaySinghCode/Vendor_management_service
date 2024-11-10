package com.Codes.vendor_management_service.repository;

import com.Codes.vendor_management_service.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity,Long> {

}
