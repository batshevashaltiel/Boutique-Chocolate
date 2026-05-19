package com.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    // הגדרות התחברות מרכזיות - משנים רק פה!
    private static final String URL = "jdbc:mysql://localhost:3306/chocolate_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // שימי את הסיסמה שלך

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }
}