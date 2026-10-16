package com.optica.dao;

import com.optica.model.Apartado;
import java.util.List;

public interface ApartadoDAO {
    int insertar(Apartado a);
    List<Apartado> listarPorCliente(int idCliente);
    boolean eliminar(int id);
    boolean existe(int idCliente, int idAro);
}
