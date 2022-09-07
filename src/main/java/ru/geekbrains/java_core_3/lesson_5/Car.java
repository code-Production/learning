package ru.geekbrains.java_core_3.lesson_5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static final Lock winnerStatus = new ReentrantLock();

    private final CyclicBarrier barrier;
    private final CountDownLatch countDownStart;
    private final CountDownLatch countDownFinish;

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race,
               int speed,
               CyclicBarrier barrier,
               CountDownLatch countDownStart,
               CountDownLatch countDownFinish) {

        this.race = race;
        this.speed = speed;
        this.barrier = barrier;
        this.countDownStart = countDownStart;
        this.countDownFinish = countDownFinish;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            barrier.await();
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownStart.countDown();
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (winnerStatus.tryLock()) {
            winnerStatus.lock();
            System.out.println(this.name + " is a WINNER.");
        }
        countDownFinish.countDown();
    }
}
