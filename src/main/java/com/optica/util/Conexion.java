package com.optica.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static final Properties CONFIG = cargarConfig();

    private Conexion() {}

    private static Properties cargarConfig() {
        Properties p = new Properties();
        try (InputStream in = Conexion.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                    "No se encontro db.properties. Copia db.properties.example a db.properties y ajusta tus credenciales.");
            }
            p.load(in);
            return p;
        } catch (IOException e) {
            throw new RuntimeException("Error al leer db.properties", e);
        }
    }

    public static Connection getConexion() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", CONFIG.getProperty("db.user"));
        props.setProperty("password", CONFIG.getProperty("db.password"));
        return DriverManager.getConnection(CONFIG.getProperty("db.url"), props);
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
