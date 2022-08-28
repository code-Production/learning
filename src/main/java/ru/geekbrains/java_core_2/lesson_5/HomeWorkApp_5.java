package ru.geekbrains.java_core_2.lesson_5;

public class HomeWorkApp_5 {

    public static void main(String[] args) {
        ChangeArrayValuesSerial serial = new ChangeArrayValuesSerial();
        serial.start();

        ChangeArrayValuesParallel parallel = new ChangeArrayValuesParallel();
        parallel.start();

    }
}
