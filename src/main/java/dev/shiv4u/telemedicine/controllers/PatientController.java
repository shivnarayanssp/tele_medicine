package dev.shiv4u.telemedicine.controllers;

import dev.shiv4u.telemedicine.dtos.ExceptionDto;
import dev.shiv4u.telemedicine.dtos.PatientResponseDto;
import dev.shiv4u.telemedicine.dtos.PatientRequestDto;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService){
        this.patientService=patientService;
    }
    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody PatientRequestDto req) throws ApiException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(req));
    }
    // READ (all)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // READ (by id)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public ResponseEntity<PatientResponseDto> getPatient(@PathVariable UUID id) throws ApiException {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    // UPDATE
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
                                                    @RequestBody PatientRequestDto req) throws ApiException {
        return ResponseEntity.ok(patientService.updatePatient(id, req));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) throws ApiException {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}

