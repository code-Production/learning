package ru.geekbrains.java_core_1.lesson_7;

public class Bowl {
    private int foodAmount;

    public Bowl() {};
    public Bowl(int foodAmount) {
        this.foodAmount = foodAmount;
    };

    public boolean eatFood(int foodAmount) {
        if (this.foodAmount < foodAmount) {
            System.out.printf("Not enough food in the bowl to eat %s food. There is only %s food.\n", foodAmount, this.foodAmount);
        } else {
            this.foodAmount -= foodAmount;
            return true;
        }
        return false;
    }

    public void addFood(int foodAmount) {
        this.foodAmount += foodAmount;
    }

    public int getFoodAmount() {
        return foodAmount;
    }
}
