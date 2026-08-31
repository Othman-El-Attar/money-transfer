package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.User.ProfileUpdateRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.User.ProfileUpdateResponseDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Auth.RegisterResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Services.JwtService;
import com.aman.intern.moneyTransfer.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService USERSERVICE;
    private final JwtService JWTSERVICE;

    public UserController(UserService userService, JwtService jwtService) {
        USERSERVICE = userService;
        JWTSERVICE = jwtService;
    }

    @GetMapping("/profile")
    public ResponseEntity<RegisterResponseDTO> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                new RegisterResponseDTO(USERSERVICE.findByEmail(userDetails.getUsername()))
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileUpdateResponseDTO> updateProfile(
            @RequestBody ProfileUpdateRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String previousEmail = userDetails.getUsername();
        User updated = USERSERVICE.updateProfile(previousEmail, request);

        // The old token's subject is the old email, so it stops resolving
        // to a user the moment the email changes. Issue a new one so the
        // caller isn't silently logged out.
        boolean emailChanged = !updated.getEmail().equals(previousEmail);
        String token = emailChanged ? JWTSERVICE.generateToken(updated.getEmail()) : null;

        return ResponseEntity.ok(new ProfileUpdateResponseDTO(updated, token));
    }

}
