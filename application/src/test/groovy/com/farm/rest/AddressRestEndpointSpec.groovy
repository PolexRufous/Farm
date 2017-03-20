package com.farm.rest

import com.farm.database.entities.address.Address
import com.farm.database.entities.address.AddressRepository
import com.farm.database.processes.AddressProcess
import spock.lang.Specification
import spock.lang.Subject

class AddressRestEndpointSpec extends Specification {
    @Subject
    addressRestEndpointSpec
    def addressRepository

    void setup() {
        addressRepository = Stub(AddressRepository)
        def addressProcess = new AddressProcess(addressRepository)
        addressRestEndpointSpec = new AddressRestEndpoint(addressProcess)
    }

    def 'should save address'() {
        given:
        def address = new Address(town:'Town')
        def savedAddress = new Address(id:1L, town:'Town')
        addressRepository.save(address) >> savedAddress

        when:
        def responseAddress = addressRestEndpointSpec.save(address).getBody()

        then:
        responseAddress.id == 1L
        responseAddress.town == 'Town'
    }

    def 'should get all addresses'() {
        given:
        def addresses = [new Address(id:1L, town:'Town1'), new Address(id:2L, town:'Town2')]
        addressRepository.findAll() >> addresses

        when:
        def responseAddresses = addressRestEndpointSpec.getAll().getBody()

        then:
        responseAddresses == addresses
    }

    def 'should edit address'() {
        given:
        def incomingAddress = new Address(id:1L, town: 'Town1')
        def storedAddress = new Address(id:1L, town: 'Town0')

        addressRepository.findOne(1L) >> storedAddress
        addressRepository.save(storedAddress) >> storedAddress

        when:
        def responseAddress = addressRestEndpointSpec.edit(incomingAddress).getBody()

        then:
        responseAddress == incomingAddress
    }
}
