package ru.geekbrains.java_core_3.lesson_7;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.geekbrains.java_core_3.lesson_7.StartTests.DEFAULT_PRIORITY;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ParameterizedTest {
    int priority() default DEFAULT_PRIORITY;
}
