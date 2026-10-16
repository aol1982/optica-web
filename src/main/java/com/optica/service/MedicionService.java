package com.optica.service;

import com.optica.dao.MedicionDAO;
import com.optica.dao.impl.MedicionDAOImpl;
import com.optica.model.Medicion;

import java.time.LocalDate;
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
        validar(m);
        if (m.getFecha() == null) m.setFecha(LocalDate.now());
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
        validar(m);
        return medicionDAO.actualizar(m);
    }

    public boolean eliminar(int id) {
        return medicionDAO.eliminar(id);
    }

    private void validar(Medicion m) {
        if (m.getIdCliente() <= 0) throw new IllegalArgumentException("Cliente inválido");
        if (m.getOjo() == null || (!m.getOjo().equals("OD") && !m.getOjo().equals("OI")))
            throw new IllegalArgumentException("El ojo debe ser 'OD' o 'OI'");
    }
}
