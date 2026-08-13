package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.models.DTO.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.Account;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.reposatories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository ACCOUNTREPOSITORY;

    public AccountService(AccountRepository accountRepository) {
        this.ACCOUNTREPOSITORY = accountRepository;
    }

    public Account findByAccountNumber(Long accountNumber) {
        return ACCOUNTREPOSITORY.findByAccountNumber(accountNumber);
    }

    public BalanceResponseDTO getBalance(User user) {
//         get user account using get method
         Account account = user.getAccount();
        return new BalanceResponseDTO(
        user.getEmail(),
        account.getBalance(),
        account.getCurrency());
    }



}
