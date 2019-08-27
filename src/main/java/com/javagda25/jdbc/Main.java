package com.javagda25.jdbc;


import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentDAO studentDAO;
        try {
            studentDAO = new StudentDAO();
        } catch (SQLException e) {
            System.err.println("Student dao cannot be created. Mysql error.");
            System.err.println("Error: " + e.getMessage());
            return;
        }

        int option;
        do {
            printOptions();
            option = getIntFromUser();
            try {
                if (option == 1) { //insert student
                    Student student = createStudentFromUser();
                    studentDAO.insertStudent(student);
                } else if (option == 2) { //delete student by id
                    int id = getIntFromUser();
                    studentDAO.deleteStudentById(id);
                } else if (option == 3) { //list all students
                    List<Student> students = studentDAO.listAllStudents();
                    System.out.println(students.toString());
                } else if (option == 4) { //get student by id
                    Long id = Long.parseLong(scanner.nextLine());
                    Student student = studentDAO.getStudentById(id).get();
                    System.out.println(student);
                } else if (option == 5) { //get students by name
                    String name = scanner.nextLine();
                    List<Student> students = studentDAO.getStudentByName(name);
                    System.out.println(students.toString());
                } else if (option == 6) { //get students by age
                    int min_age = getIntFromUser();
                    int max_age = getIntFromUser();
                    studentDAO.getStudentByAge(min_age, max_age);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (option != 0);


    }//main

    private static void printOptions() {
        System.out.println("wybierz opcję:");
        System.out.println("0. wyjdź");
        System.out.println("1. dodaj studenta");
        System.out.println("2. usuń studenta");
        System.out.println("3. wyświetl studentów");
        System.out.println("4. wyświetl studenta po ID");
        System.out.println("5. wyświetl studenta po nazwisku");
        System.out.println("6. wyświetl studenta w wieku od ... do ...");
    }

    private static Student createStudentFromUser() {
        System.out.println("podaj imie");
        String name = scanner.nextLine();
        System.out.println("podaj wiek");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("podaj średnią");
        double avarge = Double.parseDouble(scanner.nextLine());
        System.out.println("czy żyw?");
        boolean alive = Boolean.parseBoolean(scanner.nextLine());
        return new Student(name, age, avarge, alive);
    }

    private static int getIntFromUser() {
        Integer value = null;
        do {
            String input = scanner.nextLine();
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } while (value == null);
        return value;
    }

}//class
