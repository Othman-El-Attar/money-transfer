package com.aman.intern.moneyTransfer.Models.DTO.User;

import com.aman.intern.moneyTransfer.Models.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponseDTO {
    private String email;
    private BigDecimal balance;
    private CurrencyEnum currencyType;
}
