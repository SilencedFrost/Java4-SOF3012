package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class CustomFormFields implements Automatable{

    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> cBoxData;
}
