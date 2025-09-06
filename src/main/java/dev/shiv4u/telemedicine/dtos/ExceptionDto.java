package dev.shiv4u.telemedicine.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class ExceptionDto {
    private int statusCode;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionDto(int statusCode, String error, String message, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ExceptionDto of(HttpStatus status, String message) {
        return new ExceptionDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
    }
}

