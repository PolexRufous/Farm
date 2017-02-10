package com.farm.database.kassa;

import com.farm.environment.configuration.FarmPropertySource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

/**
 * Farm project. 2017
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        FarmPropertySource.class
})
public class AccountTypeTest
{
  @Resource
  private MessageSource messageSource;

  @Test
  public void testBankAccountDescription() throws Exception
  {
    //when
    String accDescriptionEn = messageSource.getMessage(AccountType.BANK_ACCOUNT.getDescription(),
            null, "", Locale.ENGLISH);
    String accDescriptionRu = messageSource.getMessage(AccountType.BANK_ACCOUNT.getDescription(),
            null, "", new Locale("ru", "RU"));
    //then
    assertEquals("Main bank account", accDescriptionEn);
    assertEquals("Основной счет в банке", accDescriptionRu);
  }
}
