package ru.geekbrains.java_core_3.lesson_7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StartTests {

    public static final int MIN_PRIORITY = 1;
    public static final int MAX_PRIORITY = 10;
    public static final int DEFAULT_PRIORITY = 5;

    public static <T> void start(Class<T> testClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method[] publicMethods = testClass.getMethods();
        T testClassObj = testClass.getConstructor().newInstance();

        int testNum = 1;

        boolean foundBeforeSuite = false;
        boolean foundAfterSuite = false;

        for (Method publicMethod : publicMethods) {
            if (publicMethod.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(publicMethod.getModifiers())) {
                    throw new RuntimeException("@BeforeSuite method must be static.");
                }
                if (!foundBeforeSuite) {
                    foundBeforeSuite = true;
                    MethodsList.add(MAX_PRIORITY + 1, publicMethod);
                } else {
                    throw new RuntimeException("There must be only one method annotated @BeforeSuite.");
                }
            }
            if (publicMethod.isAnnotationPresent(Test.class) || publicMethod.isAnnotationPresent(ParameterizedTest.class)) {
                int priority;
                if (publicMethod.isAnnotationPresent(Test.class)) {
                    priority = publicMethod.getAnnotation(Test.class).priority();
                } else {
                    priority = publicMethod.getAnnotation(ParameterizedTest.class).priority();
                }
                if (priority > MAX_PRIORITY) {
                    priority = MAX_PRIORITY;
                } else if (priority < MIN_PRIORITY) {
                    priority = MIN_PRIORITY;
                }
                MethodsList.add(priority, publicMethod);
            }
            if (publicMethod.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(publicMethod.getModifiers())) {
                    throw new RuntimeException("@AfterSuite method must be static.");
                }
                if (!foundAfterSuite) {
                    foundAfterSuite = true;
                    MethodsList.add(MIN_PRIORITY - 1, publicMethod);
                } else {
                    throw new RuntimeException("There must be only one method annotated @AfterSuite.");
                }
            }
        }
        if (MethodsList.size() == 0) {
            throw new RuntimeException("Test class must contain at least one test method.");
        }

        List<Method> sortedTestMethods = MethodsList.getSortedMethods();
        System.out.println("Start testing...");

        for (Method sortedTestMethod : sortedTestMethods) {

            if (sortedTestMethod.isAnnotationPresent(ParameterizedTest.class)) {

                if (sortedTestMethod.getParameterTypes().length != 2) {
                    throw new RuntimeException("@ParameterizedTest must have two arguments.");
                }
                if (!sortedTestMethod.isAnnotationPresent(MethodSource.class)) {
                    throw new RuntimeException("@ParameterizedTest must have @MethodSource annotation.");
                }
                String methodSourceName = sortedTestMethod.getAnnotation(MethodSource.class).value();
                Method methodSource = testClass.getDeclaredMethod(methodSourceName);

                if (!Modifier.isStatic(methodSource.getModifiers())) {
                    throw new RuntimeException("Source method for parameterized test must be static.");
                }
                if (methodSource.getReturnType() != Argument2D[].class) {
                    throw new RuntimeException("Source method for parameterized test must return Arguments[].class");
                }
                if (methodSource.getParameterTypes().length != 0) {
                    throw new RuntimeException("Source method for parameterized test must not take arguments.");
                }

                System.out.printf("#%s Test (priority = %s, parameterized): ",
                        testNum,
                        sortedTestMethod.getAnnotation(ParameterizedTest.class).priority());
                if (sortedTestMethod.isAnnotationPresent(DisplayName.class)) {
                    System.out.println(sortedTestMethod.getAnnotation(DisplayName.class).value());
                } else {
                    System.out.println();
                }

                Argument2D[] argumentArr = (Argument2D[]) methodSource.invoke(testClassObj);

                int subTestNum = 1;
                for (Argument2D argument : argumentArr) {
                    System.out.printf("\t#%s.%s: ", testNum, subTestNum++);
                    try {
                        sortedTestMethod.invoke(testClassObj, argument.arg1(), argument.arg2());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        System.out.printf("\nIllegalArgumentException - Trying to cast:\n%s - > %s (%S)\n%s -> %s (%S)\n",
                                sortedTestMethod.getParameterTypes()[0],
                                argument.arg1().getClass(),
                                sortedTestMethod.getParameterTypes()[0] == argument.arg1().getClass(),
                                sortedTestMethod.getParameterTypes()[1],
                                argument.arg2().getClass(),
                                sortedTestMethod.getParameterTypes()[1] == argument.arg2().getClass());
                        return;
                    }
                }
                testNum++;

            } else {

                if (sortedTestMethod.isAnnotationPresent(Test.class)) {
                    System.out.printf("#%s Test (priority = %s): ",
                            testNum++,
                            sortedTestMethod.getAnnotation(Test.class).priority());
                }
                if (sortedTestMethod.isAnnotationPresent(DisplayName.class)) {
                    System.out.println(sortedTestMethod.getAnnotation(DisplayName.class).value());
                } else {
                    System.out.println();
                }
                if (sortedTestMethod.isAnnotationPresent(AfterSuite.class)) {
                    System.out.println("All tests completed.\n");
                }
                sortedTestMethod.invoke(testClassObj);
            }

            System.out.println();
        }
    }

    private static class MethodsList {

        private static final List<InnerArray> methodsList = new ArrayList<>();

        public static void add(int priority, Method method) {
            methodsList.add(new InnerArray (priority, method));
        }

        public static List<Method> getSortedMethods() {
            methodsList.sort(Comparator.comparingInt(InnerArray::priority).reversed());
            return methodsList.stream().map(InnerArray::method).toList();
        }
        public static int size() {
            return methodsList.size();
        }

        private record InnerArray(int priority, Method method) {}

    }
}
