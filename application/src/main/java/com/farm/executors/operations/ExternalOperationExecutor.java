package com.farm.executors.operations;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.operations.*;
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
    private OperationExecutionParametersRepository operationExecutionParametersRepository;
    @NonNull
    private OperationSufficientFundsValidator operationSufficientFundsValidator;
    @NonNull
    private AccountProcess accountProcess;
    @NonNull
    private PartnerProcess partnerProcess;
    @NonNull
    private MessageSource messageSource;

    @Override
    @Transactional
    public OperationExecutionResult execute(@Valid Operation operation) {
        Map<String, String> errors = new HashMap<>();

        Operation originalOperation = operationRepository.findOne(operation.getId());

        if (Objects.isNull(operation.getFactCommitDate())) {
            //TODO try to use properties
            errors.put("factCommitDate", "должно быть задано");
        }
        if (Objects.isNull(originalOperation)) {
            //TODO try to use parameters
            errors.put("id", "Операция с указанным ID не найдена");
        }

        if (Objects.nonNull(originalOperation)) {
            OperationExecutionParameters parameters =
                    operationExecutionParametersRepository.findByOperationType(originalOperation.getOperationType());
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
            @Valid Document document,
            @Valid Account accountFrom) {

        OperationExecutionParameters parameters =
                operationExecutionParametersRepository.findByOperationType(operationType);

        Account accountTo;
        if (Objects.isNull(parameters.getAccountNumberTo())){
            //FIXME check if account to need to be a farm account
            accountTo = accountProcess.createAccount(
                    parameters.getAccountTypeTo(),
                    document.getPartner(),
                    document.getSubject());
        } else {
            accountTo = accountProcess.findByNumber(parameters.getAccountNumberTo());
            if (Objects.isNull(accountTo)){
                return new OperationExecutionResult(null, Collections.singletonMap("accountTo", "not found"));
            }
        }

        Operation result = operationRepository.save(
                createOperationWithoutType(document)
                        .setOperationType(operationType)
                        .setDescription(createOperationMessage(document, parameters.getOperationMessageKey()))
                        .setAccountFrom(accountFrom)
                        .setAccountTo(accountTo)
        );

        return new OperationExecutionResult(result, Collections.emptyMap());
    }

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
