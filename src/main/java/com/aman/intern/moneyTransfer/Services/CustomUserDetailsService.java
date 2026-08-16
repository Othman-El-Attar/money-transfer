package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Repositories.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/// Class to get the user security info
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository USERREPOSITORY ;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.USERREPOSITORY = userRepository;
    }

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = USERREPOSITORY.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        ));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}
