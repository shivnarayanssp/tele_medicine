package dev.shiv4u.telemedicine.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name ="users")
public class User extends BaseModel{
    @Column(unique = true, nullable = false)
    private UUID patientId;
    private String name;
    private String email;
    @Column(unique = true,nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
