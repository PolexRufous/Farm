package com.farm.rest;

import com.farm.database.entities.personality.Partner;
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
    @CrossOrigin
    public ResponseEntity getAll() {
        return Optional.of(partnerProcess.findAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity get(@PathVariable Long id) {
        return Optional.of(partnerProcess.findById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @CrossOrigin
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Partner partner) {
        return Optional.of(partner)
                .map(partnerProcess::save)
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping
    @CrossOrigin
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@RequestBody Partner partner) {
        return Optional.of(partner)
                .map(partnerProcess::update)
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
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
