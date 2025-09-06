package dev.shiv4u.telemedicine.dtos;

import dev.shiv4u.telemedicine.models.DoctorStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class DoctorStatusEvent {
    private UUID doctorId;
    private DoctorStatus status;
}
