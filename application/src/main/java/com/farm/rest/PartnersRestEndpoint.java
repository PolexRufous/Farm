package com.farm.rest;

import com.farm.database.entities.personality.PartnerEntity;
import com.farm.database.entities.personality.PartnerEntity;
import com.farm.processes.PartnerProcess;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/partner")
public class PartnersRestEndpoint {

    private PartnerProcess partnerProcess;

    @Autowired
    public PartnersRestEndpoint(PartnerProcess partnerProcess) {
        this.partnerProcess = partnerProcess;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return Optional.of(partnerProcess.findAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody PartnerEntity partnerEntity) {
        return Optional.of(partnerEntity)
                .map(curPartner -> partnerProcess.save(curPartner))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@RequestBody PartnerEntity partnerEntity) {
        return Optional.of(partnerEntity)
                .map(curPartner -> partnerProcess.update(curPartner))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{id}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(curId -> {
                    partnerProcess.delete(curId);
                    return ResponseEntity.accepted().build();
                })
                .orElseThrow(RuntimeException::new);
    }
}
