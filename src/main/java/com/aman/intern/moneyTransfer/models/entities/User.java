package com.aman.intern.moneyTransfer.models.entities;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name="password" ,nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "country")
    private String country;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;


}
