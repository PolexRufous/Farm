package com.farm.rest;

import com.farm.database.entities.operations.Operation;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.processes.OperationProcess;
import com.farm.database.utilits.FarmEntityValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/operation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OperationsRestEndpoint {

    @NonNull
    private OperationProcess operationProcess;

    @GetMapping
    @CrossOrigin
    public ResponseEntity getAll() {
        return Optional.of(operationProcess.findAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity getById(@PathVariable Long id) {
        return Optional.ofNullable(operationProcess.findById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/partner/{partner_id}")
    @CrossOrigin
    public ResponseEntity getByPartnerId(@PathVariable Long partner_id) {
        return Optional.of(operationProcess.findByPartnerId(partner_id))
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/document/{documentId}")
    @CrossOrigin
    public ResponseEntity getByDocumentId(@PathVariable Long documentId) {
        return Optional.of(operationProcess.findByDocumentId(documentId))
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @CrossOrigin
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody Operation operation) {
        operationProcess.fillOperationPartner(operation);
        Map<String, String> errorsMap = FarmEntityValidator.getValidationErrors(operation);
        if (MapUtils.isEmpty(errorsMap)){
            OperationExecutionResult executionResult = operationProcess.execute(operation);
            if (executionResult.hasNoErrors()) {
                return Optional.of(executionResult.getResult())
                        .map(ResponseEntity::ok)
                        .orElseThrow(RuntimeException::new);
            } else {
                errorsMap.putAll(executionResult.getErrors());
            }
        }
        return ResponseEntity.badRequest().body(errorsMap);
    }
}
