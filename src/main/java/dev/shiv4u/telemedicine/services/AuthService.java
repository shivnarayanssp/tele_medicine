package dev.shiv4u.telemedicine.services;

import dev.shiv4u.telemedicine.dtos.*;
import dev.shiv4u.telemedicine.exceptions.ApiException;
import dev.shiv4u.telemedicine.models.Session;
import dev.shiv4u.telemedicine.models.SessionStatus;
import dev.shiv4u.telemedicine.models.User;
import dev.shiv4u.telemedicine.repositories.SessionRepository;
import dev.shiv4u.telemedicine.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SecretKey secretKey;

    @Autowired
    public AuthService(UserRepository userRepository,
                       SessionRepository sessionRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        secretKey = Jwts.SIG.HS256.key().build();
    }

    public ResponseEntity<GenericUserDto> login(LoginRequestDto loginRequestDto) throws ApiException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User's Email Not Found !");
        }
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Incorrect Credentials!");
        }
        String token = jwtService.generateToken((UserDetails) user);
        Session session = new Session();
        session.setId(UUID.randomUUID());
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setCreatedAt(new Date().toInstant());
        sessionRepository.save(session);
        GenericUserDto genericUserDto = GenericUserDto.from(Optional.of(user));
        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth_token=" + token + "; HttpOnly; Path=/");
        return new ResponseEntity<>(genericUserDto, headers, HttpStatus.OK);
    }
    public void logout(LogoutRequestDto logoutRequestDto) throws ApiException {
        Optional<Session> sessionOptional = sessionRepository
                .findBytokenAndUser_id(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
        if (sessionOptional.isEmpty()) {
            throw new ApiException(HttpStatus.REQUEST_TIMEOUT, "Session are Ended !");
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    public GenericUserDto signUp(SignUpRequestDto signUpRequestDto) throws ApiException {
        User user = userRepository.findByEmail(signUpRequestDto.getEmail());
        if (user != null) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "User's Email Already Present !");
        }
        user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(signUpRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        return GenericUserDto.from(Optional.of(savedUser));
    }

    public SessionStatus validate(ValidateTokenRequestDto validateTokenRequestDto) {
        Optional<Session> sessionOptional = sessionRepository.findBytokenAndUser_id(
                validateTokenRequestDto.getToken(),
                validateTokenRequestDto.getUserId()
        );
        if (sessionOptional.isEmpty()) {
            return SessionStatus.ENDED;
        }
        Session session = sessionOptional.get();
        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return SessionStatus.ENDED;
        }
        try {
            String username = jwtService.extractUsername(validateTokenRequestDto.getToken());
            UserDetails userDetails = jwtService.loadUserByUsername(username);
            if (jwtService.validateToken(validateTokenRequestDto.getToken(), userDetails)) {
                return SessionStatus.ACTIVE;
            }
        } catch (Exception e) {
            return SessionStatus.ENDED;
        }
        return SessionStatus.ENDED;
    }
}
