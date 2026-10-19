package com.optica.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static final Properties CONFIG = cargarConfig();

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo cargar el driver de SQL Server", e);
        }
    }

    private Conexion() {}

    private static Properties cargarConfig() {
        Properties p = new Properties();

        // 1. Intentar leer de variables de entorno (ideal para Render/Nube)
        String envUrl = System.getenv("DB_URL");
        String envUser = System.getenv("DB_USER");
        String envPass = System.getenv("DB_PASSWORD");

        if (envUrl != null && envUser != null && envPass != null) {
            p.setProperty("db.url", envUrl);
            p.setProperty("db.user", envUser);
            p.setProperty("db.password", envPass);
            return p;
        }

        // 2. Si no hay variables de entorno, leer del archivo local (para tu PC)
        try (InputStream in = Conexion.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                    "No se encontro db.properties ni las variables de entorno DB_URL, DB_USER, DB_PASSWORD.");
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