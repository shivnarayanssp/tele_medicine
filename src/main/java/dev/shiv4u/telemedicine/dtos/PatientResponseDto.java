package dev.shiv4u.telemedicine.dtos;

import dev.shiv4u.telemedicine.models.Patient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Data
public class PatientResponseDto {
    private UUID id;
    private String name;
    private String email;
    private Instant dob;
    private String gender;
    private String bloodGroup;
    public static PatientResponseDto from(Patient patient){
        PatientResponseDto patientResponseDto=new PatientResponseDto();
        patientResponseDto.setId(patient.getId());
        patientResponseDto.setName(patient.getUser().getName());
        patientResponseDto.setEmail(patient.getUser().getEmail());
        patientResponseDto.setDob(patient.getDob());
        patientResponseDto.setGender(patient.getGender());
        patientResponseDto.setBloodGroup(patient.getBloodGroup());
        return patientResponseDto;
    }
}
