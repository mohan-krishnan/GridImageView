package com.gridimage.codepath.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mkrish4 on 10/17/15.
 */
public class Utils {
    private static final String ANY = "any";

    private static final Map<String, String> SIZE_MAP;

    static {
        /*
        <string-array name="size_array">
        <item>Small</item>
        <item>Medium</item>
        <item>Large</item>
        <item>Extra Large</item>
        </string-array>
        */
        Map<String, String> sizeMap = new HashMap(4);
        sizeMap.put("Small", "icon");
        sizeMap.put("Medium", "large");
        sizeMap.put("Large", "xxlarge");
        sizeMap.put("Extra Large", "huge");
        SIZE_MAP = Collections.unmodifiableMap(sizeMap);
    }

    public static String sizeKey(String sizeValue) {
        return SIZE_MAP.get(sizeValue);
    }

    public static String colorKey(String colorValue) {
        if (colorValue.equals(ANY)) return null;
        return colorValue.toLowerCase();
    }

    public static String typeKey(String typeValue) {
        if (typeValue.equals(ANY)) return null;
        typeValue = typeValue.toLowerCase();
        typeValue = typeValue.replace(" ", "");
        return typeValue;
    }

    public static boolean any(String value) {
        return value == null || value.equals(ANY);
    }
}
