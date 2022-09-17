package ru.geekbrains.java_core_3.lesson_7;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MethodSource {
    String value();
}
