package com.aman.intern.moneyTransfer.controllers;

import com.aman.intern.moneyTransfer.models.DTO.TransferRequestDTO;
import com.aman.intern.moneyTransfer.models.DTO.TransferResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.Account;
import com.aman.intern.moneyTransfer.models.entities.Transaction;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.services.TransactionService;
import com.aman.intern.moneyTransfer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
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
                        USERSERVICE.findByEmail(userDetails.getUsername()).getAccount()));
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
