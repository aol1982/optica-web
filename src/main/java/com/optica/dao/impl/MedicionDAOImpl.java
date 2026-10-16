package com.optica.dao.impl;

import com.optica.dao.MedicionDAO;
import com.optica.model.Medicion;
import com.optica.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicionDAOImpl implements MedicionDAO {

    private static final String INSERT =
        "INSERT INTO mediciones(id_cliente, ojo, esfera, cilindro, eje, adicion, fecha) VALUES(?,?,?,?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM mediciones WHERE id_medicion=?";
    private static final String SELECT_BY_CLIENTE =
        "SELECT * FROM mediciones WHERE id_cliente=? ORDER BY fecha DESC";
    private static final String UPDATE =
        "UPDATE mediciones SET id_cliente=?, ojo=?, esfera=?, cilindro=?, eje=?, adicion=?, fecha=? WHERE id_medicion=?";
    private static final String DELETE =
        "DELETE FROM mediciones WHERE id_medicion=?";

    @Override
    public int insertar(Medicion m) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getIdCliente());
            ps.setString(2, m.getOjo());
            ps.setBigDecimal(3, m.getEsfera());
            ps.setBigDecimal(4, m.getCilindro());
            if (m.getEje() != null) ps.setInt(5, m.getEje());
            else ps.setNull(5, Types.INTEGER);
            ps.setBigDecimal(6, m.getAdicion());
            ps.setDate(7, Date.valueOf(m.getFecha()));
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
        try (Connection c = ConexionBD.getConexion();
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
        try (Connection c = ConexionBD.getConexion();
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
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setInt(1, m.getIdCliente());
            ps.setString(2, m.getOjo());
            ps.setBigDecimal(3, m.getEsfera());
            ps.setBigDecimal(4, m.getCilindro());
            if (m.getEje() != null) ps.setInt(5, m.getEje());
            else ps.setNull(5, Types.INTEGER);
            ps.setBigDecimal(6, m.getAdicion());
            ps.setDate(7, Date.valueOf(m.getFecha()));
            ps.setInt(8, m.getIdMedicion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Medicion mapear(ResultSet rs) throws SQLException {
        Medicion m = new Medicion();
        m.setIdMedicion(rs.getInt("id_medicion"));
        m.setIdCliente(rs.getInt("id_cliente"));
        m.setOjo(rs.getString("ojo"));
        m.setEsfera(rs.getBigDecimal("esfera"));
        m.setCilindro(rs.getBigDecimal("cilindro"));
        int eje = rs.getInt("eje");
        m.setEje(rs.wasNull() ? null : eje);
        m.setAdicion(rs.getBigDecimal("adicion"));
        Date fecha = rs.getDate("fecha");
        if (fecha != null) m.setFecha(fecha.toLocalDate());
        return m;
    }
}
