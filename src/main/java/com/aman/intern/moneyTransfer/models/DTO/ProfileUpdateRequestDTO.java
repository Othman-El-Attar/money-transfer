package com.aman.intern.moneyTransfer.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDTO {

    private String email;
    private String country;
    private String DOB;
    private String password;

}
