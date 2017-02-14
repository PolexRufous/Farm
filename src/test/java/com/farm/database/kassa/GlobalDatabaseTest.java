package com.farm.database.kassa;

import com.farm.environment.configuration.FarmDatabaseTest;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@FarmDatabaseTest
public class GlobalDatabaseTest
{
  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
          value = "/database/tables/emptyDatabase.xml",
          table = "KASSA_OPERATION_DAY")
  public void testFarmDatabaseExistOperDay() throws Exception
  {
  }
}
