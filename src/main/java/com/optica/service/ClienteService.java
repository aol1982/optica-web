package com.optica.service;

import com.optica.dao.ClienteDAO;
import com.optica.dao.impl.ClienteDAOImpl;
import com.optica.model.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAOImpl();
    }

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    public Optional<Cliente> buscarPorIdUsuario(int idUsuario) {
        return clienteDAO.buscarPorIdUsuario(idUsuario);
    }

    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteDAO.buscarPorDni(dni);
    }

    public boolean actualizar(Cliente c) {
        if (c.getIdCliente() <= 0) throw new IllegalArgumentException("ID inválido");
        return clienteDAO.actualizar(c);
    }

    public boolean eliminar(int id) {
        return clienteDAO.eliminar(id);
    }
}
