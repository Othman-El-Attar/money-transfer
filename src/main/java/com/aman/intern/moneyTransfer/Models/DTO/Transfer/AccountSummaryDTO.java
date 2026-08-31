package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import com.aman.intern.moneyTransfer.Models.Enums.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryDTO {
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;
    private CurrencyEnum currency;
    @JsonProperty("isSubAccount")
    private boolean isSubAccount;
}
