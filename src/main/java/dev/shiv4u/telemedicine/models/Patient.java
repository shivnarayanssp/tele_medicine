package dev.shiv4u.telemedicine.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "patients")
public class Patient extends BaseModel{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Instant dob;
    private String gender;
    private String bloodGroup;
}
