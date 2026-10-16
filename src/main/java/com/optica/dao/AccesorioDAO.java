package com.optica.dao;

import com.optica.model.Accesorio;
import java.util.List;

public interface AccesorioDAO {
    int insertar(Accesorio a);
    Accesorio buscarPorId(int id);
    List<Accesorio> listarTodos();
    boolean actualizar(Accesorio a);
    boolean eliminar(int id);
}
