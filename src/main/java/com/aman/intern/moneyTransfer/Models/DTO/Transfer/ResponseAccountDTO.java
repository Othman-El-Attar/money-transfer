package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import com.aman.intern.moneyTransfer.Models.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAccountDTO {
    private String email;
    private String name;
    private CurrencyEnum currency;
}
