package com.farm.rest;

import com.farm.database.entities.address.Address;
import com.farm.processes.AddressProcess;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/address",  produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressRestEndpoint {
    private AddressProcess addressProcess;

    @Autowired
    public AddressRestEndpoint(AddressProcess addressProcess) {
        this.addressProcess = addressProcess;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return Optional.of(addressProcess.getAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long id) {
        return Optional.of(addressProcess.getOne(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Address address) {
        return Optional.of(address)
                .map(addressProcess::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@RequestBody Address address) {
        return Optional.of(address)
                .map(addressProcess::update)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(curId -> {
                    addressProcess.delete(curId);
                    return ResponseEntity.accepted().build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }
}
