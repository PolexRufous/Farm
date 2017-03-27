package com.farm.processes;

import com.farm.database.entities.address.AddressEntity;
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


    public AddressEntity save(@Valid AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    public List<AddressEntity> getAll() {
        return addressRepository.findAll();
    }

    public AddressEntity update(AddressEntity donorAddressEntity) {
        AddressEntity addressEntity = addressRepository.findOne(donorAddressEntity.getId());
        addressEntity.update(donorAddressEntity);
        return addressRepository.save(addressEntity);
    }

    public void delete(Long curId) {
        addressRepository.delete(curId);
    }

    public AddressEntity getOne(Long id) {
        return addressRepository.findOne(id);
    }
}
