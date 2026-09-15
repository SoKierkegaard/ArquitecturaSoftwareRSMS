package com.composite.pago;

import java.util.Scanner;

public class PagoTarjetaCredito implements MetodoPago {

    @Override
    public String getNombre() {
        return "Tarjeta de Crédito";
    }

    @Override
    public boolean procesarPago(double monto, Scanner scanner) {
        System.out.println("\n--- PAGO CON TARJETA DE CRÉDITO ---");
        System.out.printf("Total a cobrar: $%.2f%n", monto);
        
        System.out.print("Ingrese el número de la tarjeta (16 dígitos): ");
        String numeroTarjeta = scanner.nextLine().trim();
        while (numeroTarjeta.replaceAll("\\s+", "").length() < 13) {
            System.out.print("Número de tarjeta inválido. Ingrese nuevamente: ");
            numeroTarjeta = scanner.nextLine().trim();
        }

        System.out.print("Ingrese el titular de la tarjeta: ");
        String titular = scanner.nextLine().trim();

        System.out.print("Ingrese el número de cuotas (1 a 12): ");
        int cuotas = 1;
        try {
            cuotas = Integer.parseInt(scanner.nextLine().trim());
            if (cuotas < 1) cuotas = 1;
        } catch (NumberFormatException e) {
            cuotas = 1;
        }

        String ultimos4 = numeroTarjeta.length() >= 4 ? 
                numeroTarjeta.substring(numeroTarjeta.length() - 4) : "****";

        System.out.println("Conectando con la pasarela de pagos...");
        System.out.printf("¡Transacción aprobada! Cobro de $%.2f realizado a la tarjeta terminada en %s.%n", monto, ultimos4);
        System.out.printf("Titular: %s | Cuotas: %d (Monto por cuota: $%.2f)%n", titular, cuotas, (monto / cuotas));
        return true;
    }
}
