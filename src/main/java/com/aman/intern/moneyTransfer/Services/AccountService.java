package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.Account;
import com.aman.intern.moneyTransfer.Models.Entities.User;
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
