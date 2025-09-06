package dev.shiv4u.telemedicine.dtos;

import dev.shiv4u.telemedicine.models.Role;
import dev.shiv4u.telemedicine.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Data
public class GenericUserDto {
    private String name;
    private String email;
    private String password;
    private String role;
    public static GenericUserDto from(Optional<User> user) {
        if (user.isPresent()) {
            GenericUserDto genericUserDto = new GenericUserDto();
            genericUserDto.setEmail(user.get().getEmail());
            genericUserDto.setPassword(user.get().getPasswordHash());
            genericUserDto.setRole(String.valueOf(user.get().getRole()));
            genericUserDto.setName(user.get().getName());
            return genericUserDto;
        }
        return null;
    }
}
