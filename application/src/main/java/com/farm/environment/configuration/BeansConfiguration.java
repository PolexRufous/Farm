package com.farm.environment.configuration;

import com.farm.database.entities.documents.DocumentType;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationType;
import com.farm.executors.documents.external.BuyRawMaterialsDocumentExecutorParameters;
import com.farm.executors.documents.external.ExternalDocumentExecutorParameters;
import com.farm.executors.documents.external.PaySalaryDocumentExecutorParameters;
import com.farm.executors.operations.external.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.farm"
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeansConfiguration {

    @NonNull
    private ApplicationContext applicationContext;

    @Bean
    public Map<DocumentType, ExternalDocumentExecutorParameters> documentTypeDocumentExecutorExternalParametersMap() {
        Map<DocumentType, ExternalDocumentExecutorParameters> documentExecutorMap =
                new EnumMap<>(DocumentType.class);
        documentExecutorMap.put(DocumentType.BUY_RAW_MATERIALS,
                applicationContext.getBean(BuyRawMaterialsDocumentExecutorParameters.class));
        documentExecutorMap.put(DocumentType.PAY_SALARY,
                applicationContext.getBean(PaySalaryDocumentExecutorParameters.class));
        return documentExecutorMap;
    }

    @Bean
    public Map<OperationType, ExternalOperationParameters> operationTypeExternalOperationParametersMap() {
        Map<OperationType, ExternalOperationParameters> operationParametersMap =
                new EnumMap<>(OperationType.class);
        operationParametersMap.put(OperationType.PAY_FOR_RAW_MATERIALS_CASH_UA,
                applicationContext.getBean(PayForRawMaterialsOperationExecutionParameters.class));
        operationParametersMap.put(OperationType.RECEIVE_RAW_MATERIALS_UA,
                applicationContext.getBean(ReceiveRawMaterialsOperationExecutionParameters.class));
        operationParametersMap.put(OperationType.PAY_SALARY_CASH_UA,
                applicationContext.getBean(PaySalaryOperationExecutionParameters.class));
        operationParametersMap.put(OperationType.POSTING_OF_SALARY,
                applicationContext.getBean(PostingOfSalaryOperationExecutionParameters.class));
        return operationParametersMap;

    }

}
