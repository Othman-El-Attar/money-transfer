package com.aman.intern.moneyTransfer.models.DTO;

import com.aman.intern.moneyTransfer.models.entities.User;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
public class UserResponseDTO {


    private final long id;
    private final String name;
    private final String email;
    private final String country;
    private final LocalDate dob;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.country = user.getCountry();
        this.dob = user.getDob();
    }
    // getters

}
