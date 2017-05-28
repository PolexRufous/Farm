package com.farm.processes;

import com.farm.database.entities.documents.Document;
import com.farm.database.entities.documents.DocumentRepository;
import com.farm.executors.documents.DocumentExecutionResult;
import com.farm.executors.documents.ExternalDocumentExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentProcess {

    @NonNull
    private ExternalDocumentExecutor externalDocumentExecutor;
    @NonNull
    private DocumentRepository documentRepository;

    public DocumentExecutionResult createExternalDocument(@Valid Document document) {
        return externalDocumentExecutor.executeCreate(document);
    }

    public Document findById(Long id) {
        return Optional.ofNullable(id)
                .map(documentRepository::findOne)
                .orElse(null);
    }

    public Page<Document> getFirsFiveByDate() {
        return documentRepository.findAll(new PageRequest(0, 5, Sort.Direction.ASC, "enterDate"));
    }

    public Page<Document> findFiveByAmount(int pageNumber, BigDecimal amount) {
        return documentRepository.getAllByAmountGreaterThan(
                amount,
                new PageRequest(pageNumber, 5, Sort.Direction.ASC, "enterDate"));
    }
}
