package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.models.DTO.RegisterRequestDTO;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.reposatories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository USERREPOSITORY;
    private final PasswordEncoder PASSWORDENCODER;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordencoder) {
        this.USERREPOSITORY = userRepository;
        PASSWORDENCODER = passwordencoder;
    }

    public User register(RegisterRequestDTO dto) {

        if (!passwordCheckHelper(dto.getPassword())) {
            throw new IllegalArgumentException(
                    "Password must be at least 6 characters and contain " +
                            "an uppercase letter, lowercase letter, and special character."
            );
        }

        if (USERREPOSITORY.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCountry(dto.getCountry());
        user.setDob(dto.getDob());
        user.setPassword(PASSWORDENCODER.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return USERREPOSITORY.save(user);
    }


    /// helper function to validate the password
    public boolean passwordCheckHelper(String password){

        return password != null &&
                password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[$%^].*");
    }

}



