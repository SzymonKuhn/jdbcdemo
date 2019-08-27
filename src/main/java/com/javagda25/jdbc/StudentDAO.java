package com.javagda25.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.javagda25.jdbc.StudentQueries.*;

public class StudentDAO { // data access object
    private MysqlConnection mysqlConnection;

    public StudentDAO() throws SQLException {
        mysqlConnection = new MysqlConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                statement.setString(1, student.getName());
                statement.setInt(2, student.getAge());
                statement.setDouble(3, student.getAverage());
                statement.setBoolean(4, student.isAlive());
                statement.execute();
            }
        }
    }

   public void deleteStudentById(int idToRemove) throws SQLException {
       try (Connection connection = mysqlConnection.getConnection()) {
           try (PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {
               statement.setLong(1, idToRemove);
               statement.execute();
           }
       }
    }

    public List<Student> listAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERRY)) {
                ResultSet resultSet = statement.executeQuery();
                students = getListOfStudentsFromDB(resultSet);
            }
        }
        return students;
    }

    public Optional<Student> getStudentById(Long id) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Student student = getStudentFromDB(resultSet);
                    return Optional.of(student);
                }
            }
        }
        return Optional.empty();
    }

    public List<Student> getStudentByName(String name) throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_QUERY)) {
                statement.setString(1, "%" + name + "%");
                ResultSet resultSet = statement.executeQuery();
                students = getListOfStudentsFromDB(resultSet);
            }
        }
        return students;
    }

    public List<Student> getStudentByAge(int min_age, int max_age) throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_AGE_QUERY)) {
                statement.setInt(1, min_age);
                statement.setInt(2, max_age);
                ResultSet resultSet = statement.executeQuery();
                students = getListOfStudentsFromDB(resultSet);
            }
        }
        return students;
    }

    private List<Student> getListOfStudentsFromDB(ResultSet resultSet) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            Student student = getStudentFromDB(resultSet);
            students.add(student);
        }
        return students;
    }

    private Student getStudentFromDB(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong(1));
        student.setName(resultSet.getString(2));
        student.setAge(resultSet.getInt(3));
        student.setAverage(resultSet.getDouble(4));
        student.setAlive(resultSet.getBoolean(5));
        return student;
    }

}//class
