package com.optica.dao;

import com.optica.model.Aro;
import java.util.List;

public interface AroDAO {
    int insertar(Aro a);
    Aro buscarPorId(int id);
    List<Aro> listarTodos();
    List<Aro> filtrar(String marca, String material, String forma);
    List<Aro> listarStockBajo(int umbral);
    boolean actualizar(Aro a);
    boolean eliminar(int id);
    boolean descontarStock(int idAro, int cantidad);
}
