package com.aman.intern.moneyTransfer.models.entities;

import com.aman.intern.moneyTransfer.models.Enums.CurrencyEnum;
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
    @Column(name = "id")
    private long id;

    @Column(name = "account_number")
    private long accountNumber;
    @Column(name = "balance")

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")

    private CurrencyEnum currency;
    @Column(name = "is_sub_account")

    private boolean isSubAccount;
    @Column(name = "parent_account_id")

    private long parentAccountId;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false, unique = true)
    private User user;

}

