package dev.shiv4u.telemedicine.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Data
public class AppointmentReqDto {
    private UUID patientId;
    private UUID doctorId;
    private Instant timestamp;
}
