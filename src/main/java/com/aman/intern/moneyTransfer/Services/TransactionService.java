package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.TransferRequestDTO;
import com.aman.intern.moneyTransfer.Models.DTO.TransferResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.Account;
import com.aman.intern.moneyTransfer.Models.Entities.Transaction;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.Models.Enums.TransferFailureReason;
import com.aman.intern.moneyTransfer.Repositories.TransactionRepository;
import com.aman.intern.moneyTransfer.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository TRANSACTIONREPOSITORY;
    private final UserRepository USERREPOSITORY;
    private TransferFailureReason transferFailureReason;


    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.TRANSACTIONREPOSITORY = transactionRepository;
        this.USERREPOSITORY = userRepository;
    }

    public List<Transaction> getAccountTransactions(Account account) {
        return TRANSACTIONREPOSITORY.findByReceiverAccountIdOrSenderAccountId(
                account.getId(),
                account.getId()
        );
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
        if (senderUser.get().getAccounts().getFirst().getBalance().compareTo(amount) < 0) {
            transferFailureReason = TransferFailureReason.INSUFFICIENT_BALANCE;
            return StatusEnum.FAILED;
        }

        Transaction transaction = new Transaction();


        transaction.setAmount(amount);
        transaction.setReceiverAccountId(recipientUser.get().getAccounts().getFirst().getId());
        transaction.setSenderAccountId(senderUser.get().getAccounts().getFirst().getId());
        transaction.setTransferType(transferRequestDTO.getTransferType());
        transaction.setTransactionStatus(StatusEnum.PENDING);



        User sender = senderUser.get();
        User recipient = recipientUser.get();
        BigDecimal newSenderBalance =
                sender.getAccounts().getFirst().getBalance().subtract(amount);

        BigDecimal newRecipientBalance =
                recipient.getAccounts().getFirst().getBalance().add(amount);

        sender.getAccounts().getFirst().setBalance(newSenderBalance);
        recipient.getAccounts().getFirst().setBalance(newRecipientBalance);

        // make transaction to save

        transaction.setTransactionStatus(StatusEnum.APPROVED);
        TRANSACTIONREPOSITORY.save(transaction);

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
