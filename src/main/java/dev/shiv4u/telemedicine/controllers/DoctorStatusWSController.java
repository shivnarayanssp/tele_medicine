package dev.shiv4u.telemedicine.controllers;

import dev.shiv4u.telemedicine.dtos.DoctorStatusEvent;
import dev.shiv4u.telemedicine.models.Doctor;
import dev.shiv4u.telemedicine.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class DoctorStatusWSController {

    private final DoctorService doctorService;
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public DoctorStatusWSController(DoctorService doctorService, SimpMessagingTemplate messagingTemplate) {
        this.doctorService = doctorService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/doctor/status")
    @PreAuthorize("hasRole('DOCTOR')")
    public void updateStatus(DoctorStatusEvent event, Principal principal) {
        Doctor updated = doctorService.updateStatus(event.getDoctorId(), event.getStatus());
        DoctorStatusEvent broadcastEvent = new DoctorStatusEvent();
        broadcastEvent.setDoctorId(updated.getId());
        broadcastEvent.setStatus(updated.getStatus());
        messagingTemplate.convertAndSend("/topic/doctor-status", broadcastEvent);
    }
}

