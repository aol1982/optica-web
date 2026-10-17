package com.optica.model;

public class Usuario {

    public enum Rol { CLIENTE, VENDEDOR }

    private int idUsuario;
    private String correo;
    private String clave;
    private Rol rol;

    public Usuario() {}

    public Usuario(String correo, String clave, Rol rol) {
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", correo='" + correo + "', rol=" + rol + '}';
    }
}
