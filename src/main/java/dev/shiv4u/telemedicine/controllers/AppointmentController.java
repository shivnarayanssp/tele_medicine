package dev.shiv4u.telemedicine.controllers;

import dev.shiv4u.telemedicine.dtos.AppointmentReqDto;
import dev.shiv4u.telemedicine.dtos.AppointmentRespDto;
import dev.shiv4u.telemedicine.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    // CREATE appointment
    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentRespDto> createAppointment(@RequestBody AppointmentReqDto req) {
        AppointmentRespDto appointment = appointmentService.createAppointment(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    // LIST by patient
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentRespDto>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }

    // LIST by doctor
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentRespDto>> getAppointmentsByDoctor(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }
}

