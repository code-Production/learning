package ru.geekbrains.java_core_2.lesson_5;

import java.util.Arrays;

public class ChangeArrayValuesSerial {
    static final int size = 10_000_000;
    static final int h = size / 2;
    float[] arr = new float[size];

    public void start() {
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Serial cycle time: " + (System.currentTimeMillis() - a) + "ms");
    }

}
