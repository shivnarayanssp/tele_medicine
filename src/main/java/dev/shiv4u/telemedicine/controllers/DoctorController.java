package dev.shiv4u.telemedicine.controllers;

import dev.shiv4u.telemedicine.dtos.DoctorRequestDto;
import dev.shiv4u.telemedicine.dtos.DoctorResponseDto;
import dev.shiv4u.telemedicine.dtos.ExceptionDto;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponseDto> createDoctor(@RequestBody DoctorRequestDto req) throws ApiException {
        DoctorResponseDto created = doctorService.createDoctor(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    // READ (all)
    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // READ (by id)
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctor(@PathVariable UUID id) throws ApiException {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }
    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
            public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable UUID id,
                                                                  @RequestBody DoctorRequestDto req) throws ApiException {
        return ResponseEntity.ok(doctorService.updateDoctor(id, req));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // or allow self-delete
            public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}

