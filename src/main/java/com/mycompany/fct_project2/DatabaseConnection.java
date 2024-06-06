package com.mycompany.fct_project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://192.168.1.10:5432/DB_FCT";

    public static Connection getConnection(String user, String password) throws SQLException {
        try {
            return DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }
}
