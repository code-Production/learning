package ru.geekbrains.java_core_2.lesson_5;

import java.util.Arrays;

public class ChangeArrayValuesParallel {
    static final int size = 10_000_000;
    static final int h = size / 2;
    float[] arr = new float[size];

    public void start() {
        Arrays.fill(arr, 1);
        float[] firstArr = new float[h];
        float[] secondArr = new float[h];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, firstArr, 0, h );
        System.arraycopy(arr, h, secondArr, 0, h );
        Thread t1 = new Thread(() -> runThrough(firstArr));
        Thread t2 = new Thread(() -> runThrough(secondArr));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(firstArr, 0, arr, 0, h);
        System.arraycopy(secondArr, 0, arr, h, h);
        System.out.println("Parallel cycle time: " + (System.currentTimeMillis() - a) + "ms");
    }

    private void runThrough(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
    


}
