package com.farm.database.accounts;

import com.farm.environment.configuration.FarmPropertySourceConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        FarmPropertySourceConfiguration.class
})
public class AccountTypeTest
{
  @Resource
  private MessageSource messageSource;

  @Before
  public void setUp() throws Exception{
    Locale.setDefault(Locale.ENGLISH);
  }

  @Test
  public void testBankAccountDescription() throws Exception
  {
    //when
    String accDescriptionEn = messageSource.getMessage(AccountType.BANK_ACCOUNT_UA.getDescription(),
            null, "", Locale.ENGLISH);
    String accDescriptionRu = messageSource.getMessage(AccountType.BANK_ACCOUNT_UA.getDescription(),
            null, "", new Locale("ru", "RU"));
    //then
    assertEquals("Main bank account", accDescriptionEn);
    assertEquals("Основной счет в банке", accDescriptionRu);
  }
}
