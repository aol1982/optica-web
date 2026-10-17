package com.optica.dao.impl;

import com.optica.dao.UsuarioDAO;
import com.optica.model.Usuario;
import com.optica.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String INSERT =
        "INSERT INTO usuarios(correo, clave, rol) VALUES(?,?,?)";
    private static final String SELECT_BY_ID =
        "SELECT * FROM usuarios WHERE id_usuario=?";
    private static final String SELECT_BY_CORREO =
        "SELECT * FROM usuarios WHERE correo=?";
    private static final String SELECT_ALL =
        "SELECT * FROM usuarios ORDER BY correo";
    private static final String SELECT_BY_ROL =
        "SELECT * FROM usuarios WHERE rol=? ORDER BY correo";
    private static final String UPDATE =
        "UPDATE usuarios SET correo=?, clave=?, rol=? WHERE id_usuario=?";
    private static final String DELETE =
        "DELETE FROM usuarios WHERE id_usuario=?";
    private static final String EXISTS_CORREO =
        "SELECT 1 FROM usuarios WHERE correo=?";

    @Override
    public int insertar(Usuario u) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getRol().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    u.setIdUsuario(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
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
    public Optional<Usuario> buscarPorCorreo(String correo) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
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
    public List<Usuario> listarPorRol(Usuario.Rol rol) {
        List<Usuario> lista = new ArrayList<>();
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ROL)) {
            ps.setString(1, rol.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario u) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getRol().name());
            ps.setInt(4, u.getIdUsuario());
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
    public boolean existeCorreo(String correo) {
        try (Connection c = Conexion.getConexion();
             PreparedStatement ps = c.prepareStatement(EXISTS_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setCorreo(rs.getString("correo"));
        u.setClave(rs.getString("clave"));
        u.setRol(Usuario.Rol.valueOf(rs.getString("rol")));
        return u;
    }
}
