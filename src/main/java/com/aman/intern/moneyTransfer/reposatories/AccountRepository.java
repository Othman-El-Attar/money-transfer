package com.aman.intern.moneyTransfer.reposatories;

import com.aman.intern.moneyTransfer.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Long accountNumber);
    boolean existsByAccountNumber(long accountNumber);

}
