package com.farm.processes

import com.farm.database.entities.accounts.Account
import com.farm.database.entities.accounts.AccountRepository
import com.farm.database.entities.accounts.AccountType
import com.farm.database.entities.personality.Partner
import spock.lang.Specification
import spock.lang.Subject

class AccountProcessSpec extends Specification {
    @Subject
    accountProcess
    def accountRepository = Stub(AccountRepository)

    def setup() {
        accountProcess = new AccountProcess(accountRepository)
    }

    def "should not create new account when findOrCreateByType called on existing account"() {
        given:
        def accountId = 1L
        Account account = new Account(id: accountId)
        accountRepository.findByPartnerIdAndAccountType(_, _) >> account

        when:
        Account result = accountProcess.findOrCreateByType(AccountType.MONEY_BANK_ACCOUNT_UA, new Partner())

        then:
        result.getId() == accountId
    }

    def "should create new account when findOrCreateByType called on new account"() {
        given:
        def accountId = 1L
        Account account = new Account(id: accountId)
        accountRepository.findByPartnerIdAndAccountType(_, _) >> null
        accountRepository.save(_) >> account

        when:
        Account result = accountProcess.findOrCreateByType(AccountType.MONEY_BANK_ACCOUNT_UA, new Partner())

        then:
        result.getId() == accountId
    }

    def "should retrun saved Account"() {
        given:
        def accountId = 1L
        Account account = new Account()
        Account savedAccount = new Account(id: accountId)
        accountRepository.save(account) >> savedAccount

        when:
        Account result = accountProcess.saveAccount(account)

        then:
        result.getId() == accountId
    }
}
