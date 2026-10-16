package com.optica.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Medicion {
    private int idMedicion;
    private int idCliente;
    private String ojo;
    private BigDecimal esfera;
    private BigDecimal cilindro;
    private Integer eje;
    private BigDecimal adicion;
    private LocalDate fecha;

    public Medicion() {}

    public Medicion(int idCliente, String ojo, BigDecimal esfera,
                    BigDecimal cilindro, Integer eje,
                    BigDecimal adicion, LocalDate fecha) {
        this.idCliente = idCliente;
        this.ojo = ojo;
        this.esfera = esfera;
        this.cilindro = cilindro;
        this.eje = eje;
        this.adicion = adicion;
        this.fecha = fecha;
    }

    public int getIdMedicion() { return idMedicion; }
    public void setIdMedicion(int idMedicion) { this.idMedicion = idMedicion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getOjo() { return ojo; }
    public void setOjo(String ojo) { this.ojo = ojo; }

    public BigDecimal getEsfera() { return esfera; }
    public void setEsfera(BigDecimal esfera) { this.esfera = esfera; }

    public BigDecimal getCilindro() { return cilindro; }
    public void setCilindro(BigDecimal cilindro) { this.cilindro = cilindro; }

    public Integer getEje() { return eje; }
    public void setEje(Integer eje) { this.eje = eje; }

    public BigDecimal getAdicion() { return adicion; }
    public void setAdicion(BigDecimal adicion) { this.adicion = adicion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Medicion{" + ojo + " esf=" + esfera + ", cil=" + cilindro +
               ", eje=" + eje + ", add=" + adicion + ", fecha=" + fecha + '}';
    }
}
