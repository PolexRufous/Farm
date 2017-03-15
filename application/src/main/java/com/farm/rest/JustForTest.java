package com.farm.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/rest/test")
public class JustForTest {

    @GetMapping
    @ResponseBody
    @Produces(value = "application/json")
    public ResponseEntity getAll(){
        Map<String, String> result = new TreeMap<>();
        result.put("One", "First");
        result.put("Two", "Second");
        return ResponseEntity.status(200).body(result);
    }
}
