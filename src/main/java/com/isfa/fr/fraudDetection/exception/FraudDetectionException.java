package com.isfa.fr.fraudDetection.exception;

import com.isfa.fr.fraudDetection.model.enums.ErrorCode;

public class FraudDetectionException extends RuntimeException{
    private final ErrorCode errorCode;

    //  errorCode + custom message are required
    public FraudDetectionException(ErrorCode errorCode, String message) {
        super(message);  // Pass the custom message to the parent exception class
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
