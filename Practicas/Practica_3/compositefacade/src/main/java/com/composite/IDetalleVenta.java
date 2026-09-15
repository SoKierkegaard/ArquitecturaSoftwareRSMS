package com.composite;

public interface IDetalleVenta {
    double getPrecio();
    int getCantidad();
    String getDescripcion();
    IDetalleVenta clonarConCantidad(int nuevaCantidad);
}
