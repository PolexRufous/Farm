package com.farm.rest;

import static com.farm.database.utilits.FarmEntityValidator.getValidationErrors;

import com.farm.database.entities.operations.Operation;
import com.farm.database.processes.OperationProcess;
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
    public ResponseEntity getAll() {
        return Optional.of(operationProcess.findAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Operation operation) {
        Map<String, String> errorsMap = getValidationErrors(operation);
        if (MapUtils.isEmpty(errorsMap)) {
            return Optional.of(operation)
                    .map(curOperation -> operationProcess.save(curOperation))
                    .map(ResponseEntity::ok)
                    .orElseThrow(RuntimeException::new);
        } else {
            return ResponseEntity.badRequest().body(errorsMap);
        }
    }
}
