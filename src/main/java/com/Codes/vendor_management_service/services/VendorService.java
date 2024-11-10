package com.Codes.vendor_management_service.services;

import com.Codes.vendor_management_service.constant.ApiErrorCodes;
import com.Codes.vendor_management_service.dto.request.VendorRequest;
import com.Codes.vendor_management_service.dto.response.PaginatedResp;
import com.Codes.vendor_management_service.dto.response.VendorResponse;
import com.Codes.vendor_management_service.entity.VendorEntity;
import com.Codes.vendor_management_service.exception.NoSuchElementFoundException;
import com.Codes.vendor_management_service.repository.VendorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public VendorResponse createVendor(VendorRequest request) {
        VendorEntity vendorEntity = mapDtoToEntity(request);
        return mapEntityToDto(vendorRepository.save(vendorEntity));
    }

    public VendorResponse getVendorById(Long id) {
        Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(id);
        if (optionalVendorEntity.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_NOT_FOUND.getErrorCode(), ApiErrorCodes.VENDOR_NOT_FOUND.getErrorMessage());
        }
        return mapEntityToDto(optionalVendorEntity.get());
    }

    public VendorResponse updateVendor(Long id, VendorRequest request) {
        Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(id);
        if (optionalVendorEntity.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_NOT_FOUND.getErrorCode(), ApiErrorCodes.VENDOR_NOT_FOUND.getErrorMessage());
        }
        updateEntityFromDto(optionalVendorEntity.get(), request);
        return mapEntityToDto(vendorRepository.save(optionalVendorEntity.get()));
    }

    public PaginatedResp<VendorResponse> getAllVendors(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VendorEntity> vendorEntityPage = vendorRepository.findAll(pageable);
        List<VendorResponse> vendorResponseList = vendorEntityPage.getContent().stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return new PaginatedResp<>(vendorEntityPage.getTotalElements(), vendorEntityPage.getTotalPages(), pageable.getPageNumber(), vendorResponseList);
    }

    public void deleteVendor(Long id){
        VendorEntity entity = vendorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.VENDOR_NOT_FOUND.getErrorCode(), ApiErrorCodes.VENDOR_NOT_FOUND.getErrorMessage()));
        vendorRepository.delete(entity);
    }

    public VendorEntity mapDtoToEntity(VendorRequest request) {
        VendorEntity entity = new VendorEntity();
        entity.setVendorName(request.getVendorName());
        entity.setAddress(request.getAddress());
        entity.setServiceType(request.getServiceType());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setContactDetails(request.getContactDetails());
        return entity;
    }

    public VendorResponse mapEntityToDto(VendorEntity entity) {
        VendorResponse response = new VendorResponse();
        response.setId(entity.getId());
        response.setAddress(entity.getAddress());
        response.setServiceType(entity.getServiceType());
        response.setVendorName(entity.getVendorName());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setContactDetails(entity.getContactDetails());
        return response;
    }

    public void updateEntityFromDto(VendorEntity entity, VendorRequest request) {
        entity.setContactDetails(request.getContactDetails());
        entity.setServiceType(request.getServiceType());
        entity.setVendorName(request.getVendorName());
        entity.setAddress(request.getAddress());
        entity.setPhoneNumber(request.getPhoneNumber());
    }
}
