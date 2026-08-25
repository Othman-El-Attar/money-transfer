package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternalSelfTransferRequestDTO extends TransferRequestAbstractDTO {

    private Long receiverAccountId;
    private Long senderAccountId;
}
