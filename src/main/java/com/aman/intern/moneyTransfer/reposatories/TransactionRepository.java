package com.aman.intern.moneyTransfer.reposatories;

import com.aman.intern.moneyTransfer.models.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByReceiverAccountIdOrSenderAccountId(
            Long receiverAccountId,
            Long senderAccountId
    );
}
