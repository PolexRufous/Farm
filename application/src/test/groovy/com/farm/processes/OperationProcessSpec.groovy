package com.farm.processes

import com.farm.database.entities.operations.Operation
import com.farm.database.entities.operations.OperationRepository
import com.farm.database.entities.personality.Partner
import com.farm.executors.operations.OperationExecutor
import spock.lang.Specification
import spock.lang.Subject

class OperationProcessSpec extends Specification {

    @Subject
    OperationProcess operationProcess
    def operationRepository = Mock(OperationRepository)
    def partnerProcess = Stub(PartnerProcess)
    def operationExecutor = Stub(OperationExecutor)

    void setup() {
        operationProcess =
                new OperationProcess(operationRepository, partnerProcess, operationExecutor)
    }

    def "should fill operation with partner from repository"() {
        given:
        def name = 'Fill'
        def partnerId = 1L
        Partner partner = new Partner(name: name)
        Operation operation = new Operation(partnerId: partnerId)
        partnerProcess.findById(partnerId) >> partner

        when:
        operationProcess.fillOperationPartner(operation)

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

    def "should find operation by id"() {
        given:
        def id1 = 1L
        operationRepository.findOne(id1) >> new Operation(id: id1)

        when:
        def result = operationProcess.findById(id1)

        then:
        result.getId() == id1
    }

    def "should not try to find operation by id with null param"() {
        given:
        def id1 = null
        operationRepository.findOne(id1) >> new Operation(id: id1)

        when:
        def result = operationProcess.findById(id1)

        then:
        result == null
        0 * operationRepository._
    }

    def "should not try to delegate execute with null param"() {
        given:
        def operation = null
        operationExecutor.execute(operation) >> null

        when:
        def result = operationProcess.execute(operation)

        then:
        result == null
        0 * operationExecutor._
    }

    def "should find operation by document id"() {
        given:
        def id1 = 1L
        def id2 = 2L
        def docId = 1L
        operationRepository.findAllByDocumentId(docId) >> [new Operation(id: id1), new Operation(id: id2)]

        when:
        def result = operationProcess.findByDocumentId(docId)

        then:
        result.get(0).getId() == id1
        result.get(1).getId() == id2
    }

    def "should not try to find operation by document id with null param"() {
        given:
        def docId = null
        operationRepository.findAllByDocumentId(docId) >> null

        when:
        def result = operationProcess.findByDocumentId(docId)

        then:
        result == []
        0 * operationRepository._
    }
}
