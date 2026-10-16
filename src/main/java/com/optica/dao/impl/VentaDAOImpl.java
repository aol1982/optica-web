package com.optica.dao.impl;

import com.optica.dao.VentaDAO;
import com.optica.model.DetalleVenta;
import com.optica.model.Venta;
import com.optica.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    private static final String INSERT_VENTA =
        "INSERT INTO ventas(id_cliente, id_vendedor, id_medicion, subtotal, impuesto, total) VALUES(?,?,?,?,?,?)";
    private static final String INSERT_DETALLE =
        "INSERT INTO detalle_venta(id_venta, id_aro, id_luna, cantidad, precio_unitario) VALUES(?,?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM ventas WHERE id_venta=?";
    private static final String SELECT_ALL =
        "SELECT * FROM ventas ORDER BY fecha DESC";
    private static final String SELECT_BY_CLIENTE =
        "SELECT * FROM ventas WHERE id_cliente=? ORDER BY fecha DESC";
    private static final String SELECT_DETALLES =
        "SELECT * FROM detalle_venta WHERE id_venta=?";

    @Override
    public int insertar(Venta v, Connection c) throws SQLException {
        // La conexión viene desde fuera (para participar en una transacción)
        int idVenta;
        try (PreparedStatement ps = c.prepareStatement(INSERT_VENTA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, v.getIdCliente());
            ps.setInt(2, v.getIdVendedor());
            if (v.getIdMedicion() != null) ps.setInt(3, v.getIdMedicion());
            else ps.setNull(3, Types.INTEGER);
            ps.setBigDecimal(4, v.getSubtotal());
            ps.setBigDecimal(5, v.getImpuesto());
            ps.setBigDecimal(6, v.getTotal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) throw new SQLException("No se generó id de venta");
                idVenta = rs.getInt(1);
            }
        }
        v.setIdVenta(idVenta);

        // Insertar los detalles
        try (PreparedStatement ps = c.prepareStatement(INSERT_DETALLE, Statement.RETURN_GENERATED_KEYS)) {
            for (DetalleVenta d : v.getDetalles()) {
                ps.setInt(1, idVenta);
                if (d.getIdAro() != null) ps.setInt(2, d.getIdAro());
                else ps.setNull(2, Types.INTEGER);
                if (d.getIdLuna() != null) ps.setInt(3, d.getIdLuna());
                else ps.setNull(3, Types.INTEGER);
                ps.setInt(4, d.getCantidad());
                ps.setBigDecimal(5, d.getPrecioUnitario());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) d.setIdDetalle(rs.getInt(1));
                }
                d.setIdVenta(idVenta);
            }
        }
        return idVenta;
    }

    @Override
    public Venta buscarPorId(int id) {
        try (Connection c = ConexionBD.getConexion()) {
            Venta v = null;
            try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) v = mapearVenta(rs);
                }
            }
            if (v != null) cargarDetalles(c, v);
            return v;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Venta> listarTodas() {
        return listarConQuery(SELECT_ALL, null);
    }

    @Override
    public List<Venta> listarPorCliente(int idCliente) {
        return listarConQuery(SELECT_BY_CLIENTE, idCliente);
    }

    private List<Venta> listarConQuery(String sql, Integer param) {
        List<Venta> lista = new ArrayList<>();
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (param != null) ps.setInt(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta v = mapearVenta(rs);
                    lista.add(v);
                }
            }
            // Cargar detalles de cada venta
            for (Venta v : lista) cargarDetalles(c, v);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    private void cargarDetalles(Connection c, Venta v) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SELECT_DETALLES)) {
            ps.setInt(1, v.getIdVenta());
            try (ResultSet rs = ps.executeQuery()) {
                List<DetalleVenta> detalles = new ArrayList<>();
                while (rs.next()) {
                    DetalleVenta d = new DetalleVenta();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setIdVenta(rs.getInt("id_venta"));
                    int idAro = rs.getInt("id_aro");
                    d.setIdAro(rs.wasNull() ? null : idAro);
                    int idLuna = rs.getInt("id_luna");
                    d.setIdLuna(rs.wasNull() ? null : idLuna);
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                    detalles.add(d);
                }
                v.setDetalles(detalles);
            }
        }
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta v = new Venta();
        v.setIdVenta(rs.getInt("id_venta"));
        v.setIdCliente(rs.getInt("id_cliente"));
        v.setIdVendedor(rs.getInt("id_vendedor"));
        int idMed = rs.getInt("id_medicion");
        v.setIdMedicion(rs.wasNull() ? null : idMed);
        Timestamp ts = rs.getTimestamp("fecha");
        if (ts != null) v.setFecha(ts.toLocalDateTime());
        v.setSubtotal(rs.getBigDecimal("subtotal"));
        v.setImpuesto(rs.getBigDecimal("impuesto"));
        v.setTotal(rs.getBigDecimal("total"));
        return v;
    }
}
