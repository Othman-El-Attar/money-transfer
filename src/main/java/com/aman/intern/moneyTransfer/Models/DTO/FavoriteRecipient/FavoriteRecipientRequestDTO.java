package com.aman.intern.moneyTransfer.Models.DTO.FavoriteRecipient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRecipientRequestDTO {
    private long recipientAccountId;
    private String nickName;
}
