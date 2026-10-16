package com.optica.model;

import java.time.LocalDateTime;

public class Apartado {
    private int idApartado;
    private int idCliente;
    private int idAro;
    private LocalDateTime fecha;

    public Apartado() {}

    public Apartado(int idCliente, int idAro) {
        this.idCliente = idCliente;
        this.idAro = idAro;
    }

    public int getIdApartado() { return idApartado; }
    public void setIdApartado(int idApartado) { this.idApartado = idApartado; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdAro() { return idAro; }
    public void setIdAro(int idAro) { this.idAro = idAro; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Apartado{cliente=" + idCliente + ", aro=" + idAro +
               ", fecha=" + fecha + '}';
    }
}
