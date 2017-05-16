package com.farm.database.entities.documents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DocumentType {
    BUY_RAW_MATERIALS(Type.EXTERNAL),
    PAY_SALARY(Type.EXTERNAL);

    private DocumentType.Type type;

    public enum Type{
        EXTERNAL,
        INTERNAL
    }
}
