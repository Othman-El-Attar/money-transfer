package com.aman.intern.moneyTransfer.controllers;

import com.aman.intern.moneyTransfer.models.DTO.ProfileUpdateRequestDTO;
import com.aman.intern.moneyTransfer.models.DTO.UserResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService USERSERVICE;
    public UserController(UserService userService) {
        USERSERVICE = userService;
    }



    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @RequestBody ProfileUpdateRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();

        User updatedUser =
                USERSERVICE.updateProfile(email, request);

        return ResponseEntity.ok(
                new UserResponseDTO(updatedUser)
        );
    }

}
