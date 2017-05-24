package com.farm.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountRepository;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.personality.Partner;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class AccountProcessTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountProcess accountProcess;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters
    @TestCaseName("should return expected result when findByNumber is called with {0}")
    public void findByNumber(String number) throws Exception {
        //given
        Account returnedAccount = null;
        if (Objects.nonNull(number) && number.matches("\\d+")) {
            returnedAccount = new Account().setId(Long.parseLong(number)).setAccountNumber(number);
        }
        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(returnedAccount);
        //when
        Account result = accountProcess.findByNumber(number);

        //then
        if (Objects.nonNull(number) && number.matches("\\d+")) {
            assertTrue(Objects.nonNull(result));
            verify(accountRepository, times(1)).findByAccountNumber(number);
        } else {
            assertTrue(Objects.isNull(result));
            if (Objects.nonNull(number)) {
                verify(accountRepository, times(1)).findByAccountNumber(number);
            }
        }
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    @Parameters
    @TestCaseName("should save or not account {0}")
    public void saveAccount(Account account) throws Exception {
        //given
        if (Objects.nonNull(account)) {
            when(accountRepository.save(any(Account.class)))
                    .thenReturn(account.setId(1L));
        }

        //when
        Account result = accountProcess.saveAccount(account);

        //then
        if (Objects.isNull(account)) {
            assertTrue(Objects.isNull(result));
            verifyNoMoreInteractions(accountRepository);
        } else {
            assertTrue(Objects.nonNull(result));
            assertTrue(result.getId() == 1L);
            verify(accountRepository, times(1)).save(any(Account.class));
            verifyNoMoreInteractions(accountRepository);
        }
    }

    @Test
    @Parameters
    @TestCaseName("should create account")
    public void createAccount(AccountType accountType, Partner partner, String subject) throws Exception {
        //given
        when(accountRepository.save(accountArgumentCaptor.capture())).thenReturn(new Account().setId(1L));

        //when
        Account result = accountProcess.createAccount(accountType, partner, subject);

        //then
        assertEquals(1L, result.getId().longValue());
        Account savedAccount = accountArgumentCaptor.getValue();
        assertEquals(accountType, savedAccount.getAccountType());
        assertEquals(partner, savedAccount.getPartner());
        assertEquals(partner.getId(), savedAccount.getPartnerId());
        assertEquals(subject, savedAccount.getSubject());
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(accountRepository, atLeastOnce()).findByAccountNumber(anyString());
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    @Parameters
    @TestCaseName("should return {1} for number {0}")
    public void isNumberExist(String number, boolean isExist) throws Exception {
        //given
        Account returnedAccount = null;
        if (Objects.nonNull(number) && number.matches("\\d+")) {
            returnedAccount = new Account().setId(Long.parseLong(number)).setAccountNumber(number);
        }
        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(returnedAccount);
        //when
        boolean result = accountProcess.isNumberExist(number);

        //then
        assertEquals(isExist, result);
    }

    private Object[] parametersForFindByNumber() {
        return new Object[]{"12L", null, "1234"};
    }

    private Object[] parametersForSaveAccount() {
        return new Object[]{
                new Account().setSubject("subject").setPartnerId(1L).setAccountNumber("123"),
                null
        };
    }

    private Object[] parametersForCreateAccount() {
        return new Object[]{
                AccountType.ACTIVITY_EXPENSES_OTHER,
                new Partner().setId(1L).setDescription("Good partner").setName("Hello World"),
                "Hammers"
        };
    }

    private Object[] parametersForIsNumberExist() {
        return new Object[]{
                new Object[]{"12L", false},
                new Object[]{null, false},
                new Object[]{"12345", true}
        };
    }


}