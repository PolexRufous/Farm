package com.farm.database.accounts;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

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
    assertEquals(3, errors.size());
    assertEquals(mayNotBeNull, errors.get("beginAmount"));
    assertEquals(mayNotBeNull, errors.get("endAmount"));
    assertEquals(mayNotBeNull, errors.get("dateOfDay"));
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
                    ConstraintViolation::getMessage
            ));
  }

}
