package com.farm.database.accounts;

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
  @ExpectedDatabase(value = "/database/tables/emptyDatabase.xml")
  public void testEmptyFarmDatabaseExist() throws Exception
  {
  }


}
