package com.farm.database.entities.personality;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PartnerRepository extends CrudRepository<PartnerEntity, Long> {
    PartnerEntity findByName(String name);

    List<PartnerEntity> findByNameIgnoreCaseContaining(String partName);

    List<PartnerEntity> findTop5ByNameIgnoreCaseContaining(String partName);

    @Override
    List<PartnerEntity> findAll();
}
