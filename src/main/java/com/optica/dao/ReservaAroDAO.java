package com.optica.dao;

import com.optica.model.ReservaAro;
import java.util.List;

public interface ReservaAroDAO {

    int insertar(ReservaAro r);
    ReservaAro buscarPorId(int id);
    List<ReservaAro> listarPorCliente(int idCliente);
    List<ReservaAro> listarPorClienteYEstado(int idCliente, ReservaAro.Estado estado);
    boolean actualizarEstado(int idReserva, ReservaAro.Estado nuevoEstado);
    boolean eliminar(int id);
    boolean existeActiva(int idCliente, int idAro);
}
