package com.eharrison.automata;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.lang.reflect.Array;

@UtilityClass
public class Arrays {
    @SuppressWarnings("unchecked")
    public static <T> T[][] deepCopy(final Class<T> type, final T[][] array) {
        val newArray = (T[][]) Array.newInstance(type, array.length, array[0].length);
        for (int i = 0; i < array.length; i++) {
            newArray[i] = java.util.Arrays.copyOf(array[i], array[i].length);
        }
        return newArray;
    }
}
