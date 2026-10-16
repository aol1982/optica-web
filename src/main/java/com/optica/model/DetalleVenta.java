package com.optica.model;

import java.math.BigDecimal;

public class DetalleVenta {
    private int idDetalle;
    private int idVenta;
    private Integer idAro;
    private Integer idLuna;
    private int cantidad;
    private BigDecimal precioUnitario;

    public DetalleVenta() {}

    public DetalleVenta(Integer idAro, Integer idLuna,
                        int cantidad, BigDecimal precioUnitario) {
        this.idAro = idAro;
        this.idLuna = idLuna;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public Integer getIdAro() { return idAro; }
    public void setIdAro(Integer idAro) { this.idAro = idAro; }

    public Integer getIdLuna() { return idLuna; }
    public void setIdLuna(Integer idLuna) { this.idLuna = idLuna; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    @Override
    public String toString() {
        return "DetalleVenta{aro=" + idAro + ", luna=" + idLuna +
               ", cant=" + cantidad + ", precio=" + precioUnitario + '}';
    }
}
