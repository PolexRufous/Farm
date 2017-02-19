package com.farm.database.personality;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PartnerRepository extends CrudRepository<Partner, Long> {
}
