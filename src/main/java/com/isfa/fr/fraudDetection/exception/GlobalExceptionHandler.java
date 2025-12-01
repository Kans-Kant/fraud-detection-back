package com.isfa.fr.fraudDetection.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FraudDetectionException.class)
    public ResponseEntity<String> handleFraudDetectionException(FraudDetectionException ex) {
        // Tu peux renvoyer l'erreur code et le message
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("ErrorCode: " + ex.getErrorCode() + ", Message: " + ex.getMessage());
    }
}

