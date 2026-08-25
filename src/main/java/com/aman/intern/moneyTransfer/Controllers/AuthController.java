package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.Auth.LoginRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Auth.LoginResponseDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Auth.RegisterRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Auth.RegisterResponseDTO;
import com.aman.intern.moneyTransfer.Services.AuthService;
import com.aman.intern.moneyTransfer.Services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<RegisterResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponseDTO(AUTHSERVICE.register(registerRequestDTO)));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();

        return ResponseEntity.ok(
                "User " + email + " logged out successfully"
        );
    }
}
