package com.aman.intern.moneyTransfer.controllers;

import com.aman.intern.moneyTransfer.models.DTO.LoginRequestDTO;
import com.aman.intern.moneyTransfer.models.DTO.LoginResponseDTO;
import com.aman.intern.moneyTransfer.models.DTO.RegisterRequestDTO;
import com.aman.intern.moneyTransfer.models.DTO.UserResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.services.AuthService;
import com.aman.intern.moneyTransfer.services.JwtService;
import com.aman.intern.moneyTransfer.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final JwtService JWTSERVICE;
    private final AuthenticationManager AUTHENTICATIONMANAGER;
    private final AuthService AUTHSERVICE;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            AuthService AuthService) {

        this.AUTHENTICATIONMANAGER = authenticationManager;
        this.JWTSERVICE = jwtService;
        this.AUTHSERVICE = AuthService;
    }

    @PostMapping("/login")
    public LoginResponseDTO  login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication =
                AUTHENTICATIONMANAGER.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDTO.getEmail(),
                                loginRequestDTO.getPassword()
                        )
                );

        String token =
                JWTSERVICE.generateToken(
                        authentication.getName()
                );

        return new LoginResponseDTO(token);

    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User savedUser = AUTHSERVICE.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(savedUser));
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
