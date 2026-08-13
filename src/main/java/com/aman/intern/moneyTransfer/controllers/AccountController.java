package com.aman.intern.moneyTransfer.controllers;

import com.aman.intern.moneyTransfer.models.DTO.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.services.AccountService;
import com.aman.intern.moneyTransfer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountService ACCOUNTSERVICE;
    private final UserService USERSERVICE;

    public AccountController(AccountService accountService, UserService userService) {
        this.ACCOUNTSERVICE = accountService;
        this.USERSERVICE = userService;

    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDTO> balance(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                ACCOUNTSERVICE.getBalance(USERSERVICE.findByEmail(userDetails.getUsername()))
        );

    }


}
