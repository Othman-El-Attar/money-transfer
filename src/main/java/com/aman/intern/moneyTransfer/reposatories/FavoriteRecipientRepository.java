package com.aman.intern.moneyTransfer.reposatories;

import com.aman.intern.moneyTransfer.models.DTO.FavoriteRecipientDTO;
import com.aman.intern.moneyTransfer.models.entities.FavoriteRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient, Long> {
    List<FavoriteRecipient> findByUserId(Long userId);
}
