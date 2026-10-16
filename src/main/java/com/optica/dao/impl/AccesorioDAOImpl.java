package com.optica.dao.impl;

import com.optica.dao.AccesorioDAO;
import com.optica.model.Accesorio;
import com.optica.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccesorioDAOImpl implements AccesorioDAO {

    private static final String INSERT =
        "INSERT INTO accesorios(nombre, descripcion, precio, stock) VALUES(?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM accesorios WHERE id_accesorio=?";
    private static final String SELECT_ALL =
        "SELECT * FROM accesorios ORDER BY nombre";
    private static final String UPDATE =
        "UPDATE accesorios SET nombre=?, descripcion=?, precio=?, stock=? WHERE id_accesorio=?";
    private static final String DELETE =
        "DELETE FROM accesorios WHERE id_accesorio=?";

    @Override
    public int insertar(Accesorio a) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setBigDecimal(3, a.getPrecio());
            ps.setInt(4, a.getStock());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    a.setIdAccesorio(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Accesorio buscarPorId(int id) {
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
    public List<Accesorio> listarTodos() {
        List<Accesorio> lista = new ArrayList<>();
        try (Connection c = ConexionBD.getConexion();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Accesorio a) {
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setBigDecimal(3, a.getPrecio());
            ps.setInt(4, a.getStock());
            ps.setInt(5, a.getIdAccesorio());
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

    private Accesorio mapear(ResultSet rs) throws SQLException {
        Accesorio a = new Accesorio();
        a.setIdAccesorio(rs.getInt("id_accesorio"));
        a.setNombre(rs.getString("nombre"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setPrecio(rs.getBigDecimal("precio"));
        a.setStock(rs.getInt("stock"));
        return a;
    }
}
