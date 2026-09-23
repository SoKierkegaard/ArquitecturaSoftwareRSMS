package com.composite;

import com.composite.pago.MetodoPago;
import com.composite.pago.PagoFactory;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Patrón Facade: Provee una interfaz unificada y simplificada para interactuar
 * con el catálogo de productos (Composite), el proceso de venta y los métodos de pago (Factory).
 */
public class SupermercadoFacade {

    private ArrayList<IDetalleVenta> catalogo;
    private Venta ventaActual;

    public SupermercadoFacade() {
        this.catalogo = new ArrayList<>();
        this.ventaActual = null;
        inicializarCatalogoPredeterminado();
    }

    /**
     * Carga un conjunto inicial de productos simples y compuestos (combos) al catálogo.
     */
    private void inicializarCatalogoPredeterminado() {
        // 1. Productos simples
        ProductoSimple leche = new ProductoSimple("Leche Entera 1L", 1, 7.50);
        ProductoSimple pan = new ProductoSimple("Pan de Molde Familiar", 1, 12.00);
        ProductoSimple cafe = new ProductoSimple("Café Frasco 200g", 1, 24.00);
        ProductoSimple mermelada = new ProductoSimple("Mermelada de Fresa 250g", 1, 9.00);
        ProductoSimple arroz = new ProductoSimple("Arroz 1kg", 1, 6.50);
        ProductoSimple aceite = new ProductoSimple("Aceite Vegetal 1L", 1, 13.50);
        ProductoSimple fideos = new ProductoSimple("Fideos Paquete 500g", 1, 5.00);
        ProductoSimple gaseosa = new ProductoSimple("Gaseosa 2L", 1, 11.00);
        ProductoSimple galletas = new ProductoSimple("Galletas Paquete x6", 1, 8.00);

        catalogo.add(leche);
        catalogo.add(pan);
        catalogo.add(cafe);
        catalogo.add(mermelada);
        catalogo.add(arroz);
        catalogo.add(aceite);
        catalogo.add(fideos);
        catalogo.add(gaseosa);
        catalogo.add(galletas);

        // 2. Productos compuestos (Combos / Canastas usando el patrón Composite)
        // Combo Desayuno: 2x Leche, 1x Pan, 1x Café, 1x Mermelada
        ProductoCompuesto comboDesayuno = new ProductoCompuesto("Combo Desayuno Familiar", 1);
        comboDesayuno.agregarProducto(new ProductoSimple("Leche Entera 1L", 2, 7.50));
        comboDesayuno.agregarProducto(new ProductoSimple("Pan de Molde Familiar", 1, 12.00));
        comboDesayuno.agregarProducto(new ProductoSimple("Café Frasco 200g", 1, 24.00));
        comboDesayuno.agregarProducto(new ProductoSimple("Mermelada de Fresa 250g", 1, 9.00));
        catalogo.add(comboDesayuno);

        // Canasta Básica: 2x Arroz, 2x Fideos, 1x Aceite
        ProductoCompuesto canastaBasica = new ProductoCompuesto("Canasta Despensa Básica", 1);
        canastaBasica.agregarProducto(new ProductoSimple("Arroz 1kg", 2, 6.50));
        canastaBasica.agregarProducto(new ProductoSimple("Fideos Paquete 500g", 2, 5.00));
        canastaBasica.agregarProducto(new ProductoSimple("Aceite Vegetal 1L", 1, 13.50));
        catalogo.add(canastaBasica);

        // Combo Fiesta: 2x Gaseosa, 3x Galletas
        ProductoCompuesto comboFiesta = new ProductoCompuesto("Combo Fiesta Snack", 1);
        comboFiesta.agregarProducto(new ProductoSimple("Gaseosa 2L", 2, 11.00));
        comboFiesta.agregarProducto(new ProductoSimple("Galletas Paquete x6", 3, 8.00));
        catalogo.add(comboFiesta);
    }

    /**
     * Inicia una nueva venta con los datos del cliente y comprobante.
     */
    public void iniciarVenta(String nombreCliente, String fecha, String tipoDocumento, int numeroDocumento) {
        this.ventaActual = new Venta(nombreCliente, fecha, tipoDocumento, numeroDocumento);
        System.out.println("\n[Fachada] Venta iniciada exitosamente para el cliente: " + nombreCliente);
    }

    public boolean hayVentaActiva() {
        return this.ventaActual != null;
    }

    public Venta getVentaActual() {
        return this.ventaActual;
    }

    /**
     * Muestra el catálogo de productos simples y compuestos disponibles.
     */
    public void mostrarCatalogo() {
        System.out.println("\n================================================================================");
        System.out.println("                   CATÁLOGO DE PRODUCTOS - MINISUPERMERCADO                    ");
        System.out.println("================================================================================");
        System.out.printf(" %-4s | %-12s | %-33s | %-12s%n", "N°", "Tipo", "Descripción", "P. Unitario");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < catalogo.size(); i++) {
            IDetalleVenta item = catalogo.get(i);
            String tipo = (item instanceof ProductoCompuesto) ? "COMPUESTO" : "SIMPLE";
            System.out.printf(" [%2d] | %-12s | %-33s | $%10.2f%n",
                    (i + 1),
                    tipo,
                    recortarTexto(item.getDescripcion(), 33),
                    item.getPrecio());

            if (item instanceof ProductoCompuesto) {
                ProductoCompuesto comp = (ProductoCompuesto) item;
                for (IDetalleVenta subItem : comp.getProductos()) {
                    System.out.printf("      |              |   * %dx %-20s ($%.2f c/u)%n",
                            subItem.getCantidad(),
                            recortarTexto(subItem.getDescripcion(), 20),
                            subItem.getPrecio());
                }
            }
        }
        System.out.println("================================================================================");
    }

    /**
     * Agrega un producto del catálogo a la venta actual con la cantidad indicada.
     */
    public boolean agregarProductoAVenta(int numeroProducto, int cantidad) {
        if (!hayVentaActiva()) {
            System.out.println("[Error] No hay ninguna venta activa. Inicie una venta primero.");
            return false;
        }
        if (numeroProducto < 1 || numeroProducto > catalogo.size()) {
            System.out.println("[Error] El número de producto seleccionado no existe en el catálogo.");
            return false;
        }
        if (cantidad <= 0) {
            System.out.println("[Error] La cantidad debe ser mayor a cero.");
            return false;
        }

        IDetalleVenta seleccionado = catalogo.get(numeroProducto - 1);
        if (seleccionado instanceof ProductoSimple) {
            ProductoSimple ps = (ProductoSimple) seleccionado;
            ventaActual.agregarDetalle(new ProductoSimple(ps.getDescripcion(), cantidad, ps.getPrecio()));
        } else if (seleccionado instanceof ProductoCompuesto) {
            ProductoCompuesto pc = (ProductoCompuesto) seleccionado;
            ProductoCompuesto nuevoCompuesto = new ProductoCompuesto(pc.getDescripcion(), cantidad);
            for (IDetalleVenta subItem : pc.getProductos()) {
                nuevoCompuesto.agregarProducto(subItem);
            }
            ventaActual.agregarDetalle(nuevoCompuesto);
        }

        System.out.printf("[Éxito] Se agregaron %d unidad(es) de '%s' a la venta actual.%n",
                cantidad, seleccionado.getDescripcion());
        return true;
    }

    /**
     * Permite registrar un nuevo producto compuesto (combo) personalizado en el catálogo.
     */
    public void registrarNuevoCombo(String nombreCombo, ArrayList<IDetalleVenta> itemsDelCombo) {
        ProductoCompuesto nuevoCombo = new ProductoCompuesto(nombreCombo, 1);
        for (IDetalleVenta item : itemsDelCombo) {
            nuevoCombo.agregarProducto(item);
        }
        catalogo.add(nuevoCombo);
        System.out.println("[Éxito] Nuevo combo '" + nombreCombo + "' registrado en el catálogo con precio total: $" 
                + String.format("%.2f", nuevoCombo.getPrecio()));
    }

    /**
     * Muestra el detalle actual de la venta y el total acumulado.
     */
    public void mostrarDetalleVenta() {
        if (!hayVentaActiva()) {
            System.out.println("[Aviso] No hay ninguna venta activa en este momento.");
            return;
        }
        ventaActual.mostrarDetalle();
    }

    public double obtenerTotalVenta() {
        if (!hayVentaActiva()) return 0.0;
        return ventaActual.getTotal();
    }

    /**
     * Con la opción 'vender', muestra el detalle, el total a cobrar, solicita el tipo de pago
     * mediante PagoFactory y concreta la venta.
     */
    public boolean procesarVenta(int opcionTipoPago, Scanner scanner) {
        if (!hayVentaActiva()) {
            System.out.println("[Error] No hay una venta activa para procesar.");
            return false;
        }
        if (ventaActual.getDetalle().isEmpty()) {
            System.out.println("[Error] La venta actual no tiene ningún producto agregado. Agregue productos antes de vender.");
            return false;
        }

        // 1. Mostrar detalle completo y total a cobrar
        System.out.println("\n--- PROCESANDO VENTA ---");
        ventaActual.mostrarDetalle();

        double total = ventaActual.getTotal();

        // 2. Uso del patrón Factory para crear el método de pago elegido
        MetodoPago metodoPago;
        try {
            metodoPago = PagoFactory.crearPago(opcionTipoPago);
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
            return false;
        }

        System.out.println("\nMétodo de pago seleccionado: " + metodoPago.getNombre());

        // 3. Procesar el pago
        boolean pagoExitoso = metodoPago.procesarPago(total, scanner);

        if (pagoExitoso) {
            System.out.println("\n========================================================");
            System.out.println("           ¡VENTA FINALIZADA EXITOSAMENTE!              ");
            System.out.println(" Gracias por su compra en el Minisupermercado.          ");
            System.out.println("========================================================");
            this.ventaActual = null; // Reiniciar venta completada
            return true;
        } else {
            System.out.println("[Alerta] El pago no pudo completarse. La venta sigue abierta.");
            return false;
        }
    }

    public ArrayList<IDetalleVenta> getCatalogo() {
        return catalogo;
    }

    private String recortarTexto(String texto, int longitudMaxima) {
        if (texto == null) return "";
        if (texto.length() <= longitudMaxima) return texto;
        return texto.substring(0, longitudMaxima - 3) + "...";
    }
}
