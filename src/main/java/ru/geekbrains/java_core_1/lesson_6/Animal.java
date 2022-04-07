package ru.geekbrains.java_core_1.lesson_6;

public abstract class Animal {

    private final int MAX_DISTANCE_RUN;
    private final int MAX_DISTANCE_SWIM;

    private static int numOfAnimals;

    protected String name;

    public Animal(String name, int maxDistanceRun, int maxDistanceSwim){
        this.name = name;
        numOfAnimals++;
        //System.out.println("numOfAnimals: " + numOfAnimals);
        MAX_DISTANCE_RUN = maxDistanceRun;
        MAX_DISTANCE_SWIM = maxDistanceSwim;
    }

//    public abstract void run();
//    public abstract void swim();

    public void run(int distance) {
        if (MAX_DISTANCE_RUN == 0) {
            System.out.printf("%s cannot run.\n", name);
        }else if (distance <= MAX_DISTANCE_RUN) {
            System.out.printf("%s has run %s metres.\n", name, distance);
        } else {
            System.out.printf("%s has run %s metres and could not run the last %s metres.\n", name, MAX_DISTANCE_RUN, distance - MAX_DISTANCE_RUN);
        }
    }

    public void swim(int distance) {
        if (MAX_DISTANCE_SWIM == 0) {
            System.out.printf("%s cannot swim.\n", name);
        }else if (distance <= MAX_DISTANCE_SWIM) {
            System.out.printf("%s has swum %s metres.\n", name, distance);
        } else {
            System.out.printf("%s has swum %s metres and could not swim the last %s metres.\n", name, MAX_DISTANCE_SWIM, distance - MAX_DISTANCE_SWIM);
        }
    }

}
