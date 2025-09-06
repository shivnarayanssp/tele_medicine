package dev.shiv4u.telemedicine.services;

import dev.shiv4u.telemedicine.dtos.DoctorRequestDto;
import dev.shiv4u.telemedicine.dtos.DoctorResponseDto;
import dev.shiv4u.telemedicine.dtos.ExceptionDto;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.models.Doctor;
import dev.shiv4u.telemedicine.models.DoctorStatus;
import dev.shiv4u.telemedicine.models.Role;
import dev.shiv4u.telemedicine.models.User;
import dev.shiv4u.telemedicine.repositories.DoctorRepository;
import dev.shiv4u.telemedicine.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public DoctorService(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponseDto createDoctor(DoctorRequestDto req) throws ApiException {
        User user=userRepository.findById(req.getUserId())
                .orElseThrow(() ->   new ApiException(HttpStatus.NOT_FOUND, "User's  Not Found !"));
        if (user.getRole() != Role.DOCTOR) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User must have role DOCTOR !");
        }
        Doctor doctor=new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(req.getSpecialization());
        doctor.setLicenseNo(req.getLicenseNo());
        doctor.setStatus(DoctorStatus.OFFLINE);
        Doctor saved = doctorRepository.save(doctor);
        return DoctorResponseDto.from(saved);
    }
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(DoctorResponseDto::from)
                .toList();
    }

    public DoctorResponseDto getDoctor(UUID id) throws ApiException {
        return doctorRepository.findById(id)
                .map(DoctorResponseDto::from)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Doctor not Found !"));
    }

    public DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto req) throws ApiException {
        Doctor doctor=doctorRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Doctor Not Found !"));
        if(req.getSpecialization()!=null){
            doctor.setSpecialization(req.getSpecialization());
        }
        if(req.getLicenseNo()!=null){
            doctor.setLicenseNo(req.getLicenseNo());
        }
        return DoctorResponseDto.from(doctorRepository.save(doctor));
    }

    public void deleteDoctor(UUID id) {
        doctorRepository.deleteById(id);
    }
    public Doctor updateStatus(UUID doctorId, DoctorStatus status) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setStatus(status);
        return doctorRepository.save(doctor);
    }
}
