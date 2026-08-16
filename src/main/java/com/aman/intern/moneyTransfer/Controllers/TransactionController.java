package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.TransferRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.TransferResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.Transaction;
import com.aman.intern.moneyTransfer.Services.TransactionService;
import com.aman.intern.moneyTransfer.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService TRANSACTIONSERVICE;

    private final UserService USERSERVICE;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.TRANSACTIONSERVICE = transactionService;
        this.USERSERVICE = userService;
    }


    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                TRANSACTIONSERVICE.getAccountTransactions(
                        USERSERVICE.findByEmail(userDetails.getUsername()).getAccounts().getFirst()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transferTransaction(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransferRequestDTO transferRequestDTO
    ){

        return ResponseEntity.ok(
                TRANSACTIONSERVICE.Transfer(userDetails.getUsername() ,transferRequestDTO));
    }
}
