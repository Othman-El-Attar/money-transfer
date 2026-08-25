package com.aman.intern.moneyTransfer.Repositories;

import com.aman.intern.moneyTransfer.Models.Entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
            SELECT t
        FROM Transaction t
        WHERE t.receiverAccount.id IN :accountIds
           OR t.senderAccount.id IN :accountIds
        """)
    Page<Transaction> findAllUserTransactions(
            @Param("accountIds") List<Long> accountIds,
            Pageable pageable);
    }

