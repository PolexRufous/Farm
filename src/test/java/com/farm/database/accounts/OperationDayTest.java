package com.farm.database.accounts;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;

import com.farm.database.operations.OperationDay;
import com.farm.environment.configuration.FarmDatabaseTest;
import com.farm.processes.OperationDayProcess;
import com.farm.utilits.FarmEntityValidator;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
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
  public void testGetDayNotExist() throws Exception{
    //given
    Date date = Date.valueOf("2017-02-19");
    //when
    OperationDay operationDay = operationDayProcess.findOrCreateOperationDayByDate(date);
    //then
    assertEquals(date, operationDay.getDate());
    assertEquals(1L, operationDay.getId().longValue());
  }
}
