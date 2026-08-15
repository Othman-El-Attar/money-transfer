package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.models.DTO.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.Account;
import com.aman.intern.moneyTransfer.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    public BalanceResponseDTO getBalance(User user) {
//         get user account using get method
         List<Account> account = user.getAccounts();
        return new BalanceResponseDTO(
        user.getEmail(),
        account.getFirst().getBalance(),
        account.getFirst().getCurrency());
    }



}
