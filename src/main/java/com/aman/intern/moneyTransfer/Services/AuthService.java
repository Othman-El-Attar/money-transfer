package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.Auth.RegisterRequestDTO;
import com.aman.intern.moneyTransfer.Models.Entities.Account;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Repositories.AccountRepository;
import com.aman.intern.moneyTransfer.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {
    private final UserRepository USERREPOSITORY;
    private final PasswordEncoder PASSWORDENCODER;
    private final AccountRepository ACCOUNTREPOSITORY;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordencoder,AccountRepository accountRepository) {
        this.USERREPOSITORY = userRepository;
        this.PASSWORDENCODER = passwordencoder;
        this.ACCOUNTREPOSITORY = accountRepository;
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

        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);
        account.setCurrency(dto.getCurrency());


        user.getAccounts().add(account);

        return USERREPOSITORY.save(user);
    }


    /// helper function to validate the password
    public boolean passwordCheckHelper(String password){

        return password != null &&
                password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[$%#@^].*");
    }

    private long generateAccountNumber() {

        Random random = new Random();

        long accountNumber;

        do {
            accountNumber = 1_000_000_000L + random.nextLong(9_000_000_000L);
        } while (ACCOUNTREPOSITORY.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

}



