package com.farm.rest;

import com.farm.database.entities.address.AddressEntity;
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
                .orElseThrow(RuntimeException::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long id) {
        return Optional.of(addressProcess.getOne(id))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @PostMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody AddressEntity addressEntity) {
        return Optional.of(addressEntity)
                .map(curAddress -> addressProcess.save(curAddress))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@RequestBody AddressEntity addressEntity) {
        return Optional.of(addressEntity)
                .map(curAddress -> addressProcess.update(curAddress))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{id}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(curId -> {
                    addressProcess.delete(curId);
                    return ResponseEntity.accepted().build();
                })
                .orElseThrow(RuntimeException::new);
    }
}
