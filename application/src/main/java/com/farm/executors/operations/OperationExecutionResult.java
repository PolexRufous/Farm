package com.farm.executors.operations;

import com.farm.database.entities.operations.Operation;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

@Data
public class OperationExecutionResult {
    private final Operation result;
    private final Map<String, String> errors;

    public boolean hasNoErrors() {
        return MapUtils.isEmpty(errors);
    }
}
