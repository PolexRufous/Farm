package com.farm.executors.documents.external;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.documents.DocumentRepository;
import com.farm.database.entities.documents.DocumentType;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.database.entities.personality.Partner;
import com.farm.executors.documents.DocumentExecutionResult;
import com.farm.executors.documents.DocumentExecutor;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.executors.operations.external.ExternalOperationExecutor;
import com.farm.processes.AccountProcess;
import com.farm.processes.PartnerProcess;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalDocumentExecutor implements DocumentExecutor {

    @Value("${validation.error.not.exist}")
    private String IS_NOT_EXIST;

    @NonNull
    private PartnerProcess partnerProcess;
    @NonNull
    private DocumentRepository documentRepository;
    @NonNull
    private ExternalOperationExecutor externalOperationExecutor;
    @NonNull
    private AccountProcess accountProcess;
    @NonNull
    private Map<DocumentType, ExternalDocumentExecutorParameters> documentTypeDocumentExecutorExternalParametersMap;

    @Override
    public DocumentExecutionResult executeCreate(@Valid Document document) {

        //TODO create validate method instead of this ugly code
        Map<String, String> errors = new HashMap<>();

        if (!checkAndSetPartner(document)) {
            errors.put("partner", IS_NOT_EXIST);
        }

        ExternalDocumentExecutorParameters parameters =
                documentTypeDocumentExecutorExternalParametersMap.get(document.getDocumentType());
        Account accountFrom = null;
        if (parameters.isStartAccountNeeded()){
            accountFrom = Optional.ofNullable(document.getStartAccountNumber())
                    .map(accountProcess::findByNumber)
                    .orElse(null);
            if (Objects.isNull(accountFrom)){
                errors.put("startAccountNumber", IS_NOT_EXIST);
            }
        }

        if (MapUtils.isNotEmpty(errors)){
            return new DocumentExecutionResult(null, errors);
        }

        Operation payment = externalOperationExecutor
                .executeCreate(parameters.getPaymentOperationType(), document, accountFrom)
                .getResult();
        Operation receiving = externalOperationExecutor
                .executeCreate(parameters.getReceiveOperationType(), document, payment.getAccountTo())
                .getResult();

        document.setOperations(Arrays.asList(payment, receiving));

        Document resultDocument = documentRepository.save(document);
        return new DocumentExecutionResult(resultDocument, Collections.emptyMap());
    }

    private boolean checkAndSetPartner(Document document) {
        Partner partner = partnerProcess.findById(document.getPartnerId());
        document.setPartner(partner);
        return Objects.nonNull(partner);
    }
}
