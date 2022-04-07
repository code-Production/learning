package ru.geekbrains.java_core_1.lesson_5;

public class HomeWorkApp_5 {
    public static void main(String[] args) {
        Employee[] employees = new Employee[5];
        employees[0] = new Employee("Сергей Иванов", "Инженер", "ivanov@mail.ru", "8(999)112-23-33", 100000, 30);
        employees[1] = new Employee("Иван Петров", "Администратор", "petrov@mail.ru", "8(999)112-55-55", 110000, 41);
        employees[2] = new Employee("Дарья Светлова", "Менеджер", "svetlova@mail.ru", "8(999)112-66-66", 120000, 25);
        employees[3] = new Employee("Юрий Гагарин", "Директор", "gagarin@mail.ru", "8(999)111-11-11", 200000, 57);
        employees[4] = new Employee("Ольга Власова", "Аналитик", "vlasova@mail.ru", "8(999)112-23-35", 150000, 29);
        for (Employee person : employees) {
            if (person.getAge() >= 40) {
                person.getInfo();
            }
        }
    }
}
