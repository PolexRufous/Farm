package com.farm.database.utilits

import com.farm.database.entities.operations.Operation
import spock.lang.Specification

class FarmEntityValidatorSpec extends Specification {

    def 'should return errors when validate newly created entity'() {
        given: 'newly created operation with no fields set'
        def operation = new Operation()

        when:
        def errors = FarmEntityValidator.getValidationErrors(operation)

        then:
        errors.size() == 4
    }

}