package com.optica.dao;

import com.optica.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {

    int insertar(Usuario u);
    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorCorreo(String correo);
    List<Usuario> listarTodos();
    List<Usuario> listarPorRol(Usuario.Rol rol);
    boolean actualizar(Usuario u);
    boolean eliminar(int id);
    boolean existeCorreo(String correo);
}
