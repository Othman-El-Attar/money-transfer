package com.aman.intern.moneyTransfer.Models.DTO;

import com.aman.intern.moneyTransfer.Models.Enums.TransferTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TransferRequestDTO {
    private final String recipientEmail;
    private BigDecimal amount;
    private TransferTypeEnum transferType;

}
