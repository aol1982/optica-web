package com.optica.dao.impl;

import com.optica.dao.ClienteDAO;
import com.optica.model.Cliente;
import com.optica.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAOImpl implements ClienteDAO {

    private static final String INSERT =
        "INSERT INTO clientes(id_usuario, nombre, dni, telefono) VALUES(?,?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM clientes WHERE id_cliente=?";
    private static final String SELECT_BY_USUARIO =
        "SELECT * FROM clientes WHERE id_usuario=?";
    private static final String SELECT_BY_DNI =
        "SELECT * FROM clientes WHERE dni=?";
    private static final String SELECT_ALL =
        "SELECT * FROM clientes ORDER BY nombre";
    private static final String UPDATE =
        "UPDATE clientes SET nombre=?, dni=?, telefono=? WHERE id_cliente=?";
    private static final String DELETE =
        "DELETE FROM clientes WHERE id_cliente=?";
    private static final String EXISTS_DNI =
        "SELECT 1 FROM clientes WHERE dni=?";

    @Override
    public int insertar(Cliente c) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getIdUsuario());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getTelefono());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    c.setIdCliente(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorIdUsuario(int idUsuario) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_USUARIO)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_DNI)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
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
    public boolean actualizar(Cliente c) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getTelefono());
            ps.setInt(4, c.getIdCliente());
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
    public boolean existeDni(String dni) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(EXISTS_DNI)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setNombre(rs.getString("nombre"));
        c.setDni(rs.getString("dni"));
        c.setTelefono(rs.getString("telefono"));
        return c;
    }
}
