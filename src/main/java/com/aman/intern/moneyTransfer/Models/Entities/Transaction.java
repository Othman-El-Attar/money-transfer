package com.aman.intern.moneyTransfer.Models.Entities;


import com.aman.intern.moneyTransfer.Models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.Models.Enums.TransferTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @ManyToOne
    @JoinColumn(name = "sender_account_id",nullable = false)
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id",nullable = false)
    private Account receiverAccount;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private TransferTypeEnum transferType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private StatusEnum transactionStatus;

}

