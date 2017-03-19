package com.farm.rest;

import com.farm.database.processes.OperationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/operation")
public class OperationsRestEndpoint {

    private OperationProcess operationProcess;

    @Autowired
    public OperationsRestEndpoint(OperationProcess operationProcess) {
        this.operationProcess = operationProcess;
    }


}
