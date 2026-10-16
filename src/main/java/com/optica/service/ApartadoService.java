package com.optica.service;

import com.optica.dao.ApartadoDAO;
import com.optica.dao.AroDAO;
import com.optica.dao.impl.ApartadoDAOImpl;
import com.optica.dao.impl.AroDAOImpl;
import com.optica.model.Apartado;
import com.optica.model.Aro;

import java.util.List;

public class ApartadoService {

    private final ApartadoDAO apartadoDAO;
    private final AroDAO aroDAO;

    public ApartadoService() {
        this.apartadoDAO = new ApartadoDAOImpl();
        this.aroDAO = new AroDAOImpl();
    }

    public ApartadoService(ApartadoDAO apartadoDAO, AroDAO aroDAO) {
        this.apartadoDAO = apartadoDAO;
        this.aroDAO = aroDAO;
    }

    public Apartado apartar(int idCliente, int idAro) {
        if (idCliente <= 0 || idAro <= 0)
            throw new IllegalArgumentException("Cliente o aro inválidos");

        Aro aro = aroDAO.buscarPorId(idAro);
        if (aro == null) throw new IllegalArgumentException("El aro no existe");

        if (apartadoDAO.existe(idCliente, idAro))
            throw new IllegalStateException("Ya tienes ese aro apartado");

        Apartado a = new Apartado(idCliente, idAro);
        apartadoDAO.insertar(a);
        return a;
    }

    public List<Apartado> listarPorCliente(int idCliente) {
        return apartadoDAO.listarPorCliente(idCliente);
    }

    public boolean eliminar(int id) {
        return apartadoDAO.eliminar(id);
    }
}
