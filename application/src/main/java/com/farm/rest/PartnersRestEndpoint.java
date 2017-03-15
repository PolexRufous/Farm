package com.farm.rest;

import com.farm.database.entities.personality.Partner;
import com.farm.database.processes.PartnerProcess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

@RestController
@RequestMapping("/rest/partner")
public class PartnersRestEndpoint {

    @Resource
    private PartnerProcess partnerProcess;

    @GetMapping
    @ResponseBody
    @Produces(value = "application/json")
    public ResponseEntity getAll(){
        List<Partner> partners = partnerProcess.findAll();
        if (partners.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(partners);
    }

    @PostMapping
    @ResponseBody
    @Consumes(value = "application/json")
    @Produces(value = "application/json")
    public ResponseEntity save(@RequestBody Partner partner) {
        if (partner != null) {
            Partner responsePartner = partnerProcess.save(partner);
            return ResponseEntity.status(201).body(responsePartner);
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
