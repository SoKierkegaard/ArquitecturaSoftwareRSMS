package com.composite.pago;

public class PagoFactory {

    public static final int EFECTIVO = 1;
    public static final int TARJETA_CREDITO = 2;
    public static final int TRANSFERENCIA_BANCARIA = 3;

    public static MetodoPago crearPago(int tipoPago) {
        switch (tipoPago) {
            case EFECTIVO:
                return new PagoEfectivo();
            case TARJETA_CREDITO:
                return new PagoTarjetaCredito();
            case TRANSFERENCIA_BANCARIA:
                return new PagoTransferenciaBancaria();
            default:
                throw new IllegalArgumentException("Tipo de pago inválido (" + tipoPago + "). Opciones válidas: 1=Efectivo, 2=Tarjeta de Crédito, 3=Transferencia Bancaria.");
        }
    }

    public static MetodoPago crearPago(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de pago no puede estar vacío.");
        }
        String normalizado = tipo.trim().toLowerCase();
        if (normalizado.contains("efectivo")) {
            return new PagoEfectivo();
        } else if (normalizado.contains("tarjeta") || normalizado.contains("credito") || normalizado.contains("crédito")) {
            return new PagoTarjetaCredito();
        } else if (normalizado.contains("transferencia") || normalizado.contains("bancaria") || normalizado.contains("banco")) {
            return new PagoTransferenciaBancaria();
        } else {
            throw new IllegalArgumentException("Tipo de pago no reconocido: '" + tipo + "'. Opciones: efectivo, tarjeta de crédito, transferencia bancaria.");
        }
    }

    public static void mostrarOpcionesPago() {
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta de Crédito");
        System.out.println("3. Transferencia Bancaria");
    }
}
