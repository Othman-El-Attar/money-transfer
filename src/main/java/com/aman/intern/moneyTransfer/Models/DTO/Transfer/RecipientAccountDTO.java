package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import com.aman.intern.moneyTransfer.Models.Enums.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// Deliberately narrower than AccountSummaryDTO: this is returned when
/// looking up *someone else's* accounts (e.g. to save them as a favorite
/// recipient), so it must never include their balance.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipientAccountDTO {
    private Long id;
    private Long accountNumber;
    private CurrencyEnum currency;
    @JsonProperty("isSubAccount")
    private boolean isSubAccount;
}
