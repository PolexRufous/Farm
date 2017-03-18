package com.farm.rest;

import com.farm.database.entities.personality.Partner;
import com.farm.database.processes.PartnerProcess;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.Optional;

@RestController
@RequestMapping("/rest/partner")
public class PartnersRestEndpoint {

    @Resource
    private PartnerProcess partnerProcess;

    @GetMapping
    @ResponseBody
    @Produces(value = "application/json")
    public ResponseEntity getAll() {
        return Optional.of(partnerProcess.findAll())
                .filter(CollectionUtils::isNotEmpty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @ResponseBody
    @Consumes(value = "application/json")
    @Produces(value = "application/json")
    public ResponseEntity save(@RequestBody Partner partner) {
        return Optional.of(partner)
                .map(curPartner -> partnerProcess.save(curPartner))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Consumes(value = "application/json")
    @Produces(value = "application/json")
    public ResponseEntity edit(@PathVariable("id") Long id, @RequestBody Partner partner) {
        return Optional.of(partner)
                .map(curPartner -> {
                    curPartner.setId(id);
                    return curPartner;
                })
                .map(curPartner -> partnerProcess.update(curPartner))
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Consumes(value = "application/json")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(curId -> {
                    partnerProcess.delete(curId);
                    return ResponseEntity.accepted().build();
                })
                .orElseThrow(RuntimeException::new);
    }
}
