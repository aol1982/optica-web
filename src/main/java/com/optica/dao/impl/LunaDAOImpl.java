package com.optica.dao.impl;

import com.optica.dao.LunaDAO;
import com.optica.model.Luna;
import com.optica.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LunaDAOImpl implements LunaDAO {

    private static final String INSERT =
        "INSERT INTO lunas(tipo, material, precio, stock) VALUES(?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM lunas WHERE id_luna=?";
    private static final String SELECT_ALL =
        "SELECT * FROM lunas ORDER BY tipo";
    private static final String UPDATE =
        "UPDATE lunas SET tipo=?, material=?, precio=?, stock=? WHERE id_luna=?";
    private static final String DELETE =
        "DELETE FROM lunas WHERE id_luna=?";
    private static final String DESCONTAR_STOCK =
        "UPDATE lunas SET stock=stock-? WHERE id_luna=? AND stock>=?";

    @Override
    public int insertar(Luna l) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, l.getTipo());
            ps.setString(2, l.getMaterial());
            ps.setBigDecimal(3, l.getPrecio());
            ps.setInt(4, l.getStock());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    l.setIdLuna(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Luna buscarPorId(int id) {
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
    public List<Luna> listarTodas() {
        List<Luna> lista = new ArrayList<>();
        try (Connection c = Conexion.getConexion();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Luna l) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, l.getTipo());
            ps.setString(2, l.getMaterial());
            ps.setBigDecimal(3, l.getPrecio());
            ps.setInt(4, l.getStock());
            ps.setInt(5, l.getIdLuna());
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
    public boolean descontarStock(int idLuna, int cantidad) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(DESCONTAR_STOCK)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idLuna);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Luna mapear(ResultSet rs) throws SQLException {
        Luna l = new Luna();
        l.setIdLuna(rs.getInt("id_luna"));
        l.setTipo(rs.getString("tipo"));
        l.setMaterial(rs.getString("material"));
        l.setPrecio(rs.getBigDecimal("precio"));
        l.setStock(rs.getInt("stock"));
        return l;
    }
}
