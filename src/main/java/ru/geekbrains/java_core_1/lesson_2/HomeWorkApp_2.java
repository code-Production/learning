package ru.geekbrains.java_core_1.lesson_2;

public class HomeWorkApp_2 {
    public static void main(String[] args) {
        System.out.println(sumInRange(10,5));
        numSignPrint(0);
        System.out.println(numSignInverse(0));
        stringRepeater("RepeatMe",3);
        System.out.println(leapYear(1700));
    }

    public static boolean sumInRange(int a, int b){
        if (a+b >= 10 && a+b <=20) {
            return true;
        } else {
            return false;
        }
    }

    public static void numSignPrint (int a){
        if (a<0){
            System.out.println("Число отрицательное");
        } else {
            System.out.println("Число положительное");
        }
    }

    public static boolean numSignInverse (int a){
        if (a<0) {
            return true;
        } else {
            return false;
        }
    }

    public static void stringRepeater (String str, int n) {
        for(int i = 0; i<n; i++){
            System.out.println(str);
        }
    }

    public static boolean leapYear (int year){
        if ((year % 4 == 0 && !(year % 100 == 0)) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

}
