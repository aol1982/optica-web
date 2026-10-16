package com.optica.service;

import com.optica.dao.AroDAO;
import com.optica.dao.impl.AroDAOImpl;
import com.optica.model.Aro;

import java.math.BigDecimal;
import java.util.List;

public class AroService {

    public static final int UMBRAL_STOCK_BAJO = 5;

    private final AroDAO aroDAO;

    public AroService() {
        this.aroDAO = new AroDAOImpl();
    }

    public AroService(AroDAO aroDAO) {
        this.aroDAO = aroDAO;
    }

    public int crear(Aro a) {
        validar(a);
        return aroDAO.insertar(a);
    }

    public Aro buscar(int id) {
        return aroDAO.buscarPorId(id);
    }

    public List<Aro> listarTodos() {
        return aroDAO.listarTodos();
    }

    public List<Aro> filtrar(String marca, String material, String forma) {
        return aroDAO.filtrar(marca, material, forma);
    }

    public List<Aro> listarStockBajo() {
        return aroDAO.listarStockBajo(UMBRAL_STOCK_BAJO);
    }

    public boolean actualizar(Aro a) {
        if (a.getIdAro() <= 0) throw new IllegalArgumentException("ID inválido");
        validar(a);
        return aroDAO.actualizar(a);
    }

    public boolean eliminar(int id) {
        return aroDAO.eliminar(id);
    }

    public void descontarStock(int idAro, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser mayor a cero");
        boolean ok = aroDAO.descontarStock(idAro, cantidad);
        if (!ok) throw new IllegalStateException("Stock insuficiente para el aro id=" + idAro);
    }

    private void validar(Aro a) {
        if (a.getMarca() == null || a.getMarca().isBlank())
            throw new IllegalArgumentException("La marca es obligatoria");
        if (a.getPrecio() == null || a.getPrecio().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio debe ser >= 0");
        if (a.getStock() < 0)
            throw new IllegalArgumentException("El stock no puede ser negativo");
    }
}
