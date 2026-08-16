package com.aman.intern.moneyTransfer.Models.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FavoriteRecipientResponseDTO {

    private long userId;
    private long recipientAccountId;
    private String nickName;
}
