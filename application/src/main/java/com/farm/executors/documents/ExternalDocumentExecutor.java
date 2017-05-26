package com.farm.executors.documents;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.documents.DocumentExecutionParameters;
import com.farm.database.entities.documents.DocumentExecutionParametersRepository;
import com.farm.database.entities.documents.DocumentRepository;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.personality.Partner;
import com.farm.executors.operations.OperationExecutor;
import com.farm.processes.AccountProcess;
import com.farm.processes.PartnerProcess;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalDocumentExecutor implements DocumentExecutor {

    private static final String PARAMETERS_KEY = "parameters";

    @Value("${validation.error.not.exist}")
    private String isNotExist;

    @NonNull
    private PartnerProcess partnerProcess;
    @NonNull
    private DocumentRepository documentRepository;
    @NonNull
    private OperationExecutor operationExecutor;
    @NonNull
    private AccountProcess accountProcess;
    @NonNull
    private DocumentExecutionParametersRepository documentExecutionParametersRepository;

    @Override
    public DocumentExecutionResult executeCreate(@Valid Document document) {

        DocumentExecutionParameters parameters =
                documentExecutionParametersRepository.findByDocumentType(document.getDocumentType());
        Map<String, String> errors = validate(document, parameters);

        Account accountFrom = null;
        if (Objects.nonNull(parameters) && parameters.isStartAccountNeeded()){
            accountFrom = Optional.ofNullable(document.getStartAccountNumber())
                    .map(accountProcess::findByNumber)
                    .orElse(null);
            if (Objects.isNull(accountFrom)){
                errors.put("startAccountNumber", isNotExist);
            }
        }

        if (MapUtils.isNotEmpty(errors)){
            return new DocumentExecutionResult(null, errors);
        }

        //TODO implement if errors of creation of operations are possible
        Operation payment = operationExecutor
                .executeCreate(parameters.getPaymentOperationType(), document, accountFrom)
                .getResult();
        Operation receiving = operationExecutor
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

    private Map<String, String> validate(Document document, DocumentExecutionParameters parameters){
        Map<String, String> errors = new HashMap<>();
        if (!checkAndSetPartner(document)) {
            errors.put("partner", isNotExist);
        }
        if (Objects.isNull(parameters)){
            errors.put(PARAMETERS_KEY, "Parameters for current document type execution were not found");
        }

        if (Objects.nonNull(parameters)){
            if (Objects.isNull(parameters.getPaymentOperationType())){
                errors.put(PARAMETERS_KEY, "Payment operation type is not defined into parameters");
            } else if (Objects.isNull(parameters.getReceiveOperationType())){
                errors.put(PARAMETERS_KEY, "Receive operation type is not defined into parameters");
            }
        }
        return errors;
    }
}
