package bo.edu.usfx.biblioteca.presentacion;

import bo.edu.usfx.biblioteca.dominio.Prestamo;

/**
 * Formateo del comprobante impreso de prestamos (Responsable ante Kardex / Mesa de entrada).
 */
public class ComprobantePrestamo {

    public static String formatear(Prestamo prestamo) {
        return "=== BIBLIOTECA USFX ===\n"
             + "Usuario : " + prestamo.getUsuario().getNombre() + " (" + prestamo.getUsuario().getCodigo() + ")\n"
             + "Titulo  : " + prestamo.getLibro().getTitulo() + "\n"
             + "Entrega : " + prestamo.getFechaLimite() + "\n"
             + "=======================";
    }
}
