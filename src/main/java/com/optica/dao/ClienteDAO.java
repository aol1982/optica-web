package com.optica.dao;

import com.optica.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteDAO {

    int insertar(Cliente c);
    Optional<Cliente> buscarPorId(int id);
    Optional<Cliente> buscarPorIdUsuario(int idUsuario);
    Optional<Cliente> buscarPorDni(String dni);
    List<Cliente> listarTodos();
    boolean actualizar(Cliente c);
    boolean eliminar(int id);
    boolean existeDni(String dni);
}
