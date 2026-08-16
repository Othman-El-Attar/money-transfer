package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.ProfileUpdateRequestDTO;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository USERREPOSITORY;
    private final PasswordEncoder PASSWORDENCODER;

    public UserService(UserRepository userRepository, PasswordEncoder passwordencoder) {
        this.USERREPOSITORY = userRepository;
        this.PASSWORDENCODER = passwordencoder;
    }


    /// Search for certain user using his email
    public User findByEmail(String email) {
        Optional<User> user = USERREPOSITORY.findByEmail(email);
        return user.orElse(null);
    }

    public User updateProfile(
            String email,
            ProfileUpdateRequestDTO request) {

        User user = USERREPOSITORY.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        if(USERREPOSITORY.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }


        if (request.getPassword() != null) {

            if (!passwordCheckHelper(request.getPassword())) {
                throw new IllegalArgumentException(
                        "Invalid password"
                );
            }

            user.setPassword(
                    PASSWORDENCODER.encode(request.getPassword())
            );
        }
        if(request.getEmail() != null) user.setEmail(request.getEmail());
        if(request.getCountry() != null) user.setCountry(request.getCountry());
        if(request.getDob() != null) user.setDob(request.getDob());

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
