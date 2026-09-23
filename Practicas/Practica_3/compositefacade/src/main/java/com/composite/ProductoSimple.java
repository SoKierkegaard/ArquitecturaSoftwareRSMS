package com.composite;

public class ProductoSimple implements IDetalleVenta {

    private String descripcion;
    private int cantidad;
    private double precio;

    public ProductoSimple(String descripcion, double precio) {
        this(descripcion, 1, precio);
    }

    public ProductoSimple(String descripcion, int cantidad, double precio) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return "[Simple] " + descripcion + " | Cantidad: " + cantidad + " | Precio Unitario: $" + String.format("%.2f", precio) + " | Subtotal: $" + String.format("%.2f", precio * cantidad);
    }
}
