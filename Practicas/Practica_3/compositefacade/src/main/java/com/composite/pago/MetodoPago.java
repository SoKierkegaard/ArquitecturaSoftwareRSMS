package com.composite.pago;

import java.util.Scanner;

public interface MetodoPago {
    String getNombre();
    boolean procesarPago(double monto, Scanner scanner);
}
