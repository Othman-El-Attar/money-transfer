package com.aman.intern.moneyTransfer.Models.DTO;

import com.aman.intern.moneyTransfer.Models.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String name;
    private String password;
    private String email;
    private String country;
    private LocalDate dob;
    private CurrencyEnum currency;


}
