package com.optica.service;

import com.optica.dao.*;
import com.optica.dao.impl.*;
import com.optica.model.*;
import com.optica.util.Conexion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VentaService {

    public static final BigDecimal IMPUESTO = new BigDecimal("0.15");

    private final VentaDAO ventaDAO;
    private final AroDAO aroDAO;
    private final LunaDAO lunaDAO;

    public VentaService() {
        this.ventaDAO = new VentaDAOImpl();
        this.aroDAO = new AroDAOImpl();
        this.lunaDAO = new LunaDAOImpl();
    }

    public VentaService(VentaDAO ventaDAO, AroDAO aroDAO, LunaDAO lunaDAO) {
        this.ventaDAO = ventaDAO;
        this.aroDAO = aroDAO;
        this.lunaDAO = lunaDAO;
    }

    public Venta registrarVenta(int idCliente, int idVendedor, int idMedicion,
                                 Integer idAro, Integer idLuna, Integer idAccesorio,
                                 int cantidad) {

        if (idCliente <= 0 || idVendedor <= 0)
            throw new IllegalArgumentException("Cliente y vendedor son obligatorios");
        if (idMedicion <= 0)
            throw new IllegalArgumentException("La medición es obligatoria");
        if (idAro == null && idLuna == null && idAccesorio == null)
            throw new IllegalArgumentException("Debe seleccionar al menos un producto");
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");

        BigDecimal precioUnitario = BigDecimal.ZERO;

        if (idAro != null) {
            Aro aro = aroDAO.buscarPorId(idAro);
            if (aro == null) throw new IllegalArgumentException("El aro no existe");
            if (aro.getStock() < cantidad)
                throw new IllegalStateException("Stock insuficiente del aro: " + aro.getStock());
            precioUnitario = precioUnitario.add(aro.getPrecio());
        }

        if (idLuna != null) {
            Luna luna = lunaDAO.buscarPorId(idLuna);
            if (luna == null) throw new IllegalArgumentException("La luna no existe");
            if (luna.getStock() < cantidad)
                throw new IllegalStateException("Stock insuficiente de la luna: " + luna.getStock());
            precioUnitario = precioUnitario.add(luna.getPrecio());
        }

        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad))
                                            .setScale(2, RoundingMode.HALF_UP);
        BigDecimal impuesto = subtotal.multiply(IMPUESTO)
                                      .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(impuesto);

        Venta venta = new Venta();
        venta.setIdCliente(idCliente);
        venta.setIdVendedor(idVendedor);
        venta.setIdMedicion(idMedicion);
        venta.setSubtotal(subtotal);
        venta.setImpuesto(impuesto);
        venta.setTotal(total);

        DetalleVenta detalle = new DetalleVenta(idAro, idLuna, idAccesorio, cantidad, precioUnitario);
        venta.agregarDetalle(detalle);

        Connection c = null;
        try {
            c = Conexion.getConexion();
            c.setAutoCommit(false);

            ventaDAO.insertar(venta, c);

            if (idAro != null) {
                boolean ok = aroDAO.descontarStock(idAro, cantidad);
                if (!ok) throw new SQLException("No se pudo descontar stock del aro");
            }
            if (idLuna != null) {
                boolean ok = lunaDAO.descontarStock(idLuna, cantidad);
                if (!ok) throw new SQLException("No se pudo descontar stock de la luna");
            }

            c.commit();
            return venta;
        } catch (SQLException | RuntimeException e) {
            if (c != null) {
                try { c.rollback(); } catch (SQLException ignored) {}
            }
            throw new RuntimeException("Venta cancelada: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                try { c.setAutoCommit(true); } catch (SQLException ignored) {}
                Conexion.cerrar(c);
            }
        }
    }

    public Venta buscar(int id) {
        return ventaDAO.buscarPorId(id);
    }

    public List<Venta> listarTodas() {
        return ventaDAO.listarTodas();
    }

    public List<Venta> listarPorCliente(int idCliente) {
        return ventaDAO.listarPorCliente(idCliente);
    }
}
