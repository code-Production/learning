package ru.geekbrains.java_core_2.lesson_1;

public class Cat implements RunningAndJumping {
    private final String NAME;
    private final int MAX_RUN_DISTANCE;
    private final int MAX_JUMP_HEIGHT;

    public Cat(String NAME, int MAX_RUN_DISTANCE, int MAX_JUMP_HEIGHT) {
        this.NAME = NAME;
        this.MAX_RUN_DISTANCE = MAX_RUN_DISTANCE;
        this.MAX_JUMP_HEIGHT = MAX_JUMP_HEIGHT;
    }

    @Override
    public boolean run(int distance) {
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.printf("%s has run the distance of %s metres. ", NAME, distance);
            return true;
        } else {
            System.out.printf("%s cannot run the distance of %s metres. ", NAME, distance);
        }
        return false;
    }

    @Override
    public boolean jump(int height) {
        if (height <= MAX_JUMP_HEIGHT) {
            System.out.printf("%s has jumped %s metres high. ", NAME, height);
            return true;
        } else {
            System.out.printf("%s cannot jump %s metres high. ", NAME, height);
        }
        return false;
    }
}
