package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.Transfer.ResponseAccountDTO;
import com.aman.intern.moneyTransfer.Models.DTO.User.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.Services.AccountService;
import com.aman.intern.moneyTransfer.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/account")
    public ResponseEntity<ResponseAccountDTO> addSubAccount(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                ACCOUNTSERVICE.addSubAccount(
                        USERSERVICE.findByEmail(userDetails.getUsername()))
        );
    }


}
