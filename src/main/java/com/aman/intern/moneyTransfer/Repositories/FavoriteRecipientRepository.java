package com.aman.intern.moneyTransfer.Repositories;

import com.aman.intern.moneyTransfer.Models.Entities.FavoriteRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient, Long> {
    List<FavoriteRecipient> findByUserId(Long userId);
}
