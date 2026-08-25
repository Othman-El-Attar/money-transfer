package com.aman.intern.moneyTransfer.Controllers;

import com.aman.intern.moneyTransfer.Models.DTO.Transfer.TransactionResponseDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Transfer.TransferRequestAbstractDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Transfer.TransferResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.Transaction;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Services.TransactionService;
import com.aman.intern.moneyTransfer.Services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactions(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {

        User user = USERSERVICE.findByEmail(userDetails.getUsername());

        return ResponseEntity.ok(
                TRANSACTIONSERVICE.getUserTransactions(user, pageable)
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> transferTransaction(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransferRequestAbstractDTO transferRequestAbstractDTO
    ){
        return ResponseEntity.ok(
                TRANSACTIONSERVICE.transfer(userDetails.getUsername(), transferRequestAbstractDTO));
    }
}
