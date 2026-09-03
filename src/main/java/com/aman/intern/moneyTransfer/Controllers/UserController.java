package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.User.ProfileUpdateRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.User.ProfileUpdateResponseDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Auth.RegisterResponseDTO;
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

    public UserController(UserService userService, JwtService jwtService) {
        USERSERVICE = userService;
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

        ProfileUpdateResponseDTO response =
                USERSERVICE.updateProfile(userDetails.getUsername(), request);

        return ResponseEntity.ok(response);
    }


}
