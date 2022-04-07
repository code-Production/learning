package ru.geekbrains.java_core_1.lesson_1;

public class HomeWorkApp {
    public static void main(String[] args) {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
    }

    public static void printThreeWords() {
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    public static void checkSumSign(){
        int a = 1;
        int b = 2;
        if (a+b < 0){
            System.out.println("Сумма отрицательная");
        } else {
            System.out.println("Сумма положительная");
        }
    }

    public static void printColor(){
        int value = 183;
        if (value <=0){
            System.out.println("Красный");
        } else if (value > 0 && value <=100) {
            System.out.println("Желтый");
        } else if (value > 100) {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers(){
        int a = 25;
        int b = 37;
        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }
}
