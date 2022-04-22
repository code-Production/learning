package ru.geekbrains.java_core_2.lesson_1;

public class HomeWorkApp {
    public static void main(String[] args) {

        RunningAndJumping[] participants = {
                new Cat("First cat", 2000, 5),
                new Cat("Second cat", 3000, 4),
                new Human("First human", 5000, 10),
                new Human("Second human", 7000, 15),
                new Robot("First robot", 12000, 25),
                new Robot("Second robot", 20000, 35)
        };

        Obstacle[] obstacles = {
                new RunningLane(2000),
                new Wall(3),
                new RunningLane(8000),
                new Wall(20),
                new RunningLane(20000),
                new Wall(30)
        };

        int i = 0;
        for (RunningAndJumping participant : participants) {
             for (Obstacle obstacle : obstacles) {
                 i++;
                 System.out.print("Obstacle try #" + i + " : ");
                 if (!obstacle.beingUsedBy(participant)) {
                     System.out.println("Participant is out.\n");
                     break;
                 }
             }
        }

    }
}
