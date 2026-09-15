package com.composite.pago;

import java.util.Scanner;

public class PagoTransferenciaBancaria implements MetodoPago {

    @Override
    public String getNombre() {
        return "Transferencia Bancaria";
    }

    @Override
    public boolean procesarPago(double monto, Scanner scanner) {
        System.out.println("\n--- PAGO POR TRANSFERENCIA BANCARIA ---");
        System.out.printf("Total a transferir: $%.2f%n", monto);
        System.out.println("Cuentas habilitadas: BCP (123-456789), BBVA (987-654321), Banco Unión (456-789123)");
        
        System.out.print("Ingrese el banco de origen: ");
        String banco = scanner.nextLine().trim();
        if (banco.isEmpty()) banco = "Banco Local";

        System.out.print("Ingrese el número de comprobante/referencia de la transferencia: ");
        String comprobante = scanner.nextLine().trim();
        while (comprobante.isEmpty()) {
            System.out.print("El comprobante no puede estar vacío. Ingrese referencia: ");
            comprobante = scanner.nextLine().trim();
        }

        System.out.println("Verificando transferencia en el sistema bancario...");
        System.out.printf("¡Transferencia validada con éxito! Banco: %s | Referencia: %s%n", banco, comprobante);
        System.out.printf("Se ha acreditado el pago de $%.2f a la cuenta del minisupermercado.%n", monto);
        return true;
    }
}
