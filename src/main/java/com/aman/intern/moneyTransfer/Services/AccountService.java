package com.aman.intern.moneyTransfer.Services;

import com.aman.intern.moneyTransfer.Models.DTO.User.BalanceResponseDTO;
import com.aman.intern.moneyTransfer.Models.DTO.Transfer.ResponseAccountDTO;
import com.aman.intern.moneyTransfer.Models.Entities.Account;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import com.aman.intern.moneyTransfer.Repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    private final AccountRepository ACCOUNTREPOSITORY;

    public AccountService(AccountRepository ACCOUNTREPOSITORY) {
        this.ACCOUNTREPOSITORY = ACCOUNTREPOSITORY;
    }

    public BalanceResponseDTO getBalance(User user) {
//         get user account using get method
         List<Account> accounts = user.getAccounts();
        return new BalanceResponseDTO(
        user.getEmail(),
        accounts.getFirst().getBalance(),
        accounts.getFirst().getCurrency());
    }

     public ResponseAccountDTO addSubAccount (User user){

         List<Account> accounts = user.getAccounts();

         Account newAccount = new Account();
         newAccount.setAccountNumber(generateAccountNumber());
         newAccount.setBalance(BigDecimal.valueOf(0));
         newAccount.setCurrency(accounts.getFirst().getCurrency());
         newAccount.setParentAccountId(accounts.getFirst().getId());
         newAccount.setSubAccount(true);
         newAccount.setUser(user);

         accounts.add(newAccount);
         ACCOUNTREPOSITORY.save(newAccount);
         return new ResponseAccountDTO(user.getEmail(),user.getName(),user.getAccounts().getFirst().getCurrency());
     }

    private long generateAccountNumber() {

        Random random = new Random();

        long accountNumber;

        do {
            accountNumber = 1_000_000_000L + random.nextLong(9_000_000_000L);
        } while (ACCOUNTREPOSITORY.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
