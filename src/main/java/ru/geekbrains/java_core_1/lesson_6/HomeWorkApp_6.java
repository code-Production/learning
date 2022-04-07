package ru.geekbrains.java_core_1.lesson_6;

public class HomeWorkApp_6 {



    public static void main(String[] args) {
        Dog dog = new Dog("Tuman");
        Cat cat = new Cat("Pushok");
        Cat cat2 = new Cat("Polkovnik");
        dog.run(500);
        dog.swim(200);


        cat.run(500);
        cat.swim(100);
    }
}
