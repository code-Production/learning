package ru.geekbrains.java_core_2.lesson_2;

public class ArrayOperations {
    private static final int ARR_SIZE = 4;

    public static int sum(String[][] strArr) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;
        for (int i = 0; i < strArr.length; i++) {
            if (strArr.length != ARR_SIZE || strArr[i].length != ARR_SIZE) {
                throw new MyArraySizeException("Array must be " + ARR_SIZE + "x" + ARR_SIZE + "," +
                                                " but now it is " + strArr.length + "x" + strArr[i].length + ".");
            } else {
                for (int j = 0; j < strArr[i].length; j++) {
                    try {
                        sum += Integer.parseInt(strArr[i][j]);
                    } catch (NumberFormatException e) {
                        throw new MyArrayDataException("Array element [" + i + "][" + j + "] must be numeric.");
                    }
                }
            }
        }
        return sum;
    }
}
