package ru.geekbrains.java_core_2.lesson_2;

public class HomeWorkApp_2 {
    public static void main(String[] args) {
        String[][] myStrArray = {
                {"1", "2", "3", "4"},
                {"4", "5", "6", "7"},
                {"7", "8", "9", "0"},
                {"1", "2", "3", "4q"}
        };
        try {
            System.out.println("Sum of array elements = " + ArrayOperations.sum(myStrArray));
        } catch (MyArraySizeException | MyArrayDataException e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
