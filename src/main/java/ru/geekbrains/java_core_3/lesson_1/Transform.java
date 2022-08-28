package ru.geekbrains.java_core_3.lesson_1;

import java.util.ArrayList;
import java.util.Arrays;

public class Transform<T> {
    public static <T> ArrayList<T> arrayToArrayList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static <T> void swapElements(T[] arr, int first, int second) {
        T temp = arr[second];
        arr[second] = arr[first];
        arr[first] = temp;
    }
}
