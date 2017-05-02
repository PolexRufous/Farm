package com.farm.processes

import com.farm.database.entities.accounts.Account
import com.farm.database.entities.accounts.AccountType
import com.farm.database.entities.operations.Operation
import com.farm.database.entities.operations.OperationRepository
import com.farm.database.entities.operations.OperationType
import com.farm.database.entities.personality.Partner
import com.farm.executors.operations.OperationExecutionResult
import com.farm.executors.operations.PaySalaryCashUaOperationExecutor
import com.farm.executors.validators.OperationSufficientFundsValidator
import org.springframework.context.ApplicationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.sql.Date

class OperationProcessSpec extends Specification {

    @Subject
    operationProcess;
    def cashUaOperationExecutor
    def operationRepository = Mock(OperationRepository)
    def appContext = Stub(ApplicationContext)
    def partnerProcess = Stub(PartnerProcess)
    def accountProcess = Stub(AccountProcess)

    void setup() {
        operationProcess = new OperationProcess(operationRepository, appContext, partnerProcess)
        cashUaOperationExecutor = new PaySalaryCashUaOperationExecutor(accountProcess, new OperationSufficientFundsValidator())
    }

    @Unroll
    def "should make all checks before save when amout=#amout and balanceOfFarm=#balanceOfFarm"() {
        given:
        Partner partner = new Partner()
        Operation operation = new Operation(
                enterDate: new Date(dateInMills),
                amount: BigDecimal.valueOf(amout),
                partner: partner,
                operationType: operationType)

        accountProcess.findOrCreateByType(AccountType.MONEY_CASH_UA, Partner.getFarm()) >> new Account(balance: BigDecimal.valueOf(balanceOfFarm))
        accountProcess.findOrCreateByType(AccountType.CALCULATIONS_WORKER_SALARY, partner) >> new Account()
        appContext.getBean(operationType.getExecutorClass()) >> cashUaOperationExecutor

        when:
        OperationExecutionResult result = operationProcess.save(operation)

        then:
        operationRepositoryCalls * operationRepository.save(_) >> operation
        result.hasNoErrors() == expectedResult
        if (result.result != null) {
            with(result.result) {
                getAmount() == 500L
                getPartner() == partner
                getEnterDate() == new Date(dateInMills)
                getOperationType() == operationType
            }
        }

        where:
        dateInMills | amout | balanceOfFarm | operationType                    | expectedResult | operationRepositoryCalls
        100000L     | 500L  | 600L          | OperationType.PAY_SALARY_CASH_UA | true           | 1
        100000L     | 500L  | 400L          | OperationType.PAY_SALARY_CASH_UA | false          | 0
    }

    def "should fill operation with partner from repository"() {
        given:
        def name = 'Fill'
        def parnerId = 1L
        Partner partner = new Partner(name: name)
        Operation operation = new Operation(partner: new Partner(id: parnerId))
        partnerProcess.findById(parnerId) >> partner

        when:
        operationProcess.fillOperation(operation)

        then:
        operation.getPartner() == partner
    }

    def "should return all operation from repository"() {
        given:
        def id1 = 1L
        def id2 = 2L
        operationRepository.findAll() >> [new Operation(id: id1), new Operation(id: id2)]

        when:
        def result = operationProcess.findAll()

        then:
        result.get(0).getId() == id1
        result.get(1).getId() == id2
    }

    def "should return all operation from repository by partner id"() {
        given:
        def id1 = 5L
        def id2 = 4L
        def partnerId = 3L
        operationRepository.findAllByPartnerId(partnerId) >> [new Operation(id: id1), new Operation(id: id2)]

        when:
        def result = operationProcess.findByPartnerId(partnerId)

        then:
        result.get(0).getId() == id1
        result.get(1).getId() == id2
    }
}
