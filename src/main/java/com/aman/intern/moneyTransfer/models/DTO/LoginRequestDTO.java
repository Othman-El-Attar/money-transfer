package com.aman.intern.moneyTransfer.models.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    String email;
    String password;
}
