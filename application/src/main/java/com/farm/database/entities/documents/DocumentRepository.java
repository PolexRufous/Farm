package com.farm.database.entities.documents;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DocumentRepository extends CrudRepository<Document, Long> {

}
