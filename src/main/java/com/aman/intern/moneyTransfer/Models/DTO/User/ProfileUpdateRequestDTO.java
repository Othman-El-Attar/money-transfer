package com.aman.intern.moneyTransfer.Models.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDTO {

    private String email;
    private String country;
    private LocalDate dob;
    private String password;

}
