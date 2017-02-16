package com.farm.database.accounts;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import com.farm.database.operations.OperationDay;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class OperationDayTest {

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
    Map<String, String> errors = validateOperDay(operationDay);
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
    Map<String, String> errors = validateOperDay(operationDay);
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
    Map<String, String> errors = validateOperDay(operationDay);
    //then
    assertEquals(0, errors.size());
  }

  @Test
  public void testInsertion() throws Exception{

  }

  private Map<String, String> validateOperDay(OperationDay operationDay) {
    Set<ConstraintViolation<OperationDay>> constrains =
            Validation
                    .buildDefaultValidatorFactory()
                    .getValidator()
                    .validate(operationDay);

    return constrains.stream()
            .collect(Collectors.toMap(
                    constrain -> constrain.getPropertyPath().toString(),
                    ConstraintViolation::getMessage));
  }
}
