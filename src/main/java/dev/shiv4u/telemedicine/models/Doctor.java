package dev.shiv4u.telemedicine.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "doctors")
public class Doctor extends BaseModel {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String specialization;
    private String licenseNo;
    @Enumerated(EnumType.ORDINAL)
    private DoctorStatus status;
}
