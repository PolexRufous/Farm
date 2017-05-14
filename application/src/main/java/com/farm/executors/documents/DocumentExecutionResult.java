package com.farm.executors.documents;

import com.farm.database.entities.documents.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

@Getter
@AllArgsConstructor
public class DocumentExecutionResult {
    private Document document;
    private Map<String, String> errors;

    public boolean hasNoErrors() {
        return MapUtils.isEmpty(errors);
    }

}
