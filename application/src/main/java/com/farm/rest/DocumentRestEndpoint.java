package com.farm.rest;

import com.farm.database.entities.documents.Document;
import com.farm.database.utilits.FarmEntityValidator;
import com.farm.executors.documents.DocumentExecutionResult;
import com.farm.processes.DocumentProcess;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/document")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentRestEndpoint {

    private DocumentProcess documentProcess;

    @PostMapping
    @CrossOrigin
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createDocument(@RequestBody Document document){

        Map<String, String> errorsMap = FarmEntityValidator.getValidationErrors(document);
        if (MapUtils.isNotEmpty(errorsMap))
            return ResponseEntity.badRequest().body(errorsMap);

        DocumentExecutionResult result = documentProcess.createExternalDocument(document);
        if (result.hasNoErrors()){
            return ResponseEntity.ok(result.getDocument());
        } else {
            return ResponseEntity.badRequest().body(result.getErrors());
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity getById(@PathVariable Long id){
        return Optional.ofNullable(documentProcess.findById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/page")
    @CrossOrigin
    public ResponseEntity getFirstFiveByDate() {
        return Optional.of(documentProcess.getFirsFiveByDate())
                .filter(Slice::hasContent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/amount/{amount}/{pageNumber}")
    @CrossOrigin
    public ResponseEntity getFiveByAmount(@PathVariable double amount, @PathVariable int pageNumber) {
        return Optional.of(documentProcess.findFiveByAmount(pageNumber, BigDecimal.valueOf(amount)))
                .filter(Slice::hasContent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
