package com.farm.database.utilits

import com.farm.database.entities.accounts.Account
import com.farm.database.entities.accounts.AccountBalance
import com.farm.database.entities.operations.Operation
import com.farm.database.processes.AccountBalanceProcess
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class OperationValidatorSpec extends Specification {
    @Subject
    def operationValidator
    def accountBalanceProcess = Stub(AccountBalanceProcess)
    def operation = Stub(Operation)
    def accountFrom = Stub(Account)

    void setup() {
        operationValidator = new OperationValidator(accountBalanceProcess)
    }

    @Unroll
    def "should validate operation when amountOnFromAccount=#amountOnFromAccount | operationAmount=#operationAmount"() {
        given:
        def accountBalance = new AccountBalance('balanceAmount': BigDecimal.valueOf(amountOnFromAccount))
        operation.getAmount() >> BigDecimal.valueOf(operationAmount)
        operation.getAccountFrom() >> accountFrom
        accountBalanceProcess.findOrCreateByAccount(accountFrom) >> accountBalance

        when:
        operationValidator.validate(operation)
        def validationErrors = operationValidator.getErrors()

        then:
        validationErrors == expectedErrors

        where:
        amountOnFromAccount | operationAmount | expectedErrors
        100                 | 110             | [ValidationError.INSUFFICIENT_FUNDS]
        100                 | 90              | []
    }
}
