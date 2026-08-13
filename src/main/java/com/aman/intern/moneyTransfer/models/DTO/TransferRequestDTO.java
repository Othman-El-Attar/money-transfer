package com.aman.intern.moneyTransfer.models.DTO;

import com.aman.intern.moneyTransfer.models.Enums.TransferTypeEnum;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
