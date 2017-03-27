package com.farm.database.utilits

import com.farm.database.entities.accounts.AccountEntity
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class OperationSufficientFundsValidatorSpec extends Specification {
    @Subject
    def operationValidator = new OperationSufficientFundsValidator()

    @Unroll
    def "should validate operation when amountOnFromAccount=#amountOnFromAccount | operationAmount=#operationAmount"() {
        given:
        def accountFrom = Stub(AccountEntity)
        accountFrom.getBalance() >> BigDecimal.valueOf(amountOnFromAccount)

        when:
        Map validationErrors = operationValidator.validate(accountFrom, BigDecimal.valueOf(operationAmount))

        then:
        validationErrors.keySet().containsAll(expectedErrors)

        where:
        amountOnFromAccount | operationAmount | expectedErrors
        100                 | 110             | [ValidationError.INSUFFICIENT_FUNDS.shortName]
        100                 | 90              | []
    }
}
