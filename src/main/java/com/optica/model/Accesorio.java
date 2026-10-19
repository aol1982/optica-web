package com.optica.model;

import java.math.BigDecimal;

public class Accesorio {
    private int idAccesorio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private String imagenUrl;

    public Accesorio() {}

    public Accesorio(String nombre, String descripcion, BigDecimal precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdAccesorio() { return idAccesorio; }
    public void setIdAccesorio(int idAccesorio) { this.idAccesorio = idAccesorio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }
}