package com.optica.dao.impl;

import com.optica.dao.AroDAO;
import com.optica.model.Aro;
import com.optica.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AroDAOImpl implements AroDAO {

    private static final String INSERT =
        "INSERT INTO aros(marca, material, forma, color, precio, stock) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM aros WHERE id_aro=?";
    private static final String SELECT_ALL =
        "SELECT * FROM aros ORDER BY marca, forma";
    private static final String UPDATE =
        "UPDATE aros SET marca=?, material=?, forma=?, color=?, precio=?, stock=? WHERE id_aro=?";
    private static final String DELETE =
        "DELETE FROM aros WHERE id_aro=?";
    private static final String DESCONTAR_STOCK =
        "UPDATE aros SET stock=stock-? WHERE id_aro=? AND stock>=?";
    private static final String STOCK_BAJO =
        "SELECT * FROM aros WHERE stock < ? ORDER BY stock";

    @Override
    public int insertar(Aro a) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getMarca());
            ps.setString(2, a.getMaterial());
            ps.setString(3, a.getForma());
            ps.setString(4, a.getColor());
            ps.setBigDecimal(5, a.getPrecio());
            ps.setInt(6, a.getStock());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    a.setIdAro(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar aro: " + e.getMessage(), e);
        }
    }

    @Override
    public Aro buscarPorId(int id) {
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
    public List<Aro> listarTodos() {
        List<Aro> lista = new ArrayList<>();
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
    public List<Aro> filtrar(String marca, String material, String forma) {
        StringBuilder sql = new StringBuilder("SELECT * FROM aros WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (marca != null && !marca.isBlank())    { sql.append(" AND marca=?");    params.add(marca); }
        if (material != null && !material.isBlank()) { sql.append(" AND material=?"); params.add(material); }
        if (forma != null && !forma.isBlank())    { sql.append(" AND forma=?");    params.add(forma); }

        sql.append(" ORDER BY marca");

        List<Aro> lista = new ArrayList<>();
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<Aro> listarStockBajo(int umbral) {
        List<Aro> lista = new ArrayList<>();
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(STOCK_BAJO)) {
            ps.setInt(1, umbral);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Aro a) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, a.getMarca());
            ps.setString(2, a.getMaterial());
            ps.setString(3, a.getForma());
            ps.setString(4, a.getColor());
            ps.setBigDecimal(5, a.getPrecio());
            ps.setInt(6, a.getStock());
            ps.setInt(7, a.getIdAro());
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
    public boolean descontarStock(int idAro, int cantidad) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(DESCONTAR_STOCK)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idAro);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Aro mapear(ResultSet rs) throws SQLException {
        Aro a = new Aro();
        a.setIdAro(rs.getInt("id_aro"));
        a.setMarca(rs.getString("marca"));
        a.setMaterial(rs.getString("material"));
        a.setForma(rs.getString("forma"));
        a.setColor(rs.getString("color"));
        a.setPrecio(rs.getBigDecimal("precio"));
        a.setStock(rs.getInt("stock"));
        return a;
    }
}
