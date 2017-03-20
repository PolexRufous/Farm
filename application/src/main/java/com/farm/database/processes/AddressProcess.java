package com.farm.database.processes;

import com.farm.database.entities.address.Address;
import com.farm.database.entities.address.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class AddressProcess {
    private AddressRepository addressRepository;

    @Autowired
    public AddressProcess(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public Address save(@Valid Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address update(Address donorAddress) {
        Address address = addressRepository.findOne(donorAddress.getId());
        address.update(donorAddress);
        return addressRepository.save(address);
    }

    public void delete(Long curId) {
        addressRepository.delete(curId);
    }
}
