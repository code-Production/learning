package ru.geekbrains.java_core_2.lesson_1;

public class Wall extends Obstacle {
    private final int HEIGHT;

    public Wall(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public boolean beingUsedBy(RunningAndJumping unit) {
        if (unit.jump(HEIGHT)) {
            System.out.println("Wall is successfully passed.");
            return true;
        } else {
            System.out.println("Wall is too high.");
            return false;
        }
    }
}
