package com.farm.database.entities.documents;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

    Page<Document> findAllByAmountGreaterThan(BigDecimal amount, Pageable pageable);
    Page<Document> findAllByOrderByEnterDateAsc(Pageable pageable);
    Page<Document> findAllByOrderByEnterDateDesc(Pageable pageable);
}
