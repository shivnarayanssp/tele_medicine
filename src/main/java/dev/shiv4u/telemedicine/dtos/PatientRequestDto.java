package dev.shiv4u.telemedicine.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Data
public class PatientRequestDto {
    private UUID userId;
    private Instant dob;
    private String gender;
    private String bloodGroup;
}
