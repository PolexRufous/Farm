package com.farm.database.kassa;

import com.farm.environment.configuration.FarmDatabaseTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@FarmDatabaseTest
public class GlobalDatabaseTest {

    @Test
    @DatabaseSetup("database/tables/emptyDatabase.xml")
    @ExpectedDatabase("database/tables/emptyDatabase.xml")
    public void testFarmDatabaseExist() throws Exception {

    }
}
