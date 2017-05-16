package com.farm.database.entities.accounts

import com.farm.environment.configuration.FarmPropertySourceConfiguration
import org.springframework.context.MessageSource
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import javax.annotation.Resource

@ContextConfiguration(classes = [FarmPropertySourceConfiguration.class])
class AccountTypeSpec extends Specification {
    @Resource
    @Subject
    MessageSource messageSource

    def setup() {
        Locale.setDefault(Locale.ENGLISH)
    }

    //FIXME test works only with ReloadableResourceBundleMessageSource
    //FIXME but application works only with ResourceBundleMessageSource
    @Unroll
    @Ignore
    def "should create correct bank account description when locale=#locale"() {
        when:
        def description = messageSource.getMessage(input, null, locale)

        then:
        description == expectedOutput

        where:
        input                                              | locale                 | expectedOutput
        AccountType.MONEY_BANK_ACCOUNT_UA.getDescription() | Locale.ENGLISH         | 'Текущие счета в национальнoй валюте 311'
        AccountType.MONEY_BANK_ACCOUNT_UA.getDescription() | new Locale("ru", "RU") | 'Текущие счета в национальнoй валюте 311'
    }
}
