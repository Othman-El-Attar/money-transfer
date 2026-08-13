package com.aman.intern.moneyTransfer.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "favorite_recipient",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "recipientAccountId"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class FavoriteRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "recipient_account_id", nullable = false)
    private long recipientAccountId;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "add_at")
    @CreationTimestamp
    private Timestamp addedAt;
}
