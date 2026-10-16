package com.optica.dao;

import com.optica.model.Venta;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface VentaDAO {
    int insertar(Venta v, Connection c) throws SQLException;
    Venta buscarPorId(int id);
    List<Venta> listarTodas();
    List<Venta> listarPorCliente(int idCliente);
}
