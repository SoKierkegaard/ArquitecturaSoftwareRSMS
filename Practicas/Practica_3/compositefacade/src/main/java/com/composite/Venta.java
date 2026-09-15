package com.composite;

import java.util.ArrayList;

public class Venta {
    private String nombre;
    private String fecha;
    private String tipodedocumento;
    private int numeroDocumento;
    private ArrayList<IDetalleVenta> Detalle;

    public Venta(String nombre, String fecha, String tipodedocumento, int numeroDocumento) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipodedocumento = tipodedocumento;
        this.numeroDocumento = numeroDocumento;
        this.Detalle = new ArrayList<>();
    }
    
    public void agregarDetalle(IDetalleVenta producto) {
        Detalle.add(producto);   
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < Detalle.size(); i++) {
            total += Detalle.get(i).getPrecio() * Detalle.get(i).getCantidad();
        }
        return total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipodedocumento() {
        return tipodedocumento;
    }

    public void setTipodedocumento(String tipodedocumento) {
        this.tipodedocumento = tipodedocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public ArrayList<IDetalleVenta> getDetalle() {
        return Detalle;
    }

    public void mostrarDetalle() {
        System.out.println("\n========================================================");
        System.out.println("            COMPROBANTE DE VENTA - MINISUPER            ");
        System.out.println("========================================================");
        System.out.println(" Cliente:          " + nombre);
        System.out.println(" Fecha:            " + fecha);
        System.out.println(" Tipo Documento:   " + tipodedocumento);
        System.out.println(" N° Documento:     " + numeroDocumento);
        System.out.println("--------------------------------------------------------");
        System.out.println(" Cant | Descripción                     | P.Unit | Subtotal");
        System.out.println("--------------------------------------------------------");
        
        if (Detalle.isEmpty()) {
            System.out.println("  (No hay productos en el detalle de la venta)");
        } else {
            for (IDetalleVenta item : Detalle) {
                double subtotal = item.getPrecio() * item.getCantidad();
                System.out.printf(" %-4d | %-31s | $%6.2f | $%7.2f%n",
                        item.getCantidad(),
                        recortarTexto(item.getDescripcion(), 31),
                        item.getPrecio(),
                        subtotal);
                
                // Si es un producto compuesto, mostrar su desglose interno
                if (item instanceof ProductoCompuesto) {
                    ProductoCompuesto compuesto = (ProductoCompuesto) item;
                    for (IDetalleVenta subItem : compuesto.getProductos()) {
                        System.out.printf("       -> Incluye: %dx %-20s ($%.2f c/u)%n",
                                subItem.getCantidad(),
                                recortarTexto(subItem.getDescripcion(), 20),
                                subItem.getPrecio());
                    }
                }
            }
        }
        
        System.out.println("--------------------------------------------------------");
        System.out.printf(" TOTAL A COBRAR:                                $%7.2f%n", getTotal());
        System.out.println("========================================================");
    }

    private String recortarTexto(String texto, int longitudMaxima) {
        if (texto == null) return "";
        if (texto.length() <= longitudMaxima) return texto;
        return texto.substring(0, longitudMaxima - 3) + "...";
    }
}
