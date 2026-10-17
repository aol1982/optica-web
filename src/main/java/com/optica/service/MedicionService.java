package com.optica.service;

import com.optica.dao.MedicionDAO;
import com.optica.dao.impl.MedicionDAOImpl;
import com.optica.model.Medicion;

import java.util.List;

public class MedicionService {

    private final MedicionDAO medicionDAO;

    public MedicionService() {
        this.medicionDAO = new MedicionDAOImpl();
    }

    public MedicionService(MedicionDAO medicionDAO) {
        this.medicionDAO = medicionDAO;
    }

    public int registrar(Medicion m) {
        if (m.getIdCliente() <= 0) throw new IllegalArgumentException("Cliente inválido");
        return medicionDAO.insertar(m);
    }

    public Medicion buscar(int id) {
        return medicionDAO.buscarPorId(id);
    }

    public List<Medicion> listarPorCliente(int idCliente) {
        return medicionDAO.listarPorCliente(idCliente);
    }

    public boolean actualizar(Medicion m) {
        if (m.getIdMedicion() <= 0) throw new IllegalArgumentException("ID inválido");
        return medicionDAO.actualizar(m);
    }

    public boolean eliminar(int id) {
        return medicionDAO.eliminar(id);
    }
}
