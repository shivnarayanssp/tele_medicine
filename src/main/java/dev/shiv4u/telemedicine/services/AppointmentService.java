package dev.shiv4u.telemedicine.services;

import dev.shiv4u.telemedicine.dtos.AppointmentReqDto;
import dev.shiv4u.telemedicine.dtos.AppointmentRespDto;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.models.Appointment;
import dev.shiv4u.telemedicine.models.ApptStatus;
import dev.shiv4u.telemedicine.models.Doctor;
import dev.shiv4u.telemedicine.models.Patient;
import dev.shiv4u.telemedicine.repositories.AppointmentRepository;
import dev.shiv4u.telemedicine.repositories.DoctorRepository;
import dev.shiv4u.telemedicine.repositories.PatientRepository;
import dev.shiv4u.telemedicine.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentRespDto createAppointment(AppointmentReqDto req) {
        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Patient not found"));

        Doctor doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setScheduledAt(req.getTimestamp());
        appointment.setApptStatus(ApptStatus.PENDING);
        return AppointmentRespDto.from(appointmentRepository.save(appointment));
    }

    public List<AppointmentRespDto> getAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(AppointmentRespDto::from)
                .toList();
    }

    public List<AppointmentRespDto> getAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(AppointmentRespDto::from)
                .toList();
    }
}
