package ru.geekbrains.java_core_2.lesson_1;

public class RunningLane extends Obstacle {
    private final int DISTANCE;

    public RunningLane(int DISTANCE) {
        this.DISTANCE = DISTANCE;
    }

    public boolean beingUsedBy(RunningAndJumping unit) {
        if (unit.run(DISTANCE)) {
            System.out.println("Distance is successfully passed.");
            return true;
        } else {
            System.out.println("Distance is too long.");
            return false;
        }
    }

}
