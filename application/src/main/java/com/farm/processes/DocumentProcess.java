package com.farm.processes;

import com.farm.database.entities.documents.Document;
import com.farm.database.entities.documents.DocumentRepository;
import com.farm.executors.documents.DocumentExecutionResult;
import com.farm.executors.documents.ExternalDocumentExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

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
}
