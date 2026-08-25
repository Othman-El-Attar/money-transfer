package com.aman.intern.moneyTransfer.Mappers;

import com.aman.intern.moneyTransfer.Models.DTO.FavoriteRecipient.FavoriteRecipientResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.FavoriteRecipient;
import org.springframework.stereotype.Component;

@Component
public class FavoriteRecipientMapper {
    public  FavoriteRecipientResponseDTO FavoriteRecipientToDTO(FavoriteRecipient favoriteRecipient) {

        FavoriteRecipientResponseDTO dto = new FavoriteRecipientResponseDTO();

        dto.setNickName(favoriteRecipient.getNickName());
        dto.setRecipientAccountId(favoriteRecipient.getRecipientAccountId());
        dto.setUserId(favoriteRecipient.getUser().getId());
        return dto;
    }
}
