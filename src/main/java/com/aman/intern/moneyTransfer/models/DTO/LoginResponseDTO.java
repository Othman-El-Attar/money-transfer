package com.aman.intern.moneyTransfer.models.DTO;
import lombok.*;

@Getter

public class LoginResponseDTO {
    private final String token ;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
