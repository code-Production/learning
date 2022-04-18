package ru.geekbrains.java_core_1.lesson_7;

public class HomeWorkApp_7 {
    public static void main(String[] args) {
        Animal[] cats = new Cat[5];
        Bowl bowl = new Bowl(20);
        bowl.addFood(100);
        for (int i = 0; i < cats.length; i++) {
            cats[i] = new Cat("Cat #" + (i + 1));
            cats[i].eat(bowl, (i + 1) * 10);
        }
    }
}
