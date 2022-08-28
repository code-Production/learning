package ru.geekbrains.java_core_3.lesson_1;

import java.util.ArrayList;

public class Box <T extends Fruit> {
    private final ArrayList<T> contents = new ArrayList<>();

    public void putIn(T fruit) {
        contents.add(fruit);
    }

    public float getWeight() {
        float weight = 0;
        for (T fruit :
                contents) {
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean compare(Box<?> box) {
        return this.getWeight() == box.getWeight();
    }

    public void moveContentTo(Box<T> box) {
        for (T fruit :
                contents) {
            box.putIn(fruit);
        }
        contents.clear();
    }
}
