package com.optica.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static final String URL =
        "jdbc:sqlserver://servidor-optica-dencid.database.windows.net:1433;" +
        "database=optica_db;" +
        "encrypt=true;" +
        "trustServerCertificate=false;" +
        "hostNameInCertificate=*.database.windows.net;" +
        "loginTimeout=30;";

    private static final String USUARIO = "TU_USUARIO@servidor-optica-dencid";

    private static final String CLAVE = "TU_Password";

    private Conexion() {}

    public static Connection getConexion() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USUARIO);
        props.setProperty("password", CLAVE);
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
