package ru.geekbrains.java_core_3.lesson_6;

import java.util.Arrays;

public class SomeArrayMethods {

    public static int[] returnAfterFour(int [] sourceArr) {
        int length = sourceArr.length;
            for (int i = length - 1; i >= 0 ; i--) {
                if (sourceArr[i] == 4) {
                    return Arrays.copyOfRange(sourceArr, i, length);
                }
            }
        throw new RuntimeException("Array must contain at least one number 4 -> " + Arrays.toString(sourceArr));
    }

    public static boolean checkOnesAndFours(int [] sourceArr) {
        return Arrays.binarySearch(sourceArr, 1) >= 0 && Arrays.binarySearch(sourceArr, 4) >= 0;
    }
}
