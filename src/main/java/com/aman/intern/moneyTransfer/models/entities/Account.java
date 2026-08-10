package com.aman.intern.moneyTransfer.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long accountNumber;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    private boolean isSubAccount;
    private long parentAccountId;
    @Version
    private Long version;

}

enum CurrencyEnum{
    USD,
    EUR,
}
