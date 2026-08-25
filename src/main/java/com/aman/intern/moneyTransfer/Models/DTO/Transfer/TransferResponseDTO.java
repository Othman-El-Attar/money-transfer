package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import com.aman.intern.moneyTransfer.Models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.Models.Enums.TransferFailureReason;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDTO {
    private String senderEmail;
    private String recipientEmail;
    private StatusEnum status ;
    private BigDecimal amount;
    @Nullable
    private TransferFailureReason failureReason;

}
