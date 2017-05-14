package com.farm.executors.documents;

import com.farm.database.entities.documents.Document;

public interface DocumentExecutor {
    DocumentExecutionResult executeCreate(Document document);
}
