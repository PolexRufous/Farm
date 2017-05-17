package com.farm.executors.documents

import com.farm.database.entities.accounts.Account
import com.farm.database.entities.documents.*
import com.farm.database.entities.operations.Operation
import com.farm.database.entities.operations.OperationType
import com.farm.database.entities.personality.Partner
import com.farm.executors.operations.ExternalOperationExecutor
import com.farm.executors.operations.OperationExecutionResult
import com.farm.processes.AccountProcess
import com.farm.processes.PartnerProcess
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.sql.Date

class ExternalDocumentExecutorSpec extends Specification {

    @Subject
    externalDocumentExecutor

    def partnerProcess = Stub(PartnerProcess)
    def documentRepository = Stub(DocumentRepository)
    def externalOperationExecutor = Stub(ExternalOperationExecutor)
    def accountProcess = Stub(AccountProcess)
    def documentExecutionParametersRepository = Stub(DocumentExecutionParametersRepository)

    def setup() {
        externalDocumentExecutor = new ExternalDocumentExecutor(
                partnerProcess,
                documentRepository,
                externalOperationExecutor,
                accountProcess,
                documentExecutionParametersRepository
        )
    }

    def "should correct create document with correct parameters"(){
        given:
        def accountNumber = "654321"
        def preparedDocument = new Document(
                enterDate: Date.valueOf("2017-05-17"),
                expectedCommitDate: Date.valueOf("2017-05-30"),
                amount: BigDecimal.valueOf(12345.67D),
                subject: "Some goods",
                partnerId: 1L,
                documentType: DocumentType.PAY_SALARY,
                startAccountNumber: accountNumber
        )

        def documentExecutionParameters = new DocumentExecutionParameters(
                id: 1L,
                paymentOperationType: OperationType.PAY_SALARY_CASH_UA,
                receiveOperationType: OperationType.POSTING_OF_SALARY,
                startAccountNeeded: true
        )

        def testPartner = new Partner(
                id: 1L
        )

        def startAccount = new Account(
                id: 1L,
                accountNumber: accountNumber
        )

        def accountTo = new Account(
                id: 2L,
                accountNumber: "123456"
        )

        partnerProcess.findById(1L) >> testPartner
        documentRepository.save(preparedDocument) >> preparedDocument.setId(1L)
        documentExecutionParametersRepository.findByDocumentType(DocumentType.PAY_SALARY) >> documentExecutionParameters
        accountProcess.findByNumber(accountNumber) >> startAccount
        externalOperationExecutor.executeCreate(OperationType.PAY_SALARY_CASH_UA, preparedDocument, startAccount) >>
                new OperationExecutionResult(new Operation(id: 1L, accountTo: accountTo), Collections.emptyMap())
        externalOperationExecutor.executeCreate(OperationType.POSTING_OF_SALARY, preparedDocument, accountTo) >>
                new OperationExecutionResult(new Operation(id: 2L), Collections.emptyMap())

        when:
        DocumentExecutionResult result = externalDocumentExecutor.executeCreate(preparedDocument)

        then:
        result.hasNoErrors()
        Objects.nonNull(result.getDocument())
        with(result.getDocument()){
            getOperations().size() == 2
        }
    }

    @Unroll
    def "should have errCount=#errCount with appropriate incorrect parameters"(){
        given:
        def accountNumber = startNumber
        def preparedDocument = new Document(
                enterDate: Date.valueOf("2017-05-17"),
                expectedCommitDate: Date.valueOf("2017-05-30"),
                amount: BigDecimal.valueOf(12345.67D),
                subject: "Some goods",
                partnerId: 1L,
                documentType: DocumentType.PAY_SALARY,
                startAccountNumber: accountNumber
        )

        def documentExecutionParameters = new DocumentExecutionParameters(
                id: 1L,
                paymentOperationType: payOpType,
                receiveOperationType: resOpType,
                startAccountNeeded: stAccNeeded
        )

        def startAccount = new Account(
                id: 1L,
                accountNumber: accountNumber
        )

        def accountTo = new Account(
                id: 2L,
                accountNumber: "123456"
        )

        partnerProcess.findById(1L) >> testPartner
        documentRepository.save(preparedDocument) >> preparedDocument.setId(1L)
        documentExecutionParametersRepository.findByDocumentType(DocumentType.PAY_SALARY) >> documentExecutionParameters
        accountProcess.findByNumber(accountNumber) >> startAccount
        externalOperationExecutor.executeCreate(OperationType.PAY_SALARY_CASH_UA, preparedDocument, startAccount) >>
                new OperationExecutionResult(new Operation(id: 1L, accountTo: accountTo), Collections.emptyMap())
        externalOperationExecutor.executeCreate(OperationType.POSTING_OF_SALARY, preparedDocument, accountTo) >>
                new OperationExecutionResult(new Operation(id: 2L), Collections.emptyMap())

        when:
        DocumentExecutionResult result = externalDocumentExecutor.executeCreate(preparedDocument)

        then:
        !result.hasNoErrors()
        Objects.isNull(result.getDocument())
        result.getErrors().size() == errCount

        where:
        testPartner         | startNumber | payOpType                         | resOpType | stAccNeeded   | errCount
        null                | null        | null                              | null      | true          | 3
        new Partner(id: 1L) | null        | null                              | null      | true          | 2
        new Partner(id: 1L) | null        | null                              | null      | false         | 1
        new Partner(id: 1L) | null        | OperationType.PAY_SALARY_CASH_UA  | null      | false         | 1
    }

    def "should have error with null execution parameters"() {
        given:
        def preparedDocument = new Document(
                enterDate: Date.valueOf("2017-05-17"),
                expectedCommitDate: Date.valueOf("2017-05-30"),
                amount: BigDecimal.valueOf(12345.67D),
                subject: "Some goods",
                partnerId: 1L,
                documentType: DocumentType.PAY_SALARY,
                startAccountNumber: "12345"
        )
        documentExecutionParametersRepository.findByDocumentType(DocumentType.PAY_SALARY) >> null
        partnerProcess.findById(1L) >> new Partner(id:  1L)

        when:
        DocumentExecutionResult result = externalDocumentExecutor.executeCreate(preparedDocument)

        then:
        !result.hasNoErrors()
        Objects.isNull(result.getDocument())
        result.getErrors().size() == 1
    }
}