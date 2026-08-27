package com.aman.intern.moneyTransfer.Models.Entities;

import com.aman.intern.moneyTransfer.Models.Enums.CurrencyEnum;
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
    private Long id;

    @Column( name = "account_number", nullable = false, unique = true)
    private Long accountNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency",nullable = false)
    private CurrencyEnum currency;

    @Column(name = "is_sub_account")
    private boolean isSubAccount;

    @Column(name = "parent_account_id")
    private long parentAccountId;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}

