package ru.geekbrains.java_core_3.lesson_7;


public class SumsAndProducts {

    private int sums;
    private int sums_count;
    private int products;
    private int products_count;

    public int sumElements(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sums += sum;
        sums_count++;
        return sum;
    }

    public int multiplyElements(int[] arr) {
        int product = 1;
        for (int num : arr) {
            product *= num;
        }
        products += product;
        products_count++;
        return product;
    }

    public void statistic() {
        System.out.println("Statistics:");
        System.out.printf("Average of %s sums is equal %s.\n", sums_count, sums/sums_count);
        System.out.printf("Average of %s products is equal %s.\n", products_count, products/products_count);
    }

}
