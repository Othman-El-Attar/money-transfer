package com.aman.intern.moneyTransfer.controllers;

import com.aman.intern.moneyTransfer.models.DTO.FavoriteRecipientDTO;
import com.aman.intern.moneyTransfer.services.FavoriteRecipientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavoriteRecipientController {

    private final FavoriteRecipientService FAVORITERECIPIENTSERVICE;

    public FavoriteRecipientController( FavoriteRecipientService favoriteRecipientService) {
        FAVORITERECIPIENTSERVICE =  favoriteRecipientService;
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteRecipientDTO>> getFavoriteRecipients(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(FAVORITERECIPIENTSERVICE.getFavoriteRecipients(userDetails));
    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoriteRecipientDTO> addFavoriteRecipients(
            @AuthenticationPrincipal UserDetails userDetails,
            FavoriteRecipientDTO favoriteRecipientDTO){

        return  ResponseEntity.ok(FAVORITERECIPIENTSERVICE.
                createFavoriteRecipient(userDetails,favoriteRecipientDTO));

    }
    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<FavoriteRecipientDTO> DeleteFavoriteRecipient(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(FAVORITERECIPIENTSERVICE.DeleteFavoriteRecipient(userDetails,id));

    }

}
