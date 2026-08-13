package com.aman.intern.moneyTransfer.models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FavoriteRecipientDTO {

    private long userId;
    private long recipientAccountId;
    private String nickName;
}
