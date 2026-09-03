package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.User.ProfileUpdateRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.User.ProfileUpdateResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository USERREPOSITORY;
    private final PasswordEncoder PASSWORDENCODER;
    private final JwtService JWTSERVICE;


    public UserService(UserRepository userRepository, PasswordEncoder passwordencoder,JwtService jwtService) {
        this.USERREPOSITORY = userRepository;
        this.PASSWORDENCODER = passwordencoder;
        this.JWTSERVICE = jwtService;

    }


    /// Search for certain user using his email
    public User findByEmail(String email) {
        Optional<User> user = USERREPOSITORY.findByEmail(email);
        return user.orElse(null);
    }
    public ProfileUpdateResponseDTO updateProfile(
            String currentEmail,
            ProfileUpdateRequestDTO request) {

        User user = USERREPOSITORY.findByEmail(currentEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        if (request.getEmail() != null &&
                !request.getEmail().equals(currentEmail) &&
                USERREPOSITORY.findByEmail(request.getEmail()).isPresent()) {

            throw new IllegalArgumentException("User already exists");
        }

        if (request.getPassword() != null) {

            if (!passwordCheckHelper(request.getPassword())) {
                throw new IllegalArgumentException("Invalid password");
            }

            user.setPassword(
                    PASSWORDENCODER.encode(request.getPassword())
            );
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getCountry() != null) {
            user.setCountry(request.getCountry());
        }

        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }

        User updated = USERREPOSITORY.save(user);

        String token = null;

        if (!updated.getEmail().equals(currentEmail)) {
            token = JWTSERVICE.generateToken(updated.getEmail());
        }

        return new ProfileUpdateResponseDTO(updated, token);
    }



    /// helper function to validate the password
    public boolean passwordCheckHelper(String password){

        return password != null &&
                password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[$@#%^].*");
    }
}
