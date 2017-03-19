package com.farm.rest

import com.farm.database.entities.personality.Partner
import com.farm.database.entities.personality.PartnerRepository
import com.farm.database.processes.PartnerProcess
import spock.lang.Specification
import spock.lang.Subject


class PartnersRestEndpointSpec extends Specification {

    @Subject
    PartnersRestEndpoint partnersRestEndpoint
    PartnerRepository partnerRepository

    def setup() {
        partnerRepository = Mock(PartnerRepository)
        PartnerProcess partnerProcess = new PartnerProcess(partnerRepository)
        partnersRestEndpoint = new PartnersRestEndpoint(partnerProcess)
    }

    def "should save user"() {
        given:
        Partner partner = new Partner(
                name: "name",
                description: "description"
        )
        Partner savedPartner = new Partner(
                id: 1L,
                name: "name",
                description: "description"

        )
        partnerRepository.save(_ as Partner) >> savedPartner

        when:
        Partner responsePartner = (Partner) partnersRestEndpoint.save(partner).getBody()

        then:
        responsePartner.getId() == 1L
    }
}