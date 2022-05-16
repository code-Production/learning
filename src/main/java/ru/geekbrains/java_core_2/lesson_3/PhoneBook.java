package ru.geekbrains.java_core_2.lesson_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    private final Map<String, ArrayList<String>> phoneBook = new HashMap<>();

    public boolean add(String lastName, String phoneNumber) {
        if (phoneBook.containsKey(lastName)) {
            ArrayList<String> phoneArr = phoneBook.get(lastName);
            if (!phoneArr.contains(phoneNumber)) {
                phoneArr.add(phoneNumber);
                return true;
            }
        } else {
            ArrayList<String> tempArr = new ArrayList<>();
            tempArr.add(phoneNumber);
            phoneBook.put(lastName, tempArr);
            return true;
        }
        return false;
    }

    public ArrayList<String> get(String lastName) {
        return phoneBook.get(lastName); // null if not found
    }
}
