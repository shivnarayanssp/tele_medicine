package dev.shiv4u.telemedicine.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Data
public class DoctorRequestDto {
    private UUID userId;
    private String specialization;
    private String licenseNo;
}
