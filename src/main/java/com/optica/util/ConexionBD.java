package com.optica.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    // [BD] Cambiar cuando exista la BD
    private static final String URL  = "jdbc:mysql://localhost:3306/optica_db?useSSL=false&serverTimezone=America/Tegucigalpa";
    private static final String USER = "root";
    private static final String PASS = "";

    private ConexionBD() {}

    public static Connection getConexion() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        props.setProperty("useUnicode", "true");
        props.setProperty("characterEncoding", "UTF-8");
        return DriverManager.getConnection(URL, props);
    }

    public static void cerrar(Connection c) {
        if (c != null) {
            try { c.close(); } catch (SQLException ignored) {}
        }
    }

    public static boolean probarConexion() {
        try (Connection c = getConexion()) {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la BD: " + e.getMessage());
            return false;
        }
    }
}
