package com.farm.database.entities.address;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    @Override
    List<AddressEntity> findAll();
}
