package com.farm.database.utilits

import com.farm.database.entities.accounts.Account
import com.farm.executors.validators.OperationSufficientFundsValidator
import com.farm.executors.validators.ValidationError
import com.farm.executors.validators.ValidationResult
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class OperationSufficientFundsValidatorSpec extends Specification {
    @Subject
    def operationValidator = new OperationSufficientFundsValidator()

    @Unroll
    def "should validate operation when amountOnFromAccount=#amountOnFromAccount | operationAmount=#operationAmount"() {
        given:
        def accountFrom = Stub(Account)
        accountFrom.getBalance() >> BigDecimal.valueOf(amountOnFromAccount)

        when:
        ValidationResult validationResult = operationValidator.validate(accountFrom, BigDecimal.valueOf(operationAmount))

        then:
        validationResult.getErrorsMap().keySet().containsAll(expectedErrors)

        where:
        amountOnFromAccount | operationAmount | expectedErrors
        100                 | 110             | [ValidationError.INSUFFICIENT_FUNDS.shortName]
        100                 | 90              | []
    }
}
