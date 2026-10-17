package com.optica.model;

import java.time.LocalDateTime;

public class ReservaAro {

    public enum Estado { APARTADO, PROCESADO, CANCELADO }

    private int idReserva;
    private int idCliente;
    private int idAro;
    private Estado estado;
    private LocalDateTime fechaReserva;

    public ReservaAro() {}

    public ReservaAro(int idCliente, int idAro) {
        this.idCliente = idCliente;
        this.idAro = idAro;
        this.estado = Estado.APARTADO;
    }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdAro() { return idAro; }
    public void setIdAro(int idAro) { this.idAro = idAro; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    @Override
    public String toString() {
        return "ReservaAro{id=" + idReserva + ", cliente=" + idCliente +
               ", aro=" + idAro + ", estado=" + estado + '}';
    }
}
