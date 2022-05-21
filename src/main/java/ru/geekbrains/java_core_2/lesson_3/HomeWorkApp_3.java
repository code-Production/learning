package ru.geekbrains.java_core_2.lesson_3;

import java.util.*;

public class HomeWorkApp_3 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Pineapple");
        list.add("Lemon");
        list.add("Kiwi");
        list.add("Watermelon");
        list.add("Orange");
        list.add("Cranberry");
        list.add("Strawberry");
        list.add("Lemon");
        list.add("Grapefruit");
        list.add("Kiwi");
        list.add("Apple");
        list.add("Melon");
        list.add("Grape");
        list.add("Peach");
        list.add("Lemon");
        Map<String, Integer> map = new HashMap<>();
        for (String str : list){
            if (map.containsKey(str)) {
                int count = map.get(str);
                map.put(str, ++count);
            } else {
                map.put(str, 1);
            }
        }
        System.out.println(map);

        PhoneBook book = new PhoneBook();
        System.out.println(book.add("Gusev", "8(800)111-22-33"));
        System.out.println(book.add("Gusev", "8(800)222-22-33"));
        System.out.println(book.add("Gusev", "8(800)222-22-33")); //duplicate -> skipped
        System.out.println(book.add("Vatrushkin", "8(800)111-11-11"));
        System.out.println(book.add("Vatrushkin", "8(800)222-22-22"));
        System.out.println(book.add("Lavochkin", "8(800)000-00-00"));
        System.out.println(book.get("Gusev"));
        System.out.println(book.get("Gusev1")); //null
    }

}
