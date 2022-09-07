package ru.geekbrains.java_core_3.lesson_1;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeWorkApp {
    public static void main(String[] args) {

        String[] strArr = new String[]{"A", "B", "C"};
        Byte[] byteArr = new Byte[]{1, 2, 3};
        Transform.swapElements(strArr,0,2);
        Transform.swapElements(byteArr,0,2);
        System.out.println(Arrays.toString(strArr));
        System.out.println(Arrays.toString(byteArr));

        ArrayList<String> strList = Transform.arrayToArrayList(strArr);
        ArrayList<Byte> byteList = Transform.arrayToArrayList(byteArr);
        System.out.println(strList);
        System.out.println(byteList);

        Box<Apple> apples1 = new Box<>();
        Box<Apple> apples2 = new Box<>();
        Box<Orange> oranges = new Box<>();

        for (int i = 0; i < 10; i++) {
            apples1.putIn(new Apple());
            apples2.putIn(new Apple());
            oranges.putIn(new Orange());
        }
        oranges.putIn(new Orange());

        System.out.println(apples1.getWeight());
        System.out.println(apples2.getWeight());
        System.out.println(oranges.getWeight());

        System.out.println(apples1.compare(apples2));
        System.out.println(apples1.compare(oranges));

        apples1.moveContentTo(apples2);
        System.out.println(apples1.getWeight());
        System.out.println(apples2.getWeight());
    }
}
