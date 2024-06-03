package com.baeldung.rwsb.commons.endtoend.spec;

import java.util.List;

public interface DtoSpec<T> {
    
    List<DtoFieldSpec<T, ?>> defineSpecs();

    /**
     * 
     * Method to check if the spec matches a particular DTO.
     * 
     * @param expectedDto DTO to evaluate against the Spec.
     * @return list of mismatching fields
     */
    public default List<String> matches(T expectedDto){
        return defineSpecs().stream()
            .filter(fieldSpec -> !fieldSpec.matches(expectedDto))
            .map(fieldSpec -> fieldSpec.getFieldName())
            .toList();
    }

}
