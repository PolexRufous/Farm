package com.farm.database.personality;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PartnerRepository extends CrudRepository<Partner, Long> {
    Partner findByName(String name);

    List<Partner> findByNameIgnoreCaseContaining(String partName);

    List<Partner> findTop5ByNameIgnoreCaseContaining(String partName);
}
