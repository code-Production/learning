package ru.geekbrains.java_core_2.lesson_3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class PhoneBook {
    private final Map<String, String> phoneBook = new HashMap<>();

    public void add(String lastName, String phoneNumber) {
        if (phoneBook.containsKey(phoneNumber)) {
            System.out.println("Record with this phone number already exists. Operation is not complete.");
        } else {
            phoneBook.put(phoneNumber, lastName);
            System.out.println("Record is successfully added.");
        }
    }

    public void get(String lastName) {
        phoneBook.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                if (s2.equals(lastName)) {
                    System.out.println(lastName + ": " + s);
                }
            }
        });
    }
}
