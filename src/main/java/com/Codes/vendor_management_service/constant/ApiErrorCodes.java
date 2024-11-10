package com.Codes.vendor_management_service.constant;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public enum ApiErrorCodes implements Error{
    SUCCESS(40001, "Success"),
    NOT_FOUND(40002, "not found"),
    ALREADY_EXIST(40003, "already exist"),
    INVALID_INPUT(40004, "Invalid request input"),
    INVALID_USERNAME_OR_PASSWORD(40005,"invalid usename or password"),
    USER_NOT_FOUND(40006, "User not found"),
    VENDOR_NOT_FOUND(40007,"Vendor not found"),
    VENDOR_ASSIGNMENT_NOT_FOUND(40008,"VENDOR_ASSIGNMENT_NOT_FOUND"),
    MAINTENANCE_REQUEST_NOT_FOUND(40009,"maintenance request not found");
    private int errorCode;
    private String errorMessage;
    private HttpStatus status;
    private  String message;
    private LocalDateTime timestamp;


    ApiErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}

