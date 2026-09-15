package com.composite;

import com.composite.pago.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class SistemaVentasTest {

    public static void main(String[] args) {
        System.out.println("========== INICIANDO PRUEBAS UNITARIAS Y DE INTEGRACIÓN ==========");
        testProductoSimple();
        testProductoCompuestoComposite();
        testVentaTotales();
        testPagoFactory();
        testSupermercadoFacadeFlujoCompleto();
        System.out.println("\n>>> TODAS LAS PRUEBAS PASARON EXITOSAMENTE (5/5) <<<");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Fallo en prueba: " + message);
        }
        System.out.println(" [OK] " + message);
    }

    private static void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError("Fallo en prueba: " + message + " - Esperado: " + expected + ", Obtenido: " + actual);
        }
        System.out.println(" [OK] " + message + " (Valor: " + actual + ")");
    }

    private static void testProductoSimple() {
        System.out.println("\n--- 1. Prueba: Patrón Composite (Hoja / ProductoSimple) ---");
        ProductoSimple leche = new ProductoSimple("Leche 1L", 3, 7.50);
        assertEquals(7.50, leche.getPrecio(), 0.001, "Precio unitario de producto simple");
        assertEquals(3, leche.getCantidad(), 0.001, "Cantidad de producto simple");
        assertTrue(leche.getDescripcion().equals("Leche 1L"), "Descripción producto simple");

        IDetalleVenta clon = leche.clonarConCantidad(5);
        assertEquals(5, clon.getCantidad(), 0.001, "Clonación con nueva cantidad");
        assertEquals(7.50, clon.getPrecio(), 0.001, "Precio unitario preservado en clon");
    }

    private static void testProductoCompuestoComposite() {
        System.out.println("\n--- 2. Prueba: Patrón Composite (Compuesto / ProductoCompuesto) ---");
        // Crear combo con productos simples
        // Combo Desayuno: 2x Leche ($7.50 = $15) + 1x Pan ($12 = $12) -> Total unitario combo = $27.00
        ProductoCompuesto comboDesayuno = new ProductoCompuesto("Combo Desayuno", 1);
        comboDesayuno.agregarProducto(new ProductoSimple("Leche 1L", 2, 7.50));
        comboDesayuno.agregarProducto(new ProductoSimple("Pan", 1, 12.00));

        assertEquals(27.00, comboDesayuno.getPrecio(), 0.001, "Cálculo de precio unitario de combo compuesto");

        // Anidar un combo dentro de otro (Composite recursivo)
        // Super Combo: 1x Combo Desayuno ($27) + 1x Frasco de Miel ($20) -> Total = $47.00
        ProductoCompuesto superCombo = new ProductoCompuesto("Super Combo", 2);
        superCombo.agregarProducto(comboDesayuno);
        superCombo.agregarProducto(new ProductoSimple("Miel", 1, 20.00));

        assertEquals(47.00, superCombo.getPrecio(), 0.001, "Cálculo recursivo de combo anidado");
        assertEquals(2, superCombo.getCantidad(), 0.001, "Cantidad de combos");
    }

    private static void testVentaTotales() {
        System.out.println("\n--- 3. Prueba: Clase Venta y Cálculo de Totales ---");
        Venta venta = new Venta("Carlos Perez", "15/09/2026", "CI", 8877665);
        
        // 2x Aceite a $13.50 = $27.00
        venta.agregarDetalle(new ProductoSimple("Aceite 1L", 2, 13.50));

        // 3x Combo Merienda (1x Galleta $8 + 1x Jugo $5 = $13.00 c/u) -> 3 * $13.00 = $39.00
        ProductoCompuesto combo = new ProductoCompuesto("Combo Merienda", 3);
        combo.agregarProducto(new ProductoSimple("Galleta", 1, 8.00));
        combo.agregarProducto(new ProductoSimple("Jugo", 1, 5.00));
        venta.agregarDetalle(combo);

        // Total esperado: $27.00 + $39.00 = $66.00
        assertEquals(66.00, venta.getTotal(), 0.001, "Total de la venta con productos simples y compuestos");
    }

    private static void testPagoFactory() {
        System.out.println("\n--- 4. Prueba: Patrón Factory (PagoFactory) ---");
        MetodoPago efectivo = PagoFactory.crearPago(PagoFactory.EFECTIVO);
        assertTrue(efectivo instanceof PagoEfectivo, "Creación de PagoEfectivo con constante");
        assertTrue(efectivo.getNombre().equals("Efectivo"), "Nombre del método de pago Efectivo");

        MetodoPago tarjeta = PagoFactory.crearPago("tarjeta");
        assertTrue(tarjeta instanceof PagoTarjetaCredito, "Creación de PagoTarjetaCredito por texto");

        MetodoPago transferencia = PagoFactory.crearPago(PagoFactory.TRANSFERENCIA_BANCARIA);
        assertTrue(transferencia instanceof PagoTransferenciaBancaria, "Creación de PagoTransferenciaBancaria");

        try {
            PagoFactory.crearPago(99);
            throw new AssertionError("Debió lanzar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println(" [OK] Excepción esperada ante tipo de pago inválido");
        }
    }

    private static void testSupermercadoFacadeFlujoCompleto() {
        System.out.println("\n--- 5. Prueba: Patrón Facade (SupermercadoFacade - Flujo Completo) ---");
        SupermercadoFacade facade = new SupermercadoFacade();
        assertTrue(facade.getCatalogo().size() > 0, "Catálogo inicializado con productos");

        // Iniciar venta
        facade.iniciarVenta("Maria Rodriguez", "15/09/2026", "NIT", 10293847);
        assertTrue(facade.hayVentaActiva(), "Venta activa registrada");

        // Agregar 2 unidades del producto 1 (Leche $7.50 c/u = $15.00)
        boolean ag1 = facade.agregarProductoAVenta(1, 2);
        assertTrue(ag1, "Producto simple agregado a la venta");

        // Agregar 1 combo desayuno (producto 10 en catálogo predeterminado, precio $52.00)
        boolean ag2 = facade.agregarProductoAVenta(10, 1);
        assertTrue(ag2, "Producto compuesto agregado a la venta");

        // Total esperado: 2 * 7.50 ($15.00) + 1 * Combo Desayuno ($60.00) = $75.00
        assertEquals(75.00, facade.obtenerTotalVenta(), 0.001, "Total acumulado en Facade");

        // Simular pago en efectivo con $100.00
        String entradaSimulada = "100.00\n";
        Scanner scannerSimulado = new Scanner(new ByteArrayInputStream(entradaSimulada.getBytes()));
        boolean ventaFinalizada = facade.procesarVenta(PagoFactory.EFECTIVO, scannerSimulado);

        assertTrue(ventaFinalizada, "Venta procesada exitosamente a través de Facade");
        assertTrue(!facade.hayVentaActiva(), "Venta cerrada tras cobro exitoso");
    }
}
