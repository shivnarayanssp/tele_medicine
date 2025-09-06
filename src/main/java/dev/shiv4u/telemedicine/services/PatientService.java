package dev.shiv4u.telemedicine.services;

import dev.shiv4u.telemedicine.dtos.ExceptionDto;
import dev.shiv4u.telemedicine.dtos.PatientResponseDto;
import dev.shiv4u.telemedicine.dtos.PatientRequestDto;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.models.Patient;
import dev.shiv4u.telemedicine.models.Role;
import dev.shiv4u.telemedicine.models.User;
import dev.shiv4u.telemedicine.repositories.PatientRepository;
import dev.shiv4u.telemedicine.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public PatientResponseDto createPatient(PatientRequestDto req) throws ApiException {
        User user=userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"User Not Found !"));
        if(user.getRole()!= Role.PATIENT)
            throw new ApiException(HttpStatus.BAD_REQUEST,"User must have role PATIENT !");
        patientRepository.findByUserId(user.getId()).ifPresent(p -> {
            try {
                throw new ApiException(HttpStatus.BAD_REQUEST,"Patient record already exists for this user");
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        });
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setDob(req.getDob());
        patient.setGender(req.getGender());
        patient.setBloodGroup(req.getBloodGroup());
        return PatientResponseDto.from(patientRepository.save(patient));
    }

    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(PatientResponseDto::from)
                .toList();
    }

    public PatientResponseDto getPatient(UUID id) throws ApiException {
        return patientRepository.findById(id)
                .map(PatientResponseDto::from)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Patient Not Found !"));
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto req) throws ApiException {
        Patient patient=patientRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Patient Not Found !"));
        if (req.getDob()!=null)
            patient.setDob(req.getDob());
        if(req.getGender()!=null)
            patient.setGender(req.getGender());
        if(req.getBloodGroup()!=null)
            patient.setBloodGroup(req.getBloodGroup());
        return PatientResponseDto.from(patientRepository.save(patient));
    }

    public void deletePatient(UUID id) throws ApiException {
        Patient patient=patientRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Patient Not Found !"));
        patientRepository.deleteById(id);
    }
}
