package com.platinum.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	// URL de conexión a la base de datos (puerto estándar 3306)
    private static final String URL = "jdbc:mysql://localhost:3306/cuentas_clientes_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // << MODIFICAR USUARIO >>
    private static final String PASSWORD = "MySQL_2025#02"; // << MODIFICAR CONTRASEÑA >>

    public static Connection getConnection() throws SQLException {
        // Carga el driver MySQL (aunque no es estrictamente necesario en JDK 6+ o Java 11+)
        // try {
        //     Class.forName("com.mysql.cj.jdbc.Driver");
        // } catch (ClassNotFoundException e) {
        //     throw new SQLException("Error al cargar el driver JDBC: " + e.getMessage());
        // }
        
        // Retorna la conexión
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
