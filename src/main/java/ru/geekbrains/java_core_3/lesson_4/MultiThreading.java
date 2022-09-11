package ru.geekbrains.java_core_3.lesson_4;


public class MultiThreading {

    static volatile char currentChar = 'A';
    static final Object monitor = new Object();

    public static void main(String[] args) {
        new Thread(() -> printChar('A', 'B')).start();
        new Thread(() -> printChar('B', 'C')).start();
        new Thread(() -> printChar('C', 'A')).start();
    }

    public static void printChar(char current, char next) {
        synchronized (monitor) {
            for (int i = 0; i < 5; i++) {
                while (currentChar != current) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(current);
                currentChar = next;
                monitor.notifyAll();
            }
        }
    }

}
