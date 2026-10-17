import com.optica.util.Conexion;
import java.sql.Connection;

public class TestConexion2 {
    public static void main(String[] args) {
        System.out.println("Probando conexion a Azure SQL via db.properties...");
        try (Connection c = Conexion.getConexion()) {
            System.out.println("Conexion exitosa!");
            System.out.println("Base de datos: " + c.getCatalog());
            System.out.println("Producto: " + c.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.err.println("Fallo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
