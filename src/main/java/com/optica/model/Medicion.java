package com.optica.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Medicion {

    private int idMedicion;
    private int idCliente;
    private BigDecimal esferaOd;
    private BigDecimal cilindroOd;
    private Integer ejeOd;
    private BigDecimal esferaOi;
    private BigDecimal cilindroOi;
    private Integer ejeOi;
    private BigDecimal adicion;
    private LocalDateTime fechaRegistro;

    public Medicion() {}

    public int getIdMedicion() { return idMedicion; }
    public void setIdMedicion(int idMedicion) { this.idMedicion = idMedicion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public BigDecimal getEsferaOd() { return esferaOd; }
    public void setEsferaOd(BigDecimal esferaOd) { this.esferaOd = esferaOd; }

    public BigDecimal getCilindroOd() { return cilindroOd; }
    public void setCilindroOd(BigDecimal cilindroOd) { this.cilindroOd = cilindroOd; }

    public Integer getEjeOd() { return ejeOd; }
    public void setEjeOd(Integer ejeOd) { this.ejeOd = ejeOd; }

    public BigDecimal getEsferaOi() { return esferaOi; }
    public void setEsferaOi(BigDecimal esferaOi) { this.esferaOi = esferaOi; }

    public BigDecimal getCilindroOi() { return cilindroOi; }
    public void setCilindroOi(BigDecimal cilindroOi) { this.cilindroOi = cilindroOi; }

    public Integer getEjeOi() { return ejeOi; }
    public void setEjeOi(Integer ejeOi) { this.ejeOi = ejeOi; }

    public BigDecimal getAdicion() { return adicion; }
    public void setAdicion(BigDecimal adicion) { this.adicion = adicion; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return "Medicion{id=" + idMedicion + ", cliente=" + idCliente +
               ", OD=" + esferaOd + "/" + cilindroOd + "/" + ejeOd +
               ", OI=" + esferaOi + "/" + cilindroOi + "/" + ejeOi +
               ", add=" + adicion + '}';
    }
}
