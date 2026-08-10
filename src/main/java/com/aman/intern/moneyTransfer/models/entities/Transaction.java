package com.aman.intern.moneyTransfer.models.entities;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private long id;
    private long senderAccountId;
    private long receiverAccountId;

    private BigDecimal amount;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransferTypeEnum transferType;

    @Enumerated(EnumType.STRING)
    private StatusEnum transactionStatus;

}

enum StatusEnum {
    PENDING,
    FAILED,
    APPROVED
}

enum TransferTypeEnum {
    INTERNAL_SELF,INTERNAL_USER,EXTERNAL
}