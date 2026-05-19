package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // הגדרות חיבור - שנה את הסיסמה אם היא לא 1234
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234"; 

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(">>> הצלחה! החיבור לבסיס הנתונים עובד! <<<");
        } catch (ClassNotFoundException e) {
            System.out.println("שגיאה: הדרייבר לא נמצא. וודא שקובץ ה-JAR בתיקיית lib ושעשית Refresh.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("שגיאה: שם משתמש/סיסמה לא נכונים, או שהמסד לא קיים.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        getConnection();
    }
}