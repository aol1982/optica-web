package com.optica.dao.impl;

import com.optica.dao.ApartadoDAO;
import com.optica.model.Apartado;
import com.optica.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartadoDAOImpl implements ApartadoDAO {

    private static final String INSERT =
        "INSERT INTO apartados(id_cliente, id_aro) VALUES(?,?)";
    private static final String SELECT_BY_CLIENTE =
        "SELECT * FROM apartados WHERE id_cliente=? ORDER BY fecha DESC";
    private static final String DELETE =
        "DELETE FROM apartados WHERE id_apartado=?";
    private static final String EXISTS =
        "SELECT 1 FROM apartados WHERE id_cliente=? AND id_aro=?";

    @Override
    public int insertar(Apartado a) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getIdCliente());
            ps.setInt(2, a.getIdAro());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    a.setIdApartado(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Apartado> listarPorCliente(int idCliente) {
        List<Apartado> lista = new ArrayList<>();
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
    public boolean eliminar(int id) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existe(int idCliente, int idAro) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(EXISTS)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idAro);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Apartado mapear(ResultSet rs) throws SQLException {
        Apartado a = new Apartado();
        a.setIdApartado(rs.getInt("id_apartado"));
        a.setIdCliente(rs.getInt("id_cliente"));
        a.setIdAro(rs.getInt("id_aro"));
        Timestamp ts = rs.getTimestamp("fecha");
        if (ts != null) a.setFecha(ts.toLocalDateTime());
        return a;
    }
}
