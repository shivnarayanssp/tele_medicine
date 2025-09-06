package dev.shiv4u.telemedicine.repositories;

import dev.shiv4u.telemedicine.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment , UUID> {
    List<Appointment> findByPatientId(UUID patientId);
    List<Appointment> findByDoctorId(UUID doctorId);

}
