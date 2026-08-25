package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalUserTransferRequestDTO extends TransferRequestAbstractDTO {

    private String receiverName;
    private String receiverAccountNumber;
    private String bankName;
}