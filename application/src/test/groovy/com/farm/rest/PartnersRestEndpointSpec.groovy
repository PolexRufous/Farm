package com.farm.rest

import com.farm.database.entities.address.AddressRepository
import com.farm.database.entities.personality.Partner
import com.farm.database.entities.personality.PartnerRepository
import com.farm.processes.AddressProcess
import com.farm.processes.PartnerProcess
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Subject


class PartnersRestEndpointSpec extends Specification {

    @Subject
    partnersRestEndpoint
    def partnerRepository
    def addressRepository

    def setup() {
        partnerRepository = Mock(PartnerRepository);
        addressRepository = Mock(AddressRepository);
        def addressProcess = new AddressProcess(addressRepository);
        def partnerProcess = new PartnerProcess(partnerRepository, addressProcess)
        partnersRestEndpoint = new PartnersRestEndpoint(partnerProcess)
    }

    def 'should save partner'() {
        given:
        def partner = new Partner(name: 'name',description: 'description')
        def savedPartner = new Partner(id: 1L,name: 'name',description: 'description')
        partnerRepository.save(_ as Partner) >> savedPartner

        when:
        def responsePartner = partnersRestEndpoint.save(partner).getBody()

        then:
        responsePartner.getId() == 1L
    }

    def 'should return all partners'() {
        given:
        def partners = [new Partner(name: 'name1',description: 'description1'),new Partner(name: 'name2',description: 'description2')]
        partnerRepository.findAll() >> partners

        when:
        def result = partnersRestEndpoint.getAll().getBody()

        then:
        result == partners
    }

    def 'should return partner by id'() {
        given:
        def partnerId = 1L
        def partner = new Partner(id: partnerId, name: 'name1',description: 'description1')
        partnerRepository.findOne(partnerId) >> partner

        when:
        def result = partnersRestEndpoint.get(partnerId).getBody()

        then:
        result == partner
    }

    def "should return edited partner"() {
        def partnerId = 1L
        def partner = new Partner(id: partnerId, name: 'name',description: 'description', addresses: [])
        def savedPartner = new Partner(id: partnerId, name: 'name2',description: 'description2', addresses: [])
        partnerRepository.findOne(partnerId) >> partner
        partnerRepository.save(_ as Partner) >> savedPartner

        when:
        def result = partnersRestEndpoint.edit(partner).getBody()

        then:
        result == savedPartner
    }

    def "should return deleted partner"() {
        def partnerId = 1L

        when:
        def response = partnersRestEndpoint.delete(partnerId)

        then:
        response.getStatusCode() == HttpStatus.ACCEPTED
    }
}