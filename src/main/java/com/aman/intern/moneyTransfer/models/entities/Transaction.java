package com.aman.intern.moneyTransfer.models.entities;


import com.aman.intern.moneyTransfer.models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.models.Enums.TransferTypeEnum;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.math.BigDecimal;

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
    @Column(name = "sender_account_id")
        private long senderAccountId;
    @Column(name = "receiver_account_id")
    private long receiverAccountId;

    @Column(name = "amount")
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

