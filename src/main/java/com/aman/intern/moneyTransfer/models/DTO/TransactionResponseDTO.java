package com.aman.intern.moneyTransfer.models.DTO;

import com.aman.intern.moneyTransfer.models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.models.Enums.TransferTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {

    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransferTypeEnum transferType;
    private StatusEnum transactionStatus;
    private String receiverEmail;

}
