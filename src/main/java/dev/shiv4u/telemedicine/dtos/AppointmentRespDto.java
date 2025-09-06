package dev.shiv4u.telemedicine.dtos;

import dev.shiv4u.telemedicine.models.Appointment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Data
public class AppointmentRespDto {
    private UUID id;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private String status;
    private Instant timestamp;

    public static AppointmentRespDto from(Appointment appointment) {
        AppointmentRespDto dto = new AppointmentRespDto();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getUser().getName());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getUser().getName());
        dto.setStatus(String.valueOf(appointment.getApptStatus()));
        dto.setTimestamp(appointment.getScheduledAt());
        return dto;
    }
}

