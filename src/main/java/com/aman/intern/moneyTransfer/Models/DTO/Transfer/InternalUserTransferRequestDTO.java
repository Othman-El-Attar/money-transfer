package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserTransferRequestDTO extends TransferRequestAbstractDTO {
    @NotBlank
    @Email
    private String receiverEmail;

    private Long senderAccountId;
}
