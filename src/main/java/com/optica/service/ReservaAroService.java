package com.optica.service;

import com.optica.dao.AroDAO;
import com.optica.dao.ReservaAroDAO;
import com.optica.dao.impl.AroDAOImpl;
import com.optica.dao.impl.ReservaAroDAOImpl;
import com.optica.model.Aro;
import com.optica.model.ReservaAro;

import java.util.List;

public class ReservaAroService {

    private final ReservaAroDAO reservaDAO;
    private final AroDAO aroDAO;

    public ReservaAroService() {
        this.reservaDAO = new ReservaAroDAOImpl();
        this.aroDAO = new AroDAOImpl();
    }

    public ReservaAroService(ReservaAroDAO reservaDAO, AroDAO aroDAO) {
        this.reservaDAO = reservaDAO;
        this.aroDAO = aroDAO;
    }

    public ReservaAro apartar(int idCliente, int idAro) {
        if (idCliente <= 0 || idAro <= 0)
            throw new IllegalArgumentException("Cliente o aro inválidos");

        Aro aro = aroDAO.buscarPorId(idAro);
        if (aro == null) throw new IllegalArgumentException("El aro no existe");

        if (reservaDAO.existeActiva(idCliente, idAro))
            throw new IllegalStateException("Ya tienes ese aro apartado");

        ReservaAro r = new ReservaAro(idCliente, idAro);
        reservaDAO.insertar(r);
        return r;
    }

    public List<ReservaAro> listarPorCliente(int idCliente) {
        return reservaDAO.listarPorCliente(idCliente);
    }

    public List<ReservaAro> listarActivas(int idCliente) {
        return reservaDAO.listarPorClienteYEstado(idCliente, ReservaAro.Estado.APARTADO);
    }

    public boolean cancelar(int idReserva) {
        return reservaDAO.actualizarEstado(idReserva, ReservaAro.Estado.CANCELADO);
    }

    public boolean eliminar(int id) {
        return reservaDAO.eliminar(id);
    }
}
