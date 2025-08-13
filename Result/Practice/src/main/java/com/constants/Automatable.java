package com.constants;

import java.util.ArrayList;
import java.util.List;

public interface Automatable {

    String getPropertyKey();

    String getLabel();

    String getFieldType();

    List<String> getSelectionData();



    default String getErrorKey() {
        return getPropertyKey() + "Error";
    }

    static <T extends Enum<T> & Automatable> List<String> getAllPropertyKeys(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        List<String> keys = new ArrayList<>(values.length);

        for (T val : values) {
            keys.add(val.getPropertyKey());
        }

        return List.copyOf(keys); // Immutable
    }


}
