package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.Transfer.*;
import com.aman.intern.moneyTransfer.Models.Records.TransferResultRecord;
import com.aman.intern.moneyTransfer.Models.Entities.Account;
import com.aman.intern.moneyTransfer.Models.Entities.Transaction;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.Models.Enums.TransferFailureReason;
import com.aman.intern.moneyTransfer.Models.Enums.TransferTypeEnum;
import com.aman.intern.moneyTransfer.Repositories.TransactionRepository;
import com.aman.intern.moneyTransfer.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository TRANSACTIONREPOSITORY;
    private final UserRepository USERREPOSITORY;


    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.TRANSACTIONREPOSITORY = transactionRepository;
        this.USERREPOSITORY = userRepository;
    }

    public Page<TransactionResponseDTO> getUserTransactions(
            @NonNull User user,
            Pageable pageable) {

        List<Long> accountIds = user.getAccounts()
                .stream()
                .map(Account::getId)
                .toList();

        if (accountIds.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<Transaction> transactions =
                TRANSACTIONREPOSITORY.findAllUserTransactions(
                        accountIds,
                        pageable
                );

        return transactions.map(transaction ->
                new TransactionResponseDTO(
                        transaction.getSenderAccount()
                                .getUser()
                                .getEmail(),

                        transaction.getReceiverAccount()
                                .getUser()
                                .getEmail(),

                        transaction.getTransactionStatus(),

                        transaction.getAmount()
                )
        );
    }

    @Transactional
    public TransferResponseDTO transfer(
            String senderEmail,
            TransferRequestAbstractDTO request) {

        TransferResultRecord result = transferHelper(senderEmail, request);

        return transferMapping(senderEmail, request, result.status(), result.failureReason());
    }

    public TransferResultRecord transferHelper(
            String senderEmail,
            @NonNull TransferRequestAbstractDTO request) {

        return switch (request.getTransferType()) {

            case INTERNAL_SELF -> {
                if (!(request instanceof InternalSelfTransferRequestDTO selfRequest)) {
                    yield new TransferResultRecord(
                            StatusEnum.FAILED,
                            TransferFailureReason.INVALID_TRANSFER_TYPE
                    );
                }

                yield internalSelfTransfer(senderEmail, selfRequest);
            }

            case INTERNAL_USER -> {
                if (!(request instanceof InternalUserTransferRequestDTO userRequest)) {
                    yield new TransferResultRecord(
                            StatusEnum.FAILED,
                            TransferFailureReason.INVALID_TRANSFER_TYPE
                    );
                }

                yield internalUserTransfer(senderEmail, userRequest);
            }

            case EXTERNAL -> {
                if (!(request instanceof ExternalUserTransferRequestDTO externalRequest)) {
                    yield new TransferResultRecord(
                            StatusEnum.FAILED,
                            TransferFailureReason.INVALID_TRANSFER_TYPE
                    );
                }

                yield externalTransfer(senderEmail, externalRequest);
            }
        };
    }

    private TransferResultRecord internalSelfTransfer(
            String senderEmail,
            InternalSelfTransferRequestDTO request) {

        User user = USERREPOSITORY.findByEmail(senderEmail)
                .orElse(null);

        if (user == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.SENDER_USER_NOT_FOUND
        );

        Account senderAccount =
                findAccount(user, request.getSenderAccountId());

        if (senderAccount == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.SENDER_ACCOUNT_NOT_FOUND
        );

        Account receiverAccount =
                findAccount(user, request.getReceiverAccountId());

        if (receiverAccount == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.RECEIVER_ACCOUNT_NOT_FOUND
        );


        BigDecimal amount = request.getAmount();

        if (!isValidAmount(amount)) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.INVALID_AMOUNT
        );

        if (!isSufficientAmount(senderAccount, amount)) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.INSUFFICIENT_BALANCE
        );


        senderAccount.setBalance(
                senderAccount.getBalance().subtract(amount)
        );

        receiverAccount.setBalance(
                receiverAccount.getBalance().add(amount)
        );

        saveTransaction(
                senderAccount,
                receiverAccount,
                amount,
                TransferTypeEnum.INTERNAL_SELF
        );

        return new TransferResultRecord(
                StatusEnum.APPROVED, null

        );
    }

    private TransferResultRecord internalUserTransfer(
            String senderEmail,
            InternalUserTransferRequestDTO request) {

        User senderUser = USERREPOSITORY.findByEmail(senderEmail)
                .orElse(null);


        if (senderUser == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.SENDER_USER_NOT_FOUND
        );

        User receiverUser = USERREPOSITORY
                .findByEmail(request.getReceiverEmail())
                .orElse(null);

        if (receiverUser == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.RECEIVER_USER_NOT_FOUND
        );

        Account senderAccount =
                findAccount(senderUser, request.getSenderAccountId());

        if (senderAccount == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.SENDER_ACCOUNT_NOT_FOUND
        );

        Account receiverAccount = receiverUser.getAccounts()
                .stream()
                .findFirst()
                .orElse(null);

        if (receiverAccount == null) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.RECEIVER_ACCOUNT_NOT_FOUND
        );

        BigDecimal amount = request.getAmount();

        if (!isValidAmount(amount)) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.INVALID_AMOUNT
        );

        if (!isSufficientAmount(senderAccount, amount)) return new TransferResultRecord(
                StatusEnum.FAILED,
                TransferFailureReason.INSUFFICIENT_BALANCE
        );

        senderAccount.setBalance(
                senderAccount.getBalance().subtract(amount)
        );

        receiverAccount.setBalance(
                receiverAccount.getBalance().add(amount)
        );
        saveTransaction(
                senderAccount,
                receiverAccount,
                amount,
                TransferTypeEnum.INTERNAL_USER
        );

        return new TransferResultRecord(
                StatusEnum.APPROVED, null

        );
    }

    private TransferResultRecord externalTransfer(
            String senderEmail,
            ExternalUserTransferRequestDTO request) {

        // Find sender
        // Validate external account
        // Check balance
        // Call external banking/payment service
        // Save transaction

        return new TransferResultRecord(
                StatusEnum.APPROVED, null

        );
    }

    private TransferResponseDTO transferMapping(
            String senderEmail,
            TransferRequestAbstractDTO request,
            StatusEnum status,
            TransferFailureReason failureReason) {

        TransferResponseDTO response = new TransferResponseDTO();

        response.setAmount(request.getAmount());
        response.setSenderEmail(senderEmail);
        response.setStatus(status);

        switch (request) {
            case InternalSelfTransferRequestDTO selfRequest -> response.setRecipientEmail(
                    String.valueOf(selfRequest.getReceiverAccountId())
            );
            case InternalUserTransferRequestDTO userRequest -> response.setRecipientEmail(
                    userRequest.getReceiverEmail()
            );
            case ExternalUserTransferRequestDTO externalRequest -> response.setRecipientEmail(
                    externalRequest.getReceiverAccountNumber()
            );
            default -> {
            }
        }

        if (status == StatusEnum.FAILED) {
            response.setFailureReason(failureReason);
        }

        return response;
    }


    private boolean isValidAmount(BigDecimal amount) {
        return amount != null &&
                amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isSufficientAmount(Account senderAccount, BigDecimal amount) {
        return senderAccount.getBalance().compareTo(amount) >= 0;
    }

    private Account findAccount(User user, Long accountId) {

        return user.getAccounts()
                .stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    private void saveTransaction(Account senderAccount, Account receiverAccount
            , BigDecimal amount, TransferTypeEnum transferType) {
        Transaction transaction = new Transaction();


        transaction.setAmount(amount);

        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.setTransferType(transferType);
        transaction.setTransactionStatus(StatusEnum.APPROVED);
        TRANSACTIONREPOSITORY.save(transaction);
    }
}

