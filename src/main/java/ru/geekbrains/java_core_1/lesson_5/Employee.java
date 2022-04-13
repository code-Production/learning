package ru.geekbrains.java_core_1.lesson_5;

public class Employee {
    private String fullName;
    private String position;
    private String email;
    private String phoneNumber;
    private double salary;
    private int age;

    public Employee(String fullName, String position, String email, String phoneNumber, double salary, int age){
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
    }

    public void getInfo() {
        System.out.println("\nИнформация о сотруднике:\n");
        System.out.printf("\tФИО: %s\n", this.fullName);
        System.out.printf("\tДолжность: %s\n", this.position);
        System.out.printf("\tEmail: %s\n", this.email);
        System.out.printf("\tТелефон: %s\n", this.phoneNumber);
        System.out.printf("\tЗарплата: %.2f р.\n", this.salary);
        System.out.printf("\tВозраст: %d\n", this.age);
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPosition() {
        return this.position;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public double getSalary() {
        return this.salary;
    }

    public int getAge() {
        return this.age;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
