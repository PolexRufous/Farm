package com.farm.executors.operations;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.operations.*;
import com.farm.database.entities.personality.Partner;
import com.farm.executors.validators.OperationSufficientFundsValidator;
import com.farm.processes.AccountProcess;
import com.farm.processes.PartnerProcess;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class ExternalOperationExecutorTest {

    @Mock
    private OperationRepository operationRepository;
    @Mock
    private OperationExecutionParametersRepository operationExecutionParametersRepository;
    @Mock
    private OperationSufficientFundsValidator operationSufficientFundsValidator;
    @Mock
    private AccountProcess accountProcess;
    @Mock
    private PartnerProcess partnerProcess;
    @Mock
    private MessageSource messageSource;

    @Captor
    private ArgumentCaptor<Operation> operationArgumentCaptor;

    @InjectMocks
    private ExternalOperationExecutor externalOperationExecutor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters(method = "paramsForExecute")
    @TestCaseName("should has errors({5}) when operation is executed with appropriate parameters")
    public void execute(
            Date factCommitDate,
            boolean checkFunds,
            boolean enoughFunds,
            boolean savedOperationExist,
            boolean operationIsCommited,
            boolean errorsExpected) throws Exception {

        //given:
        OperationExecutionParameters parameters = new OperationExecutionParameters()
                .setId(1L)
                .setCheckFundsNeeded(checkFunds);
        Map<String, String> fundsValidationErrors = Collections.emptyMap();
        if (!enoughFunds) {
            fundsValidationErrors = Collections.singletonMap("error", "error");
        }

        Operation savedOperation = null;
        if (savedOperationExist) {
            savedOperation = new Operation()
                    .setPartnerId(1L)
                    .setAccountFrom(new Account().setBalance(BigDecimal.ONE))
                    .setAccountTo(new Account().setBalance(BigDecimal.ONE))
                    .setAmount(BigDecimal.ONE);
        }
        if (Objects.nonNull(savedOperation) && operationIsCommited) {
            savedOperation.setFactCommitDate(factCommitDate);
        }

        Operation enterOperation = new Operation()
                .setId(1L)
                .setFactCommitDate(factCommitDate);

        when(operationExecutionParametersRepository.findByOperationType(any(OperationType.class)))
                .thenReturn(parameters);
        when(operationSufficientFundsValidator.validate(any(Account.class), any(BigDecimal.class)))
                .thenReturn(fundsValidationErrors);
        when(operationRepository.findOne(1L)).thenReturn(savedOperation);
        when(operationRepository.save(operationArgumentCaptor.capture())).thenReturn(savedOperation);

        //when
        OperationExecutionResult result = externalOperationExecutor.execute(enterOperation);

        //then:
        verify(operationRepository, times(1)).findOne(eq(1L));
        if (errorsExpected) {
            assertTrue(Objects.isNull(result.getResult()));
            assertTrue(MapUtils.isNotEmpty(result.getErrors()));
            verify(operationRepository, never()).save(any(Operation.class));
            verify(accountProcess, never()).saveAccount(any(Account.class));
        } else {
            assertTrue(result.hasNoErrors());
            assertTrue(Objects.nonNull(result.getResult()));
            assertTrue(result.getResult().equals(savedOperation));
            verify(operationRepository, times(1)).save(any(Operation.class));
            verify(accountProcess, times(2)).saveAccount(any(Account.class));
            assertEquals(factCommitDate, operationArgumentCaptor.getValue().getFactCommitDate());
        }
    }

    @Test
    @Parameters(method = "paramsForExecuteCreate")
    @TestCaseName("should correct create operation with correct parameters when account number = {0}")
    public void executeCreate(String accNumTo, Account accResult) throws Exception {

        //given
        String description = "valid message";
        String subject = "hammers";
        String messageKey = "message.key";
        OperationType operationType = OperationType.PAY_SALARY_CASH_UA;
        AccountType accountType = AccountType.EXPENSES_SALARY_BASE;
        Account newAccount = new Account()
                .setId(2L)
                .setAccountNumber("65432")
                .setAccountType(accountType)
                .setSubject(subject);
        if (Objects.nonNull(accResult)) {
            accResult
                    .setAccountType(accountType)
                    .setSubject(subject);
        }
        Account accountFrom = new Account().setId(1L).setAccountNumber("987654");
        Partner savedPartner = new Partner().setId(1L).setName("Ivan");
        Document document = new Document()
                .setId(1L)
                .setPartnerId(1L)
                .setPartner(savedPartner)
                .setSubject(subject);
        Operation expectedOperation = new Operation();
        OperationExecutionParameters parameters = new OperationExecutionParameters()
                .setId(1L)
                .setAccountNumberTo(accNumTo)
                .setAccountTypeTo(accountType)
                .setOperationMessageKey(messageKey);
        when(messageSource.getMessage(eq(messageKey), any(), any(Locale.class))).thenReturn(description);
        when(partnerProcess.findById(1L)).thenReturn(savedPartner);
        when(operationRepository.save(operationArgumentCaptor.capture())).thenReturn(expectedOperation);
        when(accountProcess.createAccount(eq(accountType), any(Partner.class), anyString())).thenReturn(newAccount);
        when(accountProcess.findByNumber(eq(accNumTo))).thenReturn(accResult);
        when(operationExecutionParametersRepository.findByOperationType(eq(operationType))).thenReturn(parameters);

        //when
        OperationExecutionResult result = externalOperationExecutor.executeCreate(operationType, document, accountFrom);

        //then
        if (Objects.nonNull(accNumTo)) {
            verify(accountProcess, times(1)).findByNumber(anyString());
            verifyNoMoreInteractions(accountProcess);
        } else {
            verify(accountProcess, times(1)).createAccount(accountType, savedPartner, subject);
            verifyNoMoreInteractions(accountProcess);
        }
        if (Objects.isNull(accResult) && Objects.nonNull(accNumTo)) {
            verifyNoMoreInteractions(messageSource);
            Objects.isNull(result.getResult());
            MapUtils.isNotEmpty(result.getErrors());
            assertFalse(result.hasNoErrors());
        } else {
            verify(messageSource, times(1)).getMessage(eq(messageKey), any(), any(Locale.class));
            verifyNoMoreInteractions(messageSource);
            assertTrue(result.hasNoErrors());
            Objects.nonNull(result.getResult());
            assertTrue(result.getResult().equals(expectedOperation));
        }

        if (result.hasNoErrors()) {
            Operation operationForSave = operationArgumentCaptor.getValue();
            assertEquals(description, operationForSave.getDescription());
            assertEquals(operationType, operationForSave.getOperationType());
            assertEquals(accountType, operationForSave.getAccountTo().getAccountType());
            assertEquals(savedPartner, operationForSave.getPartner());
            assertEquals(subject, operationForSave.getAccountTo().getSubject());
        }
    }

    private Object[] paramsForExecuteCreate() {
        return new Object[]{
                new Object[]{"3010111111", new Account().setId(1L).setAccountNumber("3010111111")},
                new Object[]{null, null},
                new Object[]{"3010111111", null}
        };
    }

    private Object[] paramsForExecute() {
        return new Object[]{
                //             factCommitDate         | checkFunds | enoughFunds | savedOperExist | operCommited | errExpected
                new Object[]{Date.valueOf("2017-05-19"), true, true, true, false, false},
                new Object[]{Date.valueOf("2017-05-19"), false, false, true, false, false},
                new Object[]{Date.valueOf("2017-05-19"), false, false, true, true, true},
                new Object[]{Date.valueOf("2017-05-19"), true, false, false, false, true},
                new Object[]{null, true, true, false, false, true},
                new Object[]{null, true, false, false, false, true},
                new Object[]{null, false, false, false, false, true}
        };
    }
}