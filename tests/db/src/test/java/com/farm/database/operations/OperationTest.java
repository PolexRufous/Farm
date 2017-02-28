package test.java.com.farm.database.operations;

import com.farm.database.accounts.Account;
import com.farm.database.accounts.AccountType;
import com.farm.database.personality.Partner;
import com.farm.environment.configuration.FarmDatabaseTest;
import com.farm.processes.OperationProcess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@FarmDatabaseTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class OperationTest {

    @Resource
    private OperationProcess operationProcess;

    @Before
    public void setUp() throws Exception{
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testCreateOperation() throws Exception {
        //given
        Date date = Date.valueOf("2017-02-20");
        Operation operation = new Operation();
        operation.setDate(date);
        Account accountFrom = new Account();
        accountFrom.setId(1L);
        accountFrom.setAccountNumber("361111");
        accountFrom.setAccountType(AccountType.RECEIVABLES_FROM_CUSTOMERS);
        Partner buyer = new Partner();
        buyer.setId(111L);
        buyer.setDescription("VaryCool");
        buyer.setName("Vasily Petrovich");
        accountFrom.setPartner(buyer);
        operation.setAccountFrom(accountFrom);

        Account accountTo = new Account();
        accountTo.setAccountType(AccountType.CASH_UA);
        accountTo.setAccountNumber("3011");
        accountTo.setId(8L);
        Partner seller = new Partner();
        seller.setId(1L);
        seller.setName("Farm");
        seller.setDescription("Our Farm");
        accountTo.setPartner(seller);

        operation.setAccountTo(accountTo);
        operation.setPartner(buyer);
        operation.setOperationType(OperationType.SELL_PRODUCTION);
        operation.setAmount(BigDecimal.valueOf(10000.27d));
    }
}
