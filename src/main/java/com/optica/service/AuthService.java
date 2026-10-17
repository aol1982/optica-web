package com.optica.service;

import com.optica.dao.ClienteDAO;
import com.optica.dao.UsuarioDAO;
import com.optica.dao.impl.ClienteDAOImpl;
import com.optica.dao.impl.UsuarioDAOImpl;
import com.optica.model.Cliente;
import com.optica.model.Usuario;
import com.optica.util.Conexion;
import com.optica.util.PasswordUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class AuthService {

    private final UsuarioDAO usuarioDAO;
    private final ClienteDAO clienteDAO;

    public AuthService() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
    }

    public AuthService(UsuarioDAO usuarioDAO, ClienteDAO clienteDAO) {
        this.usuarioDAO = usuarioDAO;
        this.clienteDAO = clienteDAO;
    }

    public Optional<Usuario> login(String correo, String password) {
        if (correo == null || password == null) return Optional.empty();
        return usuarioDAO.buscarPorCorreo(correo.trim().toLowerCase())
                .filter(u -> PasswordUtil.verificar(password, u.getClave()));
    }

    public Usuario registrarCliente(String nombre, String dni, String telefono,
                                     String correo, String password) {
        validarRegistro(nombre, dni, correo, password);

        String correoLimpio = correo.trim().toLowerCase();

        if (usuarioDAO.existeCorreo(correoLimpio)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (clienteDAO.existeDni(dni.trim())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        Connection c = null;
        try {
            c = Conexion.getConexion();
            c.setAutoCommit(false);

            Usuario u = new Usuario();
            u.setCorreo(correoLimpio);
            u.setClave(PasswordUtil.hash(password));
            u.setRol(Usuario.Rol.CLIENTE);
            usuarioDAO.insertar(u);

            Cliente cl = new Cliente();
            cl.setIdUsuario(u.getIdUsuario());
            cl.setNombre(nombre.trim());
            cl.setDni(dni.trim());
            cl.setTelefono(telefono == null ? null : telefono.trim());
            clienteDAO.insertar(cl);

            c.commit();
            return u;
        } catch (SQLException | RuntimeException e) {
            if (c != null) {
                try { c.rollback(); } catch (SQLException ignored) {}
            }
            throw new RuntimeException("Registro cancelado: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                try { c.setAutoCommit(true); } catch (SQLException ignored) {}
                Conexion.cerrar(c);
            }
        }
    }

    public Usuario registrarVendedor(String correo, String password) {
        if (correo == null || correo.isBlank()) throw new IllegalArgumentException("Correo obligatorio");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("Contraseña mínimo 6 caracteres");

        String correoLimpio = correo.trim().toLowerCase();
        if (usuarioDAO.existeCorreo(correoLimpio)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario u = new Usuario();
        u.setCorreo(correoLimpio);
        u.setClave(PasswordUtil.hash(password));
        u.setRol(Usuario.Rol.VENDEDOR);
        usuarioDAO.insertar(u);
        return u;
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    private void validarRegistro(String nombre, String dni, String correo, String password) {
        if (nombre == null || nombre.isBlank())         throw new IllegalArgumentException("El nombre es obligatorio");
        if (dni == null || dni.isBlank())               throw new IllegalArgumentException("El DNI es obligatorio");
        if (correo == null || correo.isBlank())         throw new IllegalArgumentException("El correo es obligatorio");
        if (!correo.contains("@"))                      throw new IllegalArgumentException("Correo inválido");
        if (password == null || password.length() < 6)  throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
    }
}
