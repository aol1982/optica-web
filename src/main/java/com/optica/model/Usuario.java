package com.optica.model;

public class Usuario {

    public enum Rol { CLIENTE, VENDEDOR }

    private int idUsuario;
    private String nombre;
    private String dni;
    private String telefono;
    private String correo;
    private String passwordHash;
    private Rol rol;

    public Usuario() {}

    public Usuario(String nombre, String dni, String telefono,
                   String correo, String passwordHash, Rol rol) {
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario +
               ", nombre='" + nombre + '\'' +
               ", correo='" + correo + '\'' +
               ", rol=" + rol + '}';
    }
}
