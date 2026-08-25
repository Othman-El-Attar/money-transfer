package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.FavoriteRecipient.FavoriteRecipientRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.FavoriteRecipient.FavoriteRecipientResponseDTO;
import com.aman.intern.moneyTransfer.Services.FavoriteRecipientService;
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
    public ResponseEntity<List<FavoriteRecipientResponseDTO>> getFavoriteRecipients(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(FAVORITERECIPIENTSERVICE.getFavoriteRecipients(userDetails));
    }


    @PostMapping("/favorites")
    public ResponseEntity<FavoriteRecipientResponseDTO> addFavoriteRecipients(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody FavoriteRecipientRequestDTO favoriteRecipientRequestDTO){

        return  ResponseEntity.ok(FAVORITERECIPIENTSERVICE.
                createFavoriteRecipient(userDetails,favoriteRecipientRequestDTO));

    }


    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<FavoriteRecipientResponseDTO> DeleteFavoriteRecipient(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(FAVORITERECIPIENTSERVICE.DeleteFavoriteRecipient(userDetails,id));

    }

}
