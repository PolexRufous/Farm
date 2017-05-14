package com.farm.executors.operations.external;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.database.entities.operations.OperationType;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.executors.operations.OperationExecutor;
import com.farm.executors.validators.OperationSufficientFundsValidator;
import com.farm.processes.AccountProcess;
import com.farm.processes.PartnerProcess;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalOperationExecutor implements OperationExecutor {

    @NonNull
    private OperationRepository operationRepository;
    @NonNull
    private Map<OperationType, ExternalOperationParameters> operationTypeExternalOperationParametersMap;
    @NonNull
    private OperationSufficientFundsValidator operationSufficientFundsValidator;
    @NonNull
    private AccountProcess accountProcess;
    @NonNull
    private PartnerProcess partnerProcess;
    @NonNull
    private MessageSource messageSource;

    @Override
    public OperationExecutionResult execute(@Valid Operation operation) {
        Map<String, String> errors = new HashMap<>();

        Operation originalOperation = operationRepository.findOne(operation.getId());

        if (Objects.isNull(operation.getFactCommitDate())) {
            //FIXME try to use properties
            errors.put("factCommitDate", "должно быть задано");
        }
        if (Objects.isNull(originalOperation)) {
            //FIXME try to use parameters
            errors.put("id", "Операция с указанным ID не найдена");
        }

        //FIXME implement parameters container into database
        if (Objects.nonNull(originalOperation)) {
            ExternalOperationParameters parameters =
                    operationTypeExternalOperationParametersMap.get(originalOperation.getOperationType());
            //TODO create implementation of different amounts which don't mach original amount
            if (parameters.isCheckFundsNeeded()) {
                errors.putAll(operationSufficientFundsValidator.validate(
                        originalOperation.getAccountFrom(),
                        originalOperation.getAmount()));
            }
            if (Objects.nonNull(originalOperation.getFactCommitDate())) {
                errors.put("id", "Операция с указанным ID уже проведена");
            }
        }

        if (MapUtils.isNotEmpty(errors)) {
            return new OperationExecutionResult(null, errors);
        }

        originalOperation.setFactCommitDate(operation.getFactCommitDate());
        transferFunds(
                originalOperation.getAccountFrom(),
                originalOperation.getAccountTo(),
                originalOperation.getAmount()
        );

        return new OperationExecutionResult(operationRepository.save(originalOperation), Collections.emptyMap());
    }

    @Override
    public OperationExecutionResult executeCreate(
            OperationType operationType,
            Document document,
            Account accountFrom) {

        ExternalOperationParameters parameters =
                operationTypeExternalOperationParametersMap.get(operationType);


        Account accountTo = Optional.ofNullable(parameters.getAccountNumberTo())
                .map(accountProcess::findByNumber)
                .orElse(
                        accountProcess.createAccount(
                                parameters.getAccountTypeTo(),
                                document.getPartner(),
                                document.getSubject()));

        Operation result = operationRepository.save(
                createOperationWithoutType(document)
                        .setOperationType(operationType)
                        .setDescription(createOperationMessage(document, parameters.getOperationMessageKey()))
                        .setAccountFrom(accountFrom)
                        .setAccountTo(accountTo)
        );

        return new OperationExecutionResult(result, Collections.emptyMap());
    }

    @Transactional
    private void transferFunds(Account accountFrom, Account accountTo, BigDecimal amount) {
        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        accountTo.setBalance(accountTo.getBalance().add(amount));
        accountProcess.saveAccount(accountTo);
        accountProcess.saveAccount(accountFrom);
    }

    private Operation createOperationWithoutType(Document document) {
        return new Operation()
                .setAmount(document.getAmount())
                .setEnterDate(document.getEnterDate())
                .setExpectedCommitDate(document.getExpectedCommitDate())
                .setPartner(partnerProcess.findById(document.getPartnerId()));
    }

    private String createOperationMessage(Document document, String messageKey) {
        return messageSource.getMessage(messageKey,
                new Object[]{document.getSubject(), document.getDescription()},
                Locale.getDefault());
    }
}
