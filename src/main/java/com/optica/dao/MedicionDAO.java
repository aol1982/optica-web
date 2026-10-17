package com.optica.dao;

import com.optica.model.Medicion;
import java.util.List;

public interface MedicionDAO {

    int insertar(Medicion m);
    Medicion buscarPorId(int id);
    List<Medicion> listarPorCliente(int idCliente);
    boolean actualizar(Medicion m);
    boolean eliminar(int id);
}
