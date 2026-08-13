package com.aman.intern.moneyTransfer.Mappers;

import com.aman.intern.moneyTransfer.models.DTO.FavoriteRecipientDTO;
import com.aman.intern.moneyTransfer.models.entities.FavoriteRecipient;
import org.springframework.stereotype.Component;

@Component
public class FavoriteRecipientMapper {
    public FavoriteRecipientDTO FavoriteRecipientToDTO(FavoriteRecipient favoriteRecipient) {

        FavoriteRecipientDTO dto = new FavoriteRecipientDTO();

        dto.setNickName(favoriteRecipient.getNickName());
        dto.setRecipientAccountId(favoriteRecipient.getRecipientAccountId());
        dto.setUserId(favoriteRecipient.getUserId());
        return dto;
    }
}
