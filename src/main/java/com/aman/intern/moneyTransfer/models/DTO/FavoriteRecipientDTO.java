package com.aman.intern.moneyTransfer.models.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FavoriteRecipientDTO {

    private long userId;
    private long recipientAccountId;
    private String nickName;
}
