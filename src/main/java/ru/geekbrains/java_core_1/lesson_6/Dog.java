package ru.geekbrains.java_core_1.lesson_6;

public class Dog extends Animal{

    private final static int MAX_DISTANCE_RUN = 500;
    private final static int MAX_DISTANCE_SWIM = 10;

    private static int numOfDogs;

    public Dog(String name){
        super(name, MAX_DISTANCE_RUN, MAX_DISTANCE_SWIM);
        numOfDogs++;
        //System.out.println("numOfDogs: " + numOfDogs);
    }

}
