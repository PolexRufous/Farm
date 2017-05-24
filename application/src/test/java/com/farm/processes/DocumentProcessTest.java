package com.farm.processes;

import com.farm.database.entities.documents.Document;
import com.farm.database.entities.documents.DocumentRepository;
import com.farm.executors.documents.DocumentExecutionResult;
import com.farm.executors.documents.ExternalDocumentExecutor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class DocumentProcessTest {

    @Mock
    private ExternalDocumentExecutor externalDocumentExecutor;
    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentProcess documentProcess;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters
    @TestCaseName("should only delegate document creation functionality")
    public void createExternalDocument(Document document, DocumentExecutionResult result) throws Exception {
        //given
        when(externalDocumentExecutor.executeCreate(document)).thenReturn(result);

        //when
        DocumentExecutionResult result1 = documentProcess.createExternalDocument(document);

        //then
        verify(externalDocumentExecutor, times(1)).executeCreate(document);
        verifyNoMoreInteractions(externalDocumentExecutor);
        assertEquals(document, result1.getDocument());
        assertEquals(result.hasNoErrors(), result1.hasNoErrors());
    }

    @Test
    @Parameters
    @TestCaseName("should return expected result when findById is called with {0}")
    public void findById(Long id, Document returnedDocument) throws Exception {
        //given
        when(documentRepository.findOne(anyLong())).thenReturn(returnedDocument);

        //when
        Document result = documentProcess.findById(id);

        //then
        if (Objects.nonNull(id)) {
            verify(documentRepository, times(1)).findOne(anyLong());
            if (Objects.nonNull(returnedDocument)) {
                assertEquals(returnedDocument, result);
            }
        }
        verifyNoMoreInteractions(documentRepository);

    }

    private Object[] parametersForCreateExternalDocument() {
        return new Object[]{
                new Document().setId(1L),
                new DocumentExecutionResult(new Document().setId(1L), Collections.emptyMap())
        };
    }

    private Object[] parametersForFindById() {
        return new Object[]{
                new Object[]{1L, null},
                new Object[]{2L, new Document().setId(2L)},
                new Object[]{null, null}
        };
    }

}