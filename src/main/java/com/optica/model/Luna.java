package com.optica.model;

import java.math.BigDecimal;

public class Luna {
    private int idLuna;
    private String tipo;
    private String material;
    private BigDecimal precio;
    private int stock;

    public Luna() {}

    public Luna(String tipo, String material, BigDecimal precio, int stock) {
        this.tipo = tipo;
        this.material = material;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdLuna() { return idLuna; }
    public void setIdLuna(int idLuna) { this.idLuna = idLuna; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return tipo + " - " + material + " ($" + precio + ")";
    }
}
