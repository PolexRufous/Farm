package com.farm.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountRepository;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.personality.Partner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountProcess {

    @NonNull
    private AccountRepository accountRepository;

    public Account findByNumber(String number){
        return Optional.ofNullable(number)
                .map(accountRepository::findByAccountNumber)
                .orElse(null);
    }

    public Account saveAccount(Account account) {
        return Optional.ofNullable(account)
                .map(accountRepository::save)
                .orElse(null);
    }

    public Account createAccount(AccountType accountType, @Valid Partner partner, String subject) {
        Account account = new Account()
                .setAccountType(accountType)
                .setPartner(partner)
                .setPartnerId(partner.getId())
                .setSubject(subject)
                .setAccountNumber(getAccountNumber(accountType, partner));

        return accountRepository.save(account);
    }

    private String getAccountNumber(AccountType accountType, Partner partner) {
        String randomPart = getAccountRandomPart();
        String number = accountType
                .getAccountCode()
                .concat(partner.getId().toString())
                .concat(randomPart);
        return isNumberExist(number) ? getAccountNumber(accountType, partner) : number;
    }

    private String getAccountRandomPart() {
        return String.valueOf(Double.valueOf(Math.random() * 100000).longValue());
    }

    public boolean isNumberExist(String number) {
        return Objects.nonNull(accountRepository.findByAccountNumber(number));
    }
}
