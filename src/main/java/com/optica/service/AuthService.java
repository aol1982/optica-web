package com.optica.service;

import com.optica.dao.UsuarioDAO;
import com.optica.dao.impl.UsuarioDAOImpl;
import com.optica.model.Usuario;
import com.optica.util.PasswordUtil;

import java.util.Optional;

public class AuthService {

    private final UsuarioDAO usuarioDAO;

    public AuthService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public AuthService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Optional<Usuario> login(String correo, String password) {
        if (correo == null || password == null) return Optional.empty();
        return usuarioDAO.buscarPorCorreo(correo.trim().toLowerCase())
                .filter(u -> PasswordUtil.verificar(password, u.getPasswordHash()));
    }

    public Usuario registrarCliente(String nombre, String dni, String telefono,
                                     String correo, String password) {
        validarRegistro(nombre, dni, correo, password);

        String correoLimpio = correo.trim().toLowerCase();
        if (usuarioDAO.existeCorreo(correoLimpio)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario u = new Usuario();
        u.setNombre(nombre.trim());
        u.setDni(dni.trim());
        u.setTelefono(telefono == null ? null : telefono.trim());
        u.setCorreo(correoLimpio);
        u.setPasswordHash(PasswordUtil.hash(password));
        u.setRol(Usuario.Rol.CLIENTE);

        usuarioDAO.insertar(u);
        return u;
    }

    public Usuario registrarVendedor(String nombre, String dni, String telefono,
                                      String correo, String password) {
        validarRegistro(nombre, dni, correo, password);
        String correoLimpio = correo.trim().toLowerCase();
        if (usuarioDAO.existeCorreo(correoLimpio)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        Usuario u = new Usuario();
        u.setNombre(nombre.trim());
        u.setDni(dni.trim());
        u.setTelefono(telefono);
        u.setCorreo(correoLimpio);
        u.setPasswordHash(PasswordUtil.hash(password));
        u.setRol(Usuario.Rol.VENDEDOR);
        usuarioDAO.insertar(u);
        return u;
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    private void validarRegistro(String nombre, String dni, String correo, String password) {
        if (nombre == null || nombre.isBlank())       throw new IllegalArgumentException("El nombre es obligatorio");
        if (dni == null || dni.isBlank())             throw new IllegalArgumentException("El DNI es obligatorio");
        if (correo == null || correo.isBlank())       throw new IllegalArgumentException("El correo es obligatorio");
        if (!correo.contains("@"))                    throw new IllegalArgumentException("Correo inválido");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
    }
    public java.util.List<Usuario> listarClientes() {
        java.util.List<Usuario> todos = usuarioDAO.listarTodos();
        java.util.List<Usuario> clientes = new java.util.ArrayList<>();
        for (Usuario u : todos) {
            if (u.getRol() == Usuario.Rol.CLIENTE) clientes.add(u);
        }
        return clientes;
    }
}
