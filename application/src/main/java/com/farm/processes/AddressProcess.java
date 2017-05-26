package com.farm.processes;

import com.farm.database.entities.address.Address;
import com.farm.database.entities.address.AddressRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressProcess {

    @NonNull
    private AddressRepository addressRepository;

    //TODO if address already contains id
    public Address save(@Valid Address address) {
        return Optional.ofNullable(address)
                .map(addressRepository::save)
                .orElse(null);
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address update(@Valid Address donorAddress) {
        Address address = Optional.ofNullable(donorAddress)
                .map(Address::getId)
                .map(addressRepository::findOne)
                .orElse(donorAddress);

        return Optional.ofNullable(address)
                .map(address1 -> address1.update(donorAddress))
                .map(addressRepository::save)
                .orElse(null);
    }

    public void delete(Long curId) {
        Optional.ofNullable(curId)
                .ifPresent(addressRepository::delete);
    }

    public Address getOne(Long id) {
        return Optional.ofNullable(id)
                .map(addressRepository::findOne)
                .orElse(null);
    }
}