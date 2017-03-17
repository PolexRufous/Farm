package com.farm.database.entities.accounts

import com.farm.environment.configuration.FarmDatabaseTest
import com.github.springtestdbunit.annotation.ExpectedDatabase
import spock.lang.Specification

//@FarmDatabaseTest
class GlobalDatabaseSpec extends Specification {

    //@ExpectedDatabase(value = "/database/tables/emptyDatabase.xml")
    def "good test" () {
        expect:
        1==1
    }
}