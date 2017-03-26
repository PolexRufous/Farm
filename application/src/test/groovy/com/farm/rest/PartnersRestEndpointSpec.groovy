package com.farm.rest

import com.farm.database.entities.personality.Partner
import com.farm.database.entities.personality.PartnerRepository
import com.farm.processes.PartnerProcess
import spock.lang.Specification
import spock.lang.Subject


class PartnersRestEndpointSpec extends Specification {

    @Subject
    partnersRestEndpoint
    def partnerRepository

    def setup() {
        partnerRepository = Mock(PartnerRepository)
        def partnerProcess = new PartnerProcess(partnerRepository)
        partnersRestEndpoint = new PartnersRestEndpoint(partnerProcess)
    }

    def 'should save user'() {
        given:
        def partner = new Partner(name: 'name',description: 'description')
        def savedPartner = new Partner(id: 1L,name: 'name',description: 'description')
        partnerRepository.save(_ as Partner) >> savedPartner

        when:
        def responsePartner = partnersRestEndpoint.save(partner).getBody()

        then:
        responsePartner.getId() == 1L
    }
}