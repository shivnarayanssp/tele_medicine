package dev.shiv4u.telemedicine.dtos;

import dev.shiv4u.telemedicine.models.Doctor;
import dev.shiv4u.telemedicine.models.DoctorStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class DoctorResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String specialization;
    private String licenseNo;
    private DoctorStatus status;
    public static DoctorResponseDto from(Doctor doctor) {
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setName(doctor.getUser().getName());
        dto.setEmail(doctor.getUser().getEmail());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setLicenseNo(doctor.getLicenseNo());
        dto.setStatus(doctor.getStatus());
        return dto;
    }

}
