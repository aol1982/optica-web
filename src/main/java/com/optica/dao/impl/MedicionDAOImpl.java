package com.optica.dao.impl;

import com.optica.dao.MedicionDAO;
import com.optica.model.Medicion;
import com.optica.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicionDAOImpl implements MedicionDAO {

    private static final String INSERT =
        "INSERT INTO mediciones(id_cliente, esfera_od, cilindro_od, eje_od, " +
        "esfera_oi, cilindro_oi, eje_oi, adicion) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM mediciones WHERE id_medicion=?";
    private static final String SELECT_BY_CLIENTE =
        "SELECT * FROM mediciones WHERE id_cliente=? ORDER BY fecha_registro DESC";
    private static final String UPDATE =
        "UPDATE mediciones SET esfera_od=?, cilindro_od=?, eje_od=?, " +
        "esfera_oi=?, cilindro_oi=?, eje_oi=?, adicion=? WHERE id_medicion=?";
    private static final String DELETE =
        "DELETE FROM mediciones WHERE id_medicion=?";

    @Override
    public int insertar(Medicion m) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getIdCliente());
            ps.setBigDecimal(2, m.getEsferaOd());
            ps.setBigDecimal(3, m.getCilindroOd());
            setNullableInt(ps, 4, m.getEjeOd());
            ps.setBigDecimal(5, m.getEsferaOi());
            ps.setBigDecimal(6, m.getCilindroOi());
            setNullableInt(ps, 7, m.getEjeOi());
            ps.setBigDecimal(8, m.getAdicion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    m.setIdMedicion(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Medicion buscarPorId(int id) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Medicion> listarPorCliente(int idCliente) {
        List<Medicion> lista = new ArrayList<>();
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_CLIENTE)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Medicion m) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setBigDecimal(1, m.getEsferaOd());
            ps.setBigDecimal(2, m.getCilindroOd());
            setNullableInt(ps, 3, m.getEjeOd());
            ps.setBigDecimal(4, m.getEsferaOi());
            ps.setBigDecimal(5, m.getCilindroOi());
            setNullableInt(ps, 6, m.getEjeOi());
            ps.setBigDecimal(7, m.getAdicion());
            ps.setInt(8, m.getIdMedicion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNullableInt(PreparedStatement ps, int idx, Integer v) throws SQLException {
        if (v != null) ps.setInt(idx, v);
        else ps.setNull(idx, Types.INTEGER);
    }

    private Medicion mapear(ResultSet rs) throws SQLException {
        Medicion m = new Medicion();
        m.setIdMedicion(rs.getInt("id_medicion"));
        m.setIdCliente(rs.getInt("id_cliente"));
        m.setEsferaOd(rs.getBigDecimal("esfera_od"));
        m.setCilindroOd(rs.getBigDecimal("cilindro_od"));
        int ejeOd = rs.getInt("eje_od");
        m.setEjeOd(rs.wasNull() ? null : ejeOd);
        m.setEsferaOi(rs.getBigDecimal("esfera_oi"));
        m.setCilindroOi(rs.getBigDecimal("cilindro_oi"));
        int ejeOi = rs.getInt("eje_oi");
        m.setEjeOi(rs.wasNull() ? null : ejeOi);
        m.setAdicion(rs.getBigDecimal("adicion"));
        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) m.setFechaRegistro(ts.toLocalDateTime());
        return m;
    }
}
