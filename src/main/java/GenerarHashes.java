import com.optica.util.PasswordUtil;

public class GenerarHashes {
    public static void main(String[] args) {
        String adminPassword = "admin123";
        String clientePassword = "cliente123";

        System.out.println("=== Admin ===");
        System.out.println("Contrasena: " + adminPassword);
        System.out.println("Hash: " + PasswordUtil.hash(adminPassword));
        System.out.println();
        System.out.println("=== Cliente ===");
        System.out.println("Contrasena: " + clientePassword);
        System.out.println("Hash: " + PasswordUtil.hash(clientePassword));
    }
}
