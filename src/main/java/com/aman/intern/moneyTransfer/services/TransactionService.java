package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.models.DTO.TransferRequestDTO;
import com.aman.intern.moneyTransfer.models.DTO.TransferResponseDTO;
import com.aman.intern.moneyTransfer.models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.models.Enums.TransferFailureReason;
import com.aman.intern.moneyTransfer.models.entities.Account;
import com.aman.intern.moneyTransfer.models.entities.Transaction;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.reposatories.TransactionRepository;
import com.aman.intern.moneyTransfer.reposatories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository TRANSACTOINREPOSITORY;
    private final UserRepository USERREPOSITORY;
    private TransferFailureReason transferFailureReason;


    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.TRANSACTOINREPOSITORY = transactionRepository;
        USERREPOSITORY = userRepository;
    }

    public List<Transaction> getAccountTransactions(Account account) {
        return TRANSACTOINREPOSITORY.findByReceiverAccountIdOrSenderAccountId(
                account.getAccountNumber(),
                account.getAccountNumber());
    }

    public TransferResponseDTO Transfer(String senderEmail, TransferRequestDTO transferRequestDTO) {
        StatusEnum status = transferHelper(senderEmail, transferRequestDTO);
        return transferMapping(senderEmail, transferRequestDTO, status);
    }

    @Transactional
    public StatusEnum transferHelper(String senderEmail, TransferRequestDTO transferRequestDTO) {
        Optional<User> recipientUser = USERREPOSITORY.findByEmail(transferRequestDTO.getRecipientEmail());
        Optional<User> senderUser = USERREPOSITORY.findByEmail(senderEmail);


        if (recipientUser.isEmpty() || senderUser.isEmpty()) {
            transferFailureReason = TransferFailureReason.ACCOUNT_NOT_FOUND;
            return StatusEnum.FAILED;
        }

        BigDecimal amount = transferRequestDTO.getAmount();

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0
        ) {
            transferFailureReason = TransferFailureReason.INVALID_AMOUNT;
            return StatusEnum.FAILED;
        }
        if (senderUser.get().getAccount().getBalance().compareTo(amount) < 0) {
            transferFailureReason = TransferFailureReason.INSUFFICIENT_BALANCE;
            return StatusEnum.FAILED;
        }

        Transaction transaction = new Transaction();


        transaction.setAmount(amount);
        transaction.setReceiverAccountId(recipientUser.get().getAccount().getId());
        transaction.setSenderAccountId(senderUser.get().getAccount().getId());
        transaction.setTransferType(transferRequestDTO.getTransferType());
        transaction.setTransactionStatus(StatusEnum.PENDING);


        User sender = senderUser.get();
        User recipient = recipientUser.get();
        BigDecimal newSenderBalance =
                sender.getAccount().getBalance().subtract(amount);

        BigDecimal newRecipientBalance =
                recipient.getAccount().getBalance().add(amount);

        sender.getAccount().setBalance(newSenderBalance);
        recipient.getAccount().setBalance(newRecipientBalance);

        // make transaction to save

        transaction.setTransactionStatus(StatusEnum.APPROVED);
        TRANSACTOINREPOSITORY.save(transaction);

        return StatusEnum.APPROVED;
    }

    private TransferResponseDTO transferMapping(
            String senderEmail,
            TransferRequestDTO request,
            StatusEnum status) {

        TransferResponseDTO response = new TransferResponseDTO();

        response.setAmount(request.getAmount());
        response.setRecipientEmail(request.getRecipientEmail());
        response.setSenderEmail(senderEmail);
        response.setStatus(status);
        if(status ==  StatusEnum.FAILED) {
            response.setFailureReason(transferFailureReason);
        }

        return response;
    }


}
