package com.composite.pago;

import java.util.Scanner;

public class PagoEfectivo implements MetodoPago {

    @Override
    public String getNombre() {
        return "Efectivo";
    }

    @Override
    public boolean procesarPago(double monto, Scanner scanner) {
        System.out.println("\n--- PAGO EN EFECTIVO ---");
        System.out.printf("Total a pagar: $%.2f%n", monto);
        
        while (true) {
            System.out.print("Ingrese el monto entregado por el cliente: $");
            try {
                String input = scanner.nextLine().trim();
                double recibido = Double.parseDouble(input);
                
                if (recibido < monto) {
                    System.out.printf("Monto insuficiente. Falta: $%.2f. Intente nuevamente.%n", (monto - recibido));
                } else {
                    double cambio = recibido - monto;
                    System.out.println("Pago recibido con éxito.");
                    if (cambio > 0) {
                        System.out.printf("Su cambio/vuelto es: $%.2f%n", cambio);
                    } else {
                        System.out.println("Monto exacto entregado. No requiere cambio.");
                    }
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Monto no válido. Por favor, ingrese un número decimal válido.");
            }
        }
    }
}
