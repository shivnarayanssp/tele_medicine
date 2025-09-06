package dev.shiv4u.telemedicine.exceptions;

import dev.shiv4u.telemedicine.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDto> handleApiException(ApiException ex) {
        return new ResponseEntity<>(
                ExceptionDto.of(ex.getStatus(), ex.getMessage()),
                ex.getStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                ExceptionDto.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

