package com.composite;

import java.util.ArrayList;

public class ProductoCompuesto implements IDetalleVenta {

    private String descripcion;
    private int cantidad;
    private ArrayList<IDetalleVenta> productos;

    public ProductoCompuesto(String descripcion) {
        this(descripcion, 1);
    }

    public ProductoCompuesto(String descripcion, int cantidad) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(IDetalleVenta producto) {
        productos.add(producto);
    }

    public void removerProducto(IDetalleVenta producto) {
        productos.remove(producto);
    }

    public ArrayList<IDetalleVenta> getProductos() {
        return productos;
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
        double total = 0;
        for (IDetalleVenta producto : productos) {
            total += producto.getPrecio() * producto.getCantidad();
        }
        return total;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public IDetalleVenta clonarConCantidad(int nuevaCantidad) {
        ProductoCompuesto copia = new ProductoCompuesto(this.descripcion, nuevaCantidad);
        for (IDetalleVenta prod : this.productos) {
            copia.agregarProducto(prod);
        }
        return copia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Compuesto/Combo] ").append(descripcion)
          .append(" | Cantidad: ").append(cantidad)
          .append(" | Precio Unitario Combo: $").append(String.format("%.2f", getPrecio()))
          .append(" | Subtotal: $").append(String.format("%.2f", getPrecio() * cantidad));
        return sb.toString();
    }
}
