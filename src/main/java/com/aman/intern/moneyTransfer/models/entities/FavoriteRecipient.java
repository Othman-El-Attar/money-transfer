package com.aman.intern.moneyTransfer.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "favorite_recipient",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "recipientAccountId"}))@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class FavoriteRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long recipientAccountId;
    private String nickName;
    private Timestamp addedAt;
}
