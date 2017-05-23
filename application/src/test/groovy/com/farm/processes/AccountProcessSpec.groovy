package com.farm.processes

import com.farm.database.entities.accounts.Account
import com.farm.database.entities.accounts.AccountRepository
import com.farm.database.entities.accounts.AccountType
import com.farm.database.entities.personality.Partner
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AccountProcessSpec extends Specification {
    @Subject
    accountProcess
    def accountRepository = Stub(AccountRepository)

    def setup() {
        accountProcess = new AccountProcess(accountRepository)
    }

    def "should create new expected account when createAccount is called"() {
        given:
        def accountId = 1L
        def partnerId = 1L
        def subject = "Some thing"
        def accountType = AccountType.MONEY_BANK_ACCOUNT_UA
        Account account = new Account(
                id: accountId,
                partnerId: partnerId,
                subject: subject,
                accountType: accountType
        )
        accountRepository.save(_) >> account
        accountRepository.findByAccountNumber(_ as String) >> null
        def partner = new Partner(id: partnerId)


        when:
        Account result = accountProcess.createAccount(accountType, partner, subject)

        then:
        result == account
    }

    def "should return saved Account"() {
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

    @Unroll
    def "should return expected result (#expectedResult) for input (#input)"() {
        given:
        accountRepository.findByAccountNumber("65432") >> null
        accountRepository.findByAccountNumber("12345") >> new Account(id: 1L, accountNumber: "12345")

        when:
        Account result = accountProcess.findByNumber(input)

        then:
        result == expectedResult
        if (input == null) {
            0 * accountRepository.findByAccountNumber(_ as String)
        }

        where:
        input   | expectedResult
        null    | null
        "65432" | null
        "12345" | new Account(id: 1L, accountNumber: "12345")

    }
}
