package com.farm.processes;

import com.farm.database.entities.accounts.AccountEntity;
import com.farm.database.entities.accounts.AccountRepository;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.personality.PartnerEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class AccountProcess {
    @Resource
    private AccountRepository accountRepository;

    // TODO: 26.02.2017 implement method
    public AccountEntity findOrCreateByAccountNumber(String accountNumber) {
        return null;
    }

    public AccountEntity findOrCreateByType(AccountType accountType, PartnerEntity partnerEntity) {
        AccountEntity accountEntity = accountRepository.findByPartnerIdAndAccountType(partnerEntity.getId(), accountType);
        if (accountEntity == null) {
            accountEntity = new AccountEntity();
            accountEntity.setAccountType(accountType);
            accountEntity.setPartnerEntity(partnerEntity);
            accountEntity = accountRepository.save(accountEntity);
        }
        return accountEntity;
    }

    public AccountEntity saveAccount(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }
}
