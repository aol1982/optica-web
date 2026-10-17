package com.optica.dao.impl;

import com.optica.dao.ReservaAroDAO;
import com.optica.model.ReservaAro;
import com.optica.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaAroDAOImpl implements ReservaAroDAO {

    private static final String INSERT =
        "INSERT INTO reservas_aro(id_cliente, id_aro, estado) VALUES(?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM reservas_aro WHERE id_reserva=?";
    private static final String SELECT_BY_CLIENTE =
        "SELECT * FROM reservas_aro WHERE id_cliente=? ORDER BY fecha_reserva DESC";
    private static final String SELECT_BY_CLIENTE_ESTADO =
        "SELECT * FROM reservas_aro WHERE id_cliente=? AND estado=? ORDER BY fecha_reserva DESC";
    private static final String UPDATE_ESTADO =
        "UPDATE reservas_aro SET estado=? WHERE id_reserva=?";
    private static final String DELETE =
        "DELETE FROM reservas_aro WHERE id_reserva=?";
    private static final String EXISTS_ACTIVA =
        "SELECT 1 FROM reservas_aro WHERE id_cliente=? AND id_aro=? AND estado='APARTADO'";

    @Override
    public int insertar(ReservaAro r) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getIdCliente());
            ps.setInt(2, r.getIdAro());
            ps.setString(3, r.getEstado() != null ? r.getEstado().name() : ReservaAro.Estado.APARTADO.name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    r.setIdReserva(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReservaAro buscarPorId(int id) {
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
    public List<ReservaAro> listarPorCliente(int idCliente) {
        List<ReservaAro> lista = new ArrayList<>();
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
    public List<ReservaAro> listarPorClienteYEstado(int idCliente, ReservaAro.Estado estado) {
        List<ReservaAro> lista = new ArrayList<>();
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_CLIENTE_ESTADO)) {
            ps.setInt(1, idCliente);
            ps.setString(2, estado.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(int idReserva, ReservaAro.Estado nuevoEstado) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE_ESTADO)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idReserva);
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

    @Override
    public boolean existeActiva(int idCliente, int idAro) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(EXISTS_ACTIVA)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idAro);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ReservaAro mapear(ResultSet rs) throws SQLException {
        ReservaAro r = new ReservaAro();
        r.setIdReserva(rs.getInt("id_reserva"));
        r.setIdCliente(rs.getInt("id_cliente"));
        r.setIdAro(rs.getInt("id_aro"));
        r.setEstado(ReservaAro.Estado.valueOf(rs.getString("estado")));
        Timestamp ts = rs.getTimestamp("fecha_reserva");
        if (ts != null) r.setFechaReserva(ts.toLocalDateTime());
        return r;
    }
}
