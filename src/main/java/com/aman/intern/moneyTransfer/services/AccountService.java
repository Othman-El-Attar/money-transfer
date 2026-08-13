package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.models.DTO.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.Account;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.reposatories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository ACCOUNTREPOSITORY;

    public AccountService(AccountRepository accountRepository) {
        this.ACCOUNTREPOSITORY = accountRepository;
    }

    public BalanceResponseDTO getBalance(User user) {
//         get user account using get method
         List<Account> account = user.getAccount();
        return new BalanceResponseDTO(
        user.getEmail(),
        account.getFirst().getBalance(),
        account.getFirst().getCurrency());
    }



}
