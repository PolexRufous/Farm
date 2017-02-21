package com.farm.database.accounts;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;

import com.farm.database.operations.Operation;
import com.farm.database.operations.OperationDay;
import com.farm.database.operations.OperationType;
import com.farm.database.personality.Partner;
import com.farm.database.personality.PartnerType;
import com.farm.environment.configuration.FarmDatabaseTest;
import com.farm.processes.OperationDayProcess;
import com.farm.utilits.FarmEntityValidator;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@FarmDatabaseTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class OperationDayTest {

  @Resource
  private OperationDayProcess operationDayProcess;

  @Before
  public void setUp() throws Exception{
    Locale.setDefault(Locale.ENGLISH);
  }

  @Test
  public void testNullValidation() throws Exception{
    //given
    OperationDay operationDay = new OperationDay();
    String mayNotBeNull = "may not be null";
    //when
    Map<String, String> errors = FarmEntityValidator.getValidationErrors(operationDay);
    //then
    assertEquals(1, errors.size());
    assertEquals(mayNotBeNull, errors.get("date"));
  }

  @Test
  public void testFutureDateValidation() throws Exception{
    //given
    LocalDate futureDate = LocalDate.now().plusDays(1);
    OperationDay operationDay = new OperationDay();
    operationDay.setDate(Date.valueOf(futureDate));
    String mayBeInPast = "must be in the past";
    //when
    Map<String, String> errors = FarmEntityValidator.getValidationErrors(operationDay);
    //then
    assertEquals(1, errors.size());
    assertEquals(mayBeInPast, errors.get("date"));
  }

  @Test
  public void testCurrentDateValidation() throws Exception{
    //given
    LocalDate currentDate = LocalDate.now();
    OperationDay operationDay = new OperationDay();
    operationDay.setDate(Date.valueOf(currentDate));
    //when
    Map<String, String> errors = FarmEntityValidator.getValidationErrors(operationDay);
    //then
    assertEquals(0, errors.size());
  }

  @Test
  @ExpectedDatabase(value = "/database/tables/databaseOneDay19022017.xml")
  public void testGetDayNotExist() throws Exception{
    //given
    Date date = Date.valueOf("2017-02-19");
    //when
    OperationDay operationDay = operationDayProcess.findOrCreateByDate(date);
    //then
    assertEquals(date, operationDay.getDate());
    assertEquals(1L, operationDay.getId().longValue());
  }

  @Test
  @DatabaseSetup(value = "/database/tables/databaseWithSomeData.xml")
  @ExpectedDatabase(value = "", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  public void testCreateDayWithOperations() throws Exception{
    //given
    Date date = Date.valueOf("2017-02-20");
    OperationDay operationDay = new OperationDay();
    operationDay.setDate(date);
    Operation operation = new Operation();
    Account accountFrom = new Account();
    accountFrom.setId(1L);
    accountFrom.setAccountNumber("361111");
    accountFrom.setAccountType(AccountType.RECEIVABLES_FROM_CUSTOMERS);
    Partner buyer = new Partner();
    buyer.setId(111L);
    buyer.setDescription("VaryCool");
    buyer.setName("Vasily Petrovich");
    buyer.setPartnerType(PartnerType.BUYER);
    accountFrom.setPartner(buyer);
    operation.setAccountFrom(accountFrom);

    Account accountTo = new Account();
    accountTo.setAccountType(AccountType.CASH_UA);
    accountTo.setAccountNumber("3011");
    accountTo.setId(8L);
    Partner seller = new Partner();
    seller.setId(1L);
    seller.setPartnerType(PartnerType.FARM);
    seller.setName("Farm");
    seller.setDescription("Our Farm");
    accountTo.setPartner(seller);

    operation.setAccountTo(accountTo);
    operation.setPartner(buyer);
    operation.setOperationType(OperationType.SELL_PRODUCTION);
    operation.setAmount(BigDecimal.valueOf(10000.27d));
    operation.setOperationDay(operationDay);

    operationDay.setOperations(Collections.singletonList(operation));
    //when
    operationDayProcess.save(operationDay);
  }
}
