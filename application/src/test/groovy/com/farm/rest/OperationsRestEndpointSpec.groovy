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
}
