package com.farm.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountRepository;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.personality.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountProcess {
    private AccountRepository accountRepository;

    @Autowired
    public AccountProcess(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findOrCreateByType(AccountType accountType, Partner partner) {
        Account account = accountRepository.findByPartnerIdAndAccountType(partner.getId(), accountType);
        if (account == null) {
            account = new Account();
            account.setAccountType(accountType);
            account.setPartner(partner);
            account = accountRepository.save(account);
        }
        return  account;
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
