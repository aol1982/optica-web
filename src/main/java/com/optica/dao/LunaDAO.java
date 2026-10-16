package com.optica.dao;

import com.optica.model.Luna;
import java.util.List;

public interface LunaDAO {
    int insertar(Luna l);
    Luna buscarPorId(int id);
    List<Luna> listarTodas();
    boolean actualizar(Luna l);
    boolean eliminar(int id);
    boolean descontarStock(int idLuna, int cantidad);
}
