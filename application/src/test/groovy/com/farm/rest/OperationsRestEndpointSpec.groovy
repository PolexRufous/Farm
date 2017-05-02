package com.farm.rest

import com.farm.database.entities.operations.Operation
import com.farm.database.entities.operations.OperationType
import com.farm.database.entities.personality.Partner
import com.farm.executors.operations.OperationExecutionResult
import com.farm.processes.OperationProcess
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

import java.sql.Date

class OperationsRestEndpointSpec extends Specification {
    @Subject
            operationRestEndpoint
    def operationProcess = Stub(OperationProcess)

    void setup() {
        operationRestEndpoint = new OperationsRestEndpoint(operationProcess)
    }

    def "should get all operations"() {
        given:
        def operations = [new Operation(id: 1L), new Operation(id: 2L)]
        operationProcess.findAll() >> operations

        when:
        def result = operationRestEndpoint.getAll().getBody()

        then:
        result == operations
    }

    def "should get operations by partner id"() {
        given:
        def partnerId = 1199L
        def operations = [new Operation(id: 3L), new Operation(id: 4L)]
        operationProcess.findByPartnerId(partnerId) >> operations

        when:
        def result = operationRestEndpoint.getByPartnerId(partnerId).getBody()

        then:
        result == operations
    }

    def "should return saved operation if no errors"() {
        given:
        def id = 1L
        def date = new Date(10000L)
        def amount = BigDecimal.valueOf(500L)
        def filledPartner = new Partner(id: 1L, name: 'John')
        def type = OperationType.PAY_SALARY_CASH_UA
        def operation = new Operation(
                enterDate: date,
                expectedCommitDate: date,
                factCommitDate: date,
                amount: amount,
                partner: filledPartner,
                operationType: type)
        def savedOperation = new Operation(
                id : id,
                enterDate: date,
                expectedCommitDate: date,
                factCommitDate: date,
                amount: amount,
                partner: filledPartner,
                operationType: type)

        operationProcess.save(operation) >> new OperationExecutionResult(result: savedOperation, errors: Collections.EMPTY_MAP)

        when:
        def result = operationRestEndpoint.save(operation).getBody()

        then:
        with(result) {
            getId() == id
            getEnterDate() == date
            getExpectedCommitDate() == date
            getFactCommitDate() == date
            getAmount() == amount
            getPartner() == filledPartner
            getOperationType() == type
        }
    }

    def "should return badRequest if errors on entity validation"() {
        given:
        def operation = new Operation()

        when:
        ResponseEntity result = operationRestEndpoint.save(operation)

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    def "should return badRequest if errors on operation validation"() {
        given:
        def date = new Date(10000L)
        def amount = BigDecimal.valueOf(500L)
        def filledPartner = new Partner(id: 1L, name: 'John')
        def type = OperationType.PAY_SALARY_CASH_UA
        def operation = new Operation(
                enterDate: date,
                amount: amount,
                partner: filledPartner,
                operationType: type)

        operationProcess.save(operation) >> new OperationExecutionResult(result: null, errors: ['errorName': 'errorText'])
        when:
        ResponseEntity result = operationRestEndpoint.save(operation)

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }
}
