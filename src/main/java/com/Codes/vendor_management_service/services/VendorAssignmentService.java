package com.Codes.vendor_management_service.services;


import com.Codes.vendor_management_service.constant.ApiErrorCodes;
import com.Codes.vendor_management_service.dto.request.VendorAssignmentRequest;
import com.Codes.vendor_management_service.dto.response.PaginatedResp;
import com.Codes.vendor_management_service.dto.response.VendorAssignmentResponse;
import com.Codes.vendor_management_service.entity.VendorAssignmentsEntity;
import com.Codes.vendor_management_service.entity.VendorEntity;
import com.Codes.vendor_management_service.exception.NoSuchElementFoundException;
import com.Codes.vendor_management_service.repository.VendorAssignmentRepository;
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
public class VendorAssignmentService {

    private final VendorAssignmentRepository vendorAssignmentRepository;
    private final VendorRepository vendorRepository;

    public VendorAssignmentService(VendorAssignmentRepository vendorAssignmentRepository, VendorRepository vendorRepository) {
        this.vendorAssignmentRepository = vendorAssignmentRepository;
        this.vendorRepository = vendorRepository;
    }

    public VendorAssignmentResponse createVendorAssignment(VendorAssignmentRequest request) {
        Optional<VendorEntity> vendorEntityOptional = vendorRepository.findById(request.getVendorId());
        if (vendorEntityOptional.isPresent()) {
            VendorAssignmentsEntity assignmentEntity = mapDtoToEntity(request, vendorEntityOptional.get());
            return mapEntityToDto(vendorAssignmentRepository.save(assignmentEntity));
        } else {
            throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_NOT_FOUND.getErrorCode(),
                    ApiErrorCodes.VENDOR_NOT_FOUND.getErrorMessage());
        }
    }

    public VendorAssignmentResponse getVendorAssignmentById(Long id) {
        Optional<VendorAssignmentsEntity> assignmentEntityOptional = vendorAssignmentRepository.findById(id);
        if (assignmentEntityOptional.isPresent()) {
            return mapEntityToDto(assignmentEntityOptional.get());
        } else {
            throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_ASSIGNMENT_NOT_FOUND.getErrorCode(),
                    ApiErrorCodes.VENDOR_ASSIGNMENT_NOT_FOUND.getErrorMessage());
        }
    }

    public VendorAssignmentResponse updateVendorAssignment(Long id, VendorAssignmentRequest request) {
        Optional<VendorAssignmentsEntity> assignmentEntityOptional = vendorAssignmentRepository.findById(id);
        if(assignmentEntityOptional.isPresent()){
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(request.getVendorId());
            if(optionalVendorEntity.isEmpty()){
                throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_NOT_FOUND.getErrorCode(), ApiErrorCodes.VENDOR_NOT_FOUND.getErrorMessage());
            }
            updateEntityFromDto(assignmentEntityOptional.get(),request);
            return mapEntityToDto(vendorAssignmentRepository.save(assignmentEntityOptional.get()));
        }
        else {
            throw new NoSuchElementFoundException(ApiErrorCodes.INVALID_INPUT.getErrorCode(), ApiErrorCodes.INVALID_INPUT.getErrorMessage());
        }
    }

    public PaginatedResp<VendorAssignmentResponse> getAllVendorAssignments(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VendorAssignmentsEntity> assignmentPage = vendorAssignmentRepository.findAll(pageable);

        List<VendorAssignmentResponse> assignmentResponses = assignmentPage.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(assignmentPage.getTotalElements(), assignmentPage.getTotalPages(), pageable.getPageNumber(), assignmentResponses);
    }

    public void deleteVendorAssignment(Long id) {
        Optional<VendorAssignmentsEntity> assignmentEntityOptional = vendorAssignmentRepository.findById(id);
        if (assignmentEntityOptional.isPresent()) {
            vendorAssignmentRepository.delete(assignmentEntityOptional.get());
        } else {
            throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_ASSIGNMENT_NOT_FOUND.getErrorCode(),
                    ApiErrorCodes.VENDOR_ASSIGNMENT_NOT_FOUND.getErrorMessage());
        }
    }

    private VendorAssignmentsEntity mapDtoToEntity(VendorAssignmentRequest request, VendorEntity vendorEntity) {
        VendorAssignmentsEntity entity = new VendorAssignmentsEntity();
        entity.setVendorEntity(vendorEntity);
        entity.setAssignedDate(request.getAssignedDate());
        entity.setCompletionDate(request.getCompletionDate());
        return entity;
    }

    private VendorAssignmentResponse mapEntityToDto(VendorAssignmentsEntity entity) {
        VendorAssignmentResponse response = new VendorAssignmentResponse();
        response.setVendorEntity(entity.getVendorEntity());
        response.setAssignedDate(entity.getAssignedDate());
        response.setCompletionDate(entity.getCompletionDate());
        response.setId(entity.getId());
        return response;
    }

    private void updateEntityFromDto(VendorAssignmentsEntity entity, VendorAssignmentRequest request) {
        if (request.getVendorId() != null) {
            Optional<VendorEntity> newVendorOptional = vendorRepository.findById(request.getVendorId());
            if (newVendorOptional.isPresent()) {
                VendorEntity newVendor = newVendorOptional.get();
                if (!entity.getVendorEntity().getId().equals(newVendor.getId())) {
                    entity.setVendorEntity(newVendor);
                }
            } else {
                throw new NoSuchElementFoundException(ApiErrorCodes.VENDOR_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.VENDOR_NOT_FOUND.getErrorMessage());
            }
        }

        entity.setAssignedDate(request.getAssignedDate());
        entity.setCompletionDate(request.getCompletionDate());
    }

}
