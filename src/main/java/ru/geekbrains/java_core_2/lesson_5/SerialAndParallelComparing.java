package ru.geekbrains.java_core_2.lesson_5;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SerialAndParallelComparing {
    private static final int size = 10_000_000;
    private static final int h = size / 2;

    public static void main(String[] args) {
        SerialAndParallelComparing process = new SerialAndParallelComparing();
        process.start();
    }


    public void start() {
        float[] arr1 = createAndFillArray(size);
        measureTime(() -> serialMethod(arr1), "Serial method");
        float[] arr2 = createAndFillArray(size);
        measureTime(() -> parallelMethod(arr2), "Parallel method");
        System.out.println("Arrays are equal: " + Arrays.equals(arr1, arr2));
//        System.out.println(Arrays.toString(arr1));
//        System.out.println(Arrays.toString(arr2));
    }

    private float[] createAndFillArray(int size) {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        return arr;
    }


    private void run(float[] arr, int offset) {
        for (int i = 0; i < arr.length; i++) {
            int index = i + offset;
            arr[i] = (float)(arr[i] * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
        }
    }

    private void run(float[] arr) {
        run(arr, 0);
    }

    private void serialMethod(float[] arr) {
        Thread t = new Thread(() -> run(arr));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void parallelMethod(float[] arr) {
        float[] firstArr = new float[h];
        float[] secondArr = new float[h];
        System.arraycopy(arr, 0, firstArr, 0, h );
        System.arraycopy(arr, h, secondArr, 0, h );
        Thread t1 = new Thread(() -> run(firstArr, 0));
        Thread t2 = new Thread(() -> run(secondArr, h));
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
    }

    private void measureTime(Runnable action, String actionName) {
        long duration = System.currentTimeMillis();
        action.run();
        duration = System.currentTimeMillis() - duration;
        System.out.printf("%s took %d ms\n", actionName, duration);
    }


}
