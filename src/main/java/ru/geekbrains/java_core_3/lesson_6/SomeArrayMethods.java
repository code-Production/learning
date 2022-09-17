package ru.geekbrains.java_core_3.lesson_6;

import java.util.Arrays;

public class SomeArrayMethods {

    public static int[] returnAfterFour(int [] sourceArr) {
        int length = sourceArr.length;
        for (int i = length - 1; i >= 0 ; i--) {
            if (sourceArr[i] == 4) {
                return Arrays.copyOfRange(sourceArr, i + 1, length);
            }
        }
        throw new RuntimeException("Array must contain at least one number 4 -> " + Arrays.toString(sourceArr));
    }

    public static boolean checkOnesAndFours(int [] sourceArr) {
        boolean isFour = false;
        boolean isOne = false;
        for (int number :
                sourceArr) {
            if (number == 4) {
                isFour = true;
            }else if (number == 1) {
                isOne = true;
            }
            if (isFour && isOne) {
                break;
            }
        }
        return isFour && isOne;
    }
}
