package com.farm.rest

import com.farm.database.entities.personality.Partner
import com.farm.database.entities.personality.PartnerRepository
import com.farm.database.processes.PartnerProcess
import spock.lang.Specification


class PartnersRestEndpointTest extends Specification {

    PartnersRestEndpoint partnersRestEndpoint
    PartnerRepository partnerRepository

    def setup() {
        partnerRepository = Mock()
        PartnerProcess partnerProcess = new PartnerProcess(partnerRepository)
        partnersRestEndpoint = new PartnersRestEndpoint(partnerProcess)
    }

    def "should save user"() {
        given: "repository returns user with id = 1"
            Partner partner = new Partner()
            partner.setName("name")
            partner.setDescription("description")
            partnerRepository.save(partner) >> {
                partner.setId(1)
            }
        when:
            partnersRestEndpoint.save(partner)
        then:
            1 * partnerRepository.save(partner)
            partner.getId() == 1

    }

}