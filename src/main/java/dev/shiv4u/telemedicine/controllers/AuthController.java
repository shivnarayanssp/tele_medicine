package dev.shiv4u.telemedicine.controllers;

import dev.shiv4u.telemedicine.dtos.*;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.models.SessionStatus;
import dev.shiv4u.telemedicine.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<GenericUserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws ApiException{
        return authService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws ApiException  {
        authService.logout(logoutRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signUp")
    public ResponseEntity<GenericUserDto> signUp(@RequestBody SignUpRequestDto request) throws ApiException  {
        GenericUserDto genericUserDto = authService.signUp(request);
        return new ResponseEntity<GenericUserDto>(genericUserDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request) {
        SessionStatus sessionStatus = authService.validate(request);
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
