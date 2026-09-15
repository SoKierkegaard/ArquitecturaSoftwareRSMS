package com.composite;

import com.composite.pago.PagoFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SupermercadoFacade facade = new SupermercadoFacade();

        System.out.println("================================================================");
        System.out.println("  SISTEMA DE VENTAS - MINISUPERMERCADO");
        System.out.println("  Patrones de Diseño: Composite, Factory y Facade");
        System.out.println("================================================================");

        boolean salir = false;

        while (!salir) {
            System.out.println("\n----------------- MENÚ PRINCIPAL -----------------");
            System.out.println("1. Iniciar nueva venta");
            System.out.println("2. Ver catálogo de productos (Simples y Compuestos)");
            System.out.println("3. Seleccionar producto y agregar a la venta");
            System.out.println("4. Crear nuevo combo personalizado (Patrón Composite)");
            System.out.println("5. Ver detalle de venta actual y total acumulado");
            System.out.println("6. VENDER (Mostrar detalle, total y pagar)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            String opcionStr = scanner.nextLine().trim();
            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                System.out.println("[Error] Ingrese un número válido entre 1 y 7.");
                continue;
            }

            switch (opcion) {
                case 1:
                    iniciarVenta(facade, scanner);
                    break;

                case 2:
                    facade.mostrarCatalogo();
                    break;

                case 3:
                    if (!facade.hayVentaActiva()) {
                        System.out.println("[Aviso] Primero debe iniciar una venta con los datos del cliente.");
                        System.out.print("¿Desea iniciar una venta ahora? (s/n): ");
                        String resp = scanner.nextLine().trim().toLowerCase();
                        if (resp.equals("s") || resp.equals("si") || resp.equals("sí")) {
                            iniciarVenta(facade, scanner);
                        } else {
                            break;
                        }
                    }
                    agregarProducto(facade, scanner);
                    break;

                case 4:
                    crearComboPersonalizado(facade, scanner);
                    break;

                case 5:
                    if (!facade.hayVentaActiva()) {
                        System.out.println("[Aviso] No hay ninguna venta activa en este momento.");
                    } else {
                        facade.mostrarDetalleVenta();
                    }
                    break;

                case 6:
                    if (!facade.hayVentaActiva()) {
                        System.out.println("[Error] No hay ninguna venta activa para vender.");
                        break;
                    }
                    if (facade.getVentaActual().getDetalle().isEmpty()) {
                        System.out.println("[Error] La venta está vacía. Agregue productos antes de cobrar.");
                        break;
                    }
                    realizarVenta(facade, scanner);
                    break;

                case 7:
                    salir = true;
                    System.out.println("¡Gracias por utilizar el sistema del Minisupermercado! Hasta pronto.");
                    break;

                default:
                    System.out.println("[Error] Opción no válida. Intente nuevamente.");
                    break;
            }
        }

        scanner.close();
    }

    private static void iniciarVenta(SupermercadoFacade facade, Scanner scanner) {
        System.out.println("\n--- REGISTRO DE DATOS DE LA VENTA ---");
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine().trim();
        while (nombre.isEmpty()) {
            System.out.print("El nombre no puede estar vacío. Ingrese nombre: ");
            nombre = scanner.nextLine().trim();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaSugerida = sdf.format(new Date());
        System.out.print("Fecha de la venta [" + fechaSugerida + "] (Presione Enter para usar sugerida): ");
        String fecha = scanner.nextLine().trim();
        if (fecha.isEmpty()) {
            fecha = fechaSugerida;
        }

        System.out.print("Tipo de documento (ej. CI, NIT, Pasaporte) [CI]: ");
        String tipoDoc = scanner.nextLine().trim();
        if (tipoDoc.isEmpty()) {
            tipoDoc = "CI";
        }

        System.out.print("Número de documento: ");
        int numDoc = 0;
        while (true) {
            try {
                String numDocStr = scanner.nextLine().trim();
                numDoc = Integer.parseInt(numDocStr);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Ingrese un número de documento numérico: ");
            }
        }

        facade.iniciarVenta(nombre, fecha, tipoDoc, numDoc);
    }

    private static void agregarProducto(SupermercadoFacade facade, Scanner scanner) {
        facade.mostrarCatalogo();
        System.out.print("Seleccione el N° de producto (simple o compuesto/combo) a comprar: ");
        int numProd;
        try {
            numProd = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Error] Debe ingresar un número de producto válido.");
            return;
        }

        System.out.print("Indique la cantidad a llevar: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Error] Debe ingresar una cantidad entera válida.");
            return;
        }

        facade.agregarProductoAVenta(numProd, cantidad);
    }

    private static void crearComboPersonalizado(SupermercadoFacade facade, Scanner scanner) {
        System.out.println("\n--- CREACIÓN DE COMBO PERSONALIZADO (COMPOSITE) ---");
        System.out.print("Nombre o descripción del nuevo combo: ");
        String nombreCombo = scanner.nextLine().trim();
        if (nombreCombo.isEmpty()) {
            nombreCombo = "Combo Especial";
        }

        ArrayList<IDetalleVenta> itemsParaCombo = new ArrayList<>();
        boolean agregando = true;

        while (agregando) {
            facade.mostrarCatalogo();
            System.out.print("Seleccione el N° de producto del catálogo para incluir en el combo (0 para terminar): ");
            int opcionProd;
            try {
                opcionProd = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Número no válido.");
                continue;
            }

            if (opcionProd == 0) {
                if (itemsParaCombo.isEmpty()) {
                    System.out.println("[Aviso] Debe agregar al menos un producto para crear el combo.");
                    continue;
                }
                agregando = false;
                break;
            }

            if (opcionProd < 1 || opcionProd > facade.getCatalogo().size()) {
                System.out.println("[Error] Número fuera de rango.");
                continue;
            }

            System.out.print("¿Cuántas unidades de este producto incluye el combo?: ");
            int cant;
            try {
                cant = Integer.parseInt(scanner.nextLine().trim());
                if (cant <= 0) {
                    System.out.println("[Error] La cantidad debe ser mayor a 0.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Cantidad inválida.");
                continue;
            }

            IDetalleVenta base = facade.getCatalogo().get(opcionProd - 1);
            itemsParaCombo.add(base.clonarConCantidad(cant));
            System.out.printf("Añadido al combo: %dx '%s'%n", cant, base.getDescripcion());

            System.out.print("¿Desea añadir otro producto a este combo? (s/n): ");
            String continuar = scanner.nextLine().trim().toLowerCase();
            if (!continuar.equals("s") && !continuar.equals("si") && !continuar.equals("sí")) {
                agregando = false;
            }
        }

        facade.registrarNuevoCombo(nombreCombo, itemsParaCombo);
    }

    private static void realizarVenta(SupermercadoFacade facade, Scanner scanner) {
        System.out.println("\n========================================================");
        System.out.println("                  FINALIZAR VENTA                       ");
        System.out.println("========================================================");
        
        // Muestra el detalle de la venta y total a cobrar
        facade.mostrarDetalleVenta();

        System.out.println("\nSeleccione el método de pago:");
        PagoFactory.mostrarOpcionesPago();
        System.out.print("Elija una opción (1, 2 o 3): ");

        int opcionPago;
        try {
            opcionPago = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Error] Opción de pago inválida.");
            return;
        }

        facade.procesarVenta(opcionPago, scanner);
    }
}