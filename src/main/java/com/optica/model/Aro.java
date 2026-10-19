package com.optica.model;

import java.math.BigDecimal;

public class Aro {
    private int idAro;
    private String marca;
    private String material;
    private String forma;
    private String color;
    private BigDecimal precio;
    private int stock;
    private String imagenUrl;

    public Aro() {}

    public Aro(String marca, String material, String forma, String color, BigDecimal precio, int stock) {
        this.marca = marca;
        this.material = material;
        this.forma = forma;
        this.color = color;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdAro() { return idAro; }
    public void setIdAro(int idAro) { this.idAro = idAro; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getForma() { return forma; }
    public void setForma(String forma) { this.forma = forma; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    @Override
    public String toString() {
        return marca + " - " + material + " - " + forma + " ($" + precio + ")";
    }
}