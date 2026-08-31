package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.Transfer.AccountSummaryDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Transfer.RecipientAccountDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Transfer.ResponseAccountDTO;
import com.aman.intern.moneyTransfer.Models.DTO.User.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Services.AccountService;
import com.aman.intern.moneyTransfer.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService ACCOUNTSERVICE;
    private final UserService USERSERVICE;

    public AccountController(AccountService accountService, UserService userService) {
        this.ACCOUNTSERVICE = accountService;
        this.USERSERVICE = userService;

    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountSummaryDTO>> accounts(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                ACCOUNTSERVICE.getAccounts(USERSERVICE.findByEmail(userDetails.getUsername()))
        );
    }

    /// Looks up another user's accounts by email (and optionally narrows to
    /// one specific account number), so they can be picked as a favorite
    /// recipient without needing to already know their account id.
    @GetMapping("/accounts/lookup")
    public ResponseEntity<List<RecipientAccountDTO>> lookupRecipientAccounts(
            @RequestParam String email,
            @RequestParam(required = false) Long accountNumber) {

        User recipient = USERSERVICE.findByEmail(email);

        if (recipient == null) {
            throw new IllegalArgumentException("No user found with that email.");
        }

        List<RecipientAccountDTO> accounts =
                ACCOUNTSERVICE.getRecipientAccounts(recipient, accountNumber);

        if (accounts.isEmpty()) {
            throw new IllegalArgumentException(
                    accountNumber == null
                            ? "That user has no accounts."
                            : "No account with that number was found for this user."
            );
        }

        return ResponseEntity.ok(accounts);
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
