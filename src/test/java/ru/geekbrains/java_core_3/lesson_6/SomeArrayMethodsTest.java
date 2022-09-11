package ru.geekbrains.java_core_3.lesson_6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static ru.geekbrains.java_core_3.lesson_6.SomeArrayMethods.returnAfterFour;
import static ru.geekbrains.java_core_3.lesson_6.SomeArrayMethods.checkOnesAndFours;


class SomeArrayMethodsTest {

    @Test
    @DisplayName("Testing ReturnAfterFour() throwing exception with wrong input array")
    public void testReturnAfterFourException() {
        Assertions.assertThrows(RuntimeException.class, () -> returnAfterFour(new int[] {1,2,3,5,6,7,8,9,0}));
    }

    @ParameterizedTest
    @MethodSource("dataForReturnAfterFour")
    @DisplayName("Testing ReturnAfterFour() with right input arrays")
    public void testReturnAfterFour(int[] sourceArr, int[] resultArr) {
        Assertions.assertArrayEquals(resultArr, returnAfterFour(sourceArr));
    }

    public static Stream<Arguments> dataForReturnAfterFour() {
        List<Arguments> tests = new ArrayList<>();
        tests.add(Arguments.arguments(new int[] {1,2,3,4,5,6}, new int[] {4,5,6}));
        tests.add(Arguments.arguments(new int[] {6,5,4,3,2,1}, new int[] {4,3,2,1}));
        tests.add(Arguments.arguments(new int[] {4,2,3,4,5,4,3,4}, new int[] {4}));
        tests.add(Arguments.arguments(new int[] {4,9,8,7,6,5,3,2,1}, new int[] {4,9,8,7,6,5,3,2,1}));
        return tests.stream();
    }


    @ParameterizedTest
    @MethodSource("dataForCheckOnesAndFours")
    @DisplayName("Testing checkOnesAndFours()")
    void testCheckOnesAndFours(int[] sourceArr, boolean result) {
        Assertions.assertEquals(result, checkOnesAndFours(sourceArr));
    }

    public static Stream<Arguments> dataForCheckOnesAndFours() {
        List<Arguments> tests = new ArrayList<>();
        tests.add(Arguments.arguments(new int[] {2,3,4,5,6,7,8,9,0}, false));
        tests.add(Arguments.arguments(new int[] {1,2,3,5,6,7,8,9,0}, false));
        tests.add(Arguments.arguments(new int[] {2,3,5,6,7,8,9,0}, false));
        tests.add(Arguments.arguments(new int[] {1,2,3,4,5,6,7,8,9,0}, true));
        return tests.stream();
    }
}