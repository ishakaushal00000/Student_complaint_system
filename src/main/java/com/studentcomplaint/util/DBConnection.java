package com.studentcomplaint.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_complaint_db";

    private static final String USER = "root";

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException("DB_PASSWORD environment variable is not set");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}