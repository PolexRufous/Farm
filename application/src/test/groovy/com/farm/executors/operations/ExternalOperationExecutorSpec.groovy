package com.farm.executors.operations

import com.farm.database.entities.accounts.Account
import com.farm.database.entities.accounts.AccountType
import com.farm.database.entities.documents.Document
import com.farm.database.entities.operations.*
import com.farm.database.entities.personality.Partner
import com.farm.executors.validators.OperationForExecutionValidator
import com.farm.executors.validators.OperationSufficientFundsValidator
import com.farm.processes.AccountProcess
import com.farm.processes.PartnerProcess
import org.apache.commons.collections4.MapUtils
import org.springframework.context.MessageSource
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.sql.Date

class ExternalOperationExecutorSpec extends Specification {

    @Subject
            externalOperationExecutor

    def operationRepository = Mock(OperationRepository)
    def operationExecutionParametersRepository = Stub(OperationExecutionParametersRepository)
    def operationSufficientFundsValidator = Stub(OperationSufficientFundsValidator)
    def accountProcess = Mock(AccountProcess)
    def partnerProcess = Stub(PartnerProcess)
    def messageSource = Stub(MessageSource)
    def operationForExecutionValidator = Spy(OperationForExecutionValidator)

    def setup() {
        externalOperationExecutor = new ExternalOperationExecutor(
                operationRepository,
                operationExecutionParametersRepository,
                operationSufficientFundsValidator,
                operationForExecutionValidator,
                accountProcess,
                partnerProcess,
                messageSource
        )
    }

    @Unroll
    def "should correct create operation with correct parameters when account number to = #accNumTo"() {

        given:
        def operationType = OperationType.PAY_SALARY_CASH_UA
        def accountType = AccountType.EXPENSES_SALARY_BASE
        def newAccount = new Account(id: 2L, accountNumber: "65432")
        def messageKey = "message.key"
        def accountFrom = new Account(id: 1L, accountNumber: "987654")
        def savedPartner = new Partner(id: 1L, name: "Ivan")
        def description = "valid message"
        def document = new Document(
                id: 1L,
                partnerId: 1L,
                partner: savedPartner
        )
        operationExecutionParametersRepository.findByOperationType(operationType) >> new OperationExecutionParameters(
                id: 1L,
                accountNumberTo: accNumTo,
                accountTypeTo: accountType
        )
        def expectedOperation = new Operation(
                accountFrom: accountFrom,
                partnerId: 1L,
                partner: savedPartner,
                description: description
        )
        messageSource.getMessage(messageKey, _ as Object[], _ as Locale) >> description
        partnerProcess.findById(1L) >> new Partner(id: 1L)

        when:
        def result = externalOperationExecutor.executeCreate(operationType, document, accountFrom)

        then:
        operationRepository.save(_) >> expectedOperation
        if (Objects.nonNull(accNumTo)) {
            1 * accountProcess.findByNumber(accNumTo) >> accResult
        } else {
            0 * accountProcess.createAccount(accountType, _ as Partner, _ as String) >> newAccount
        }
        if (Objects.isNull(accResult) && Objects.nonNull(accNumTo)) {
            Objects.isNull(result.result)
            MapUtils.isNotEmpty(result.errors)
        } else {
            result.hasNoErrors()
            Objects.nonNull(result.result)
            result.getResult() == expectedOperation
        }

        where:
        accNumTo     | accResult
        "3010111111" | new Account(id: 1L, accountNumber: "3010111111")
        null         | null
        "3010111111" | null
    }

    @Unroll
    def "should has errors(#errorsExpected) when operation is executed with appropriate parameters"() {

        given:
        operationExecutionParametersRepository.findByOperationType(_ as OperationType) >> new OperationExecutionParameters(
                id: 1L,
                checkFundsNeeded: checkFunds
        )

        def fundsValidationErrors = Collections.emptyMap()
        if (!enoughFunds) {
            fundsValidationErrors = Collections.singletonMap("error", "error")
        }
        operationSufficientFundsValidator.validate(_ as Account, _ as BigDecimal) >> fundsValidationErrors
        def savedOperation = null
        if (savedOperationExist) {
            savedOperation = new Operation(
                    partnerId: 1L,
                    accountFrom: new Account(balance: BigDecimal.ONE),
                    accountTo: new Account(balance: BigDecimal.ONE),
                    amount: BigDecimal.ONE
            )
        }

        def enterOperation = new Operation(
                id: 1L,
                factCommitDate: factCommitDate
        )

        when:
        def result = externalOperationExecutor.execute(enterOperation)

        then:
        0 * accountProcess.createAccount(_ as AccountType, _ as Partner, _ as String)
        0 * accountProcess.findByNumber(_ as String)
        1 * operationRepository.findOne(1L) >> savedOperation
        operationRepository.save(_) >> savedOperation

        where:
        factCommitDate             | checkFunds | enoughFunds | savedOperationExist | errorsExpected
        Date.valueOf("2017-05-19") | true       | true        | true                | false
        Date.valueOf("2017-05-19") | false      | false       | true                | false
        Date.valueOf("2017-05-19") | true       | false       | false               | true
        null                       | true       | true        | false               | true
        null                       | true       | false       | false               | true
        null                       | false      | true        | false               | true

    }
}
