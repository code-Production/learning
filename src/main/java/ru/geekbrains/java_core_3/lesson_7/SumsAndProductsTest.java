package ru.geekbrains.java_core_3.lesson_7;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumsAndProductsTest {
    private static SumsAndProducts mathObj;

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        StartTests.start(SumsAndProductsTest.class);
    }

    @BeforeSuite
    public static void before(){
        System.out.println("Test object was created.");
        mathObj = new SumsAndProducts();
    }

    @Test(priority = 6)
    @DisplayName("Test sum of elements")
    public void testSumElements() {
        int[] sourceArr = new int[] {1,2,3,4,5,6};
        int expected = 21;
        System.out.printf("Sum of %s is equal %s: %S.\n",
                Arrays.toString(sourceArr),
                expected,
                mathObj.sumElements(sourceArr) == expected);
    }

    @Test
//    @DisplayName("Test multiplying elements")
    public void testMultiplyElements() {
        int[] sourceArr = new int[] {1,2,3,4,5,6};
        int expected = 720;
        System.out.printf("Product of %s is equal %s: %S.\n",
                Arrays.toString(sourceArr),
                expected,
                mathObj.multiplyElements(sourceArr) == expected);
    }


    @ParameterizedTest(priority = 10)
    @MethodSource("dataTestMultiplyElementsWithData")
    @DisplayName("Test multiplying elements with data")
    public void testMultiplyElementsWithData(int[] sourceArr, int expected) {
        System.out.printf("Product of %s is equal %s: %S.\n",
                Arrays.toString(sourceArr),
                expected,
                mathObj.multiplyElements(sourceArr) == expected);
    }


    public static Argument2D[] dataTestMultiplyElementsWithData() {
        List<Argument2D> tests = new ArrayList<>();
        tests.add(new Argument2D(new int[] {1,2,3,4,5,6}, 720));
        tests.add(new Argument2D(new int[] {1,1,3,4,5,6}, 360));
        tests.add(new Argument2D(new int[] {1,1,1,4,5,6}, 120));
        return tests.toArray(Argument2D[]::new);
    }

    @ParameterizedTest(priority = 1)
    @MethodSource("dataTestSumElementsWithData")
//    @DisplayName("Test summing elements with data")
    public void testSumElementsWithData(int[] sourceArr, int expected) {
        System.out.printf("Sum of %s is equal %s: %S.\n",
                Arrays.toString(sourceArr),
                expected,
                mathObj.sumElements(sourceArr) == expected);
    }


    public static Argument2D[] dataTestSumElementsWithData() {
        List<Argument2D> tests = new ArrayList<>();
        tests.add(new Argument2D(new int[] {1,2,3,4,5,6}, 21));
        tests.add(new Argument2D(new int[] {1,1,3,4,5,6}, 20)); //miscast
        tests.add(new Argument2D(new int[] {1,1,1,4,5,6}, "18"));
        return tests.toArray(Argument2D[]::new);
    }

    @AfterSuite
    public static void after() {
        mathObj.statistic();
    }


}
