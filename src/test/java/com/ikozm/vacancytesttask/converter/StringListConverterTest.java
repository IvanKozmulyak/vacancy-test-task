package com.ikozm.vacancytesttask.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringListConverterTest {
    private static final StringListConverter converter = new StringListConverter();

    @Test
    public void testConvertToDatabaseColumn() {
        List<String> stringList = Arrays.asList("abc", "def", "ghi");
        String result = converter.convertToDatabaseColumn(stringList);
        assertEquals("abc;def;ghi", result);
    }

    @Test
    public void testConvertToDatabaseColumnWithNull() {
        List<String> stringList = null;
        String result = converter.convertToDatabaseColumn(stringList);
        assertEquals("", result);
    }

    @Test
    public void testConvertToEntityAttribute() {
        String string = "abc;def;ghi";
        List<String> result = converter.convertToEntityAttribute(string);
        List<String> expected = Arrays.asList("abc", "def", "ghi");
        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }

    @Test
    public void testConvertToEntityAttributeWithNull() {
        String string = null;
        List<String> result = converter.convertToEntityAttribute(string);
        assertTrue(result.isEmpty());
    }
}
