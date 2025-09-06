package dev.shiv4u.telemedicine.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpRequestDto {
    private String email;
    private String password;
}
