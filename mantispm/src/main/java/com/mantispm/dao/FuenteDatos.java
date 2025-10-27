package com.mantispm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FuenteDatos {

    private static final String URL = "jdbc:mysql://localhost:3306/mantis_pm";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "hola";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se pudo cargar el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
