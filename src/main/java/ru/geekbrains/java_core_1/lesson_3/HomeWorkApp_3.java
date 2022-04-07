package ru.geekbrains.java_core_1.lesson_3;

import java.util.Arrays;
import java.util.Random;


public class HomeWorkApp_3 {
    public static void main(String[] args) {
        invertArray();
        fillArrayByIncrement();
        multiplyNumInArray();
        fillArrayByCustom();
        System.out.println("Initialized array: " + Arrays.toString(initializeArray(10,7)));
        searchMinMaxValues();
        int[] arr = new int[10];
        Random random = new Random();
        do {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(10);
            }
        } while (!canBeSplitBySum(arr));



//        for (int shift = -9; shift <= 9 ; shift++) {
//            int[] arr2 = {4, 6, 3, 8, 9, 3, 0, 5, 5};//, 2};
//            int[] arr3 = {4, 6, 3, 8, 9, 3, 0, 5, 5};//, 2};
//            if (Arrays.equals(shiftArray(arr2, shift), shiftArrayNew(arr3, shift))){
//                System.out.println(shift + ":TRUE");
//            } else {
//                System.out.println(shift + ":FALSE");
//            }
//        }
        int shift = 1;
        int[] arr2 = {4, 6, 3, 8, 9, 3, 0, 5, 5};//, 2};
        int[] arr3 = {4, 6, 3, 8, 9, 3, 0, 5, 5};//, 2};
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(shiftArray(arr2, shift)));
        shiftArrayNew(arr3, shift);
        System.out.println(Arrays.equals(arr2, arr3));

    }

    public static void invertArray(){
        int[] arr = {1,1,0,0,1,0,1,1,0,0};
        System.out.println("Original array: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == 0) {
                arr[i] = 1;
            } else {
                arr[i] = 0;
            }
        }
        System.out.println("Inverted array: " +Arrays.toString(arr));
        System.out.println();
    }

    public static void fillArrayByIncrement (){
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++){
            arr[i] = i + 1;
        }
        System.out.println("Filled array: " + Arrays.toString(arr));
        System.out.println();
    }

    public static void multiplyNumInArray(){
        int[] arr = {1,5,3,2,11,4,5,2,4,8,9,1};
        System.out.println("Original array: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++){
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        System.out.println("Multiplied array: " + Arrays.toString(arr));
    }

    public static void fillArrayByCustom(){
        int squareSize = 9;
        int[][] arr = new int[squareSize][squareSize];
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[i].length; j++){
                if (i == j || i + j == arr.length - 1) {
                    arr[i][j] = 1;
                }
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static int[] initializeArray(int len, int initialValue){
        int[] arr = new int[len];
        Arrays.fill(arr,initialValue);
        return arr;
    }

    public static void searchMinMaxValues(){
        int[] arr = new int[10];
        int minValue = 1000; //out of bounds
        int maxValue = -1;
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
            minValue = Math.min(minValue,arr[i]);
            maxValue = Math.max(maxValue,arr[i]);
        }
        System.out.println("Initialized array: " + Arrays.toString(arr));
        System.out.printf("min value: %s, max value: %s.\n", minValue,maxValue);
    }

    public static boolean canBeSplitBySum(int[] arr){

        for (int i = 0; i < arr.length-1; i++) {
            int leftSum = 0;
            int rightSum = 0;
            for (int left = i; left >= 0; left--) {
                leftSum += arr[left];
            }
            for (int right = i + 1; right < arr.length; right++) {
                rightSum += arr[right];
            }
            if (leftSum == rightSum){
                System.out.println(Arrays.toString(arr));
                System.out.printf("Split between %s and %s elements [1...]\n", i+1, i+2);
                return true;
            }
        }
        return false;
    }

    public static int[] shiftArray (int[] arr, int shift) {
        shift %= arr.length;
        for (int i = 1; i <= Math.abs(shift); i++){
            if (shift < 0) { //left
                int temp = arr[0];
                for (int j = 0; j < arr.length - 1; j++){
                    arr[j] = arr[j+1];
                }
                arr[arr.length -1] = temp;
            } else if (shift > 0){ //right
                int temp = arr[arr.length-1];
                for (int j = arr.length - 1; j > 0; j--){
                    arr[j] = arr[j-1];
                }
                arr[0] = temp;
            }
        }
        return arr;
    }

    public static int[] shiftArrayNew (int[] arr, int shift){
        shift %= arr.length;
        int index = 0;
        int nextIndex;
        int temp = arr[index];
        int nextTemp;
        int count = 0;
        int startIndex = index;
        do {
            nextIndex = (arr.length + index + shift) % arr.length;
            nextTemp = arr[nextIndex];
            arr[nextIndex] = temp;
            temp = nextTemp;
            index = nextIndex;
            count++;
            if (count < arr.length && index == startIndex) {
                index++;
                temp = arr[index];
                startIndex = index;
            }
        } while (count < arr.length);
        return arr;
    }

}
