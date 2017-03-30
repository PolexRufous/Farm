package com.farm.rest;

import com.farm.database.entities.operations.Operation;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.processes.OperationProcess;
import com.farm.database.utilits.FarmEntityValidator;
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
public class OperationsRestEndpoint {

    private OperationProcess operationProcess;

    @Autowired
    public OperationsRestEndpoint(OperationProcess operationProcess) {
        this.operationProcess = operationProcess;
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity getAll() {
        return Optional.of(operationProcess.findAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @CrossOrigin
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Operation operation) {
        operationProcess.fillOperation(operation);
        Map<String, String> errorsMap = FarmEntityValidator.getValidationErrors(operation);
        if (MapUtils.isNotEmpty(errorsMap))
            return ResponseEntity.badRequest().body(errorsMap);

        OperationExecutionResult executionResult = operationProcess.save(operation);
        errorsMap.putAll(executionResult.getErrors());
        if (MapUtils.isEmpty(errorsMap)) {
            return Optional.of(operation)
                    .map(curOperation -> executionResult.getResult())
                    .map(ResponseEntity::ok)
                    .orElseThrow(RuntimeException::new);
        }

        return ResponseEntity.badRequest().body(errorsMap);
    }
}
