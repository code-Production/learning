package ru.geekbrains.java_core_1.lesson_6;

public class Cat extends Animal {

    private final static int MAX_DISTANCE_RUN = 200;
    private final static int MAX_DISTANCE_SWIM = 0;

    private static int numOfCats;

    public Cat(String name) {
        super(name, MAX_DISTANCE_RUN, MAX_DISTANCE_SWIM);
        numOfCats++;
        //System.out.println("numOfCats: " + numOfCats);
    }


}
