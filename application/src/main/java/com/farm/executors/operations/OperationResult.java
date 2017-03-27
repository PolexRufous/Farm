package com.farm.executors.operations;

import com.farm.database.entities.operations.OperationEntity;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

@Data
public class OperationResult {
    private Map<String, String> errors;
    private OperationEntity result;

    public boolean hasNoErrors() {
        return MapUtils.isEmpty(errors);
    }
}
