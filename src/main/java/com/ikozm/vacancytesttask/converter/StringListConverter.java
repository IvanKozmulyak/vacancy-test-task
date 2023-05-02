package com.ikozm.vacancytesttask.converter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.*;

import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * A converter class that converts a list of strings to a semicolon-separated string for storage in the database,
 * <p>
 * and converts a semicolon-separated string to a list of strings for use in the application.
 */
@Converter
@Component
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }
}