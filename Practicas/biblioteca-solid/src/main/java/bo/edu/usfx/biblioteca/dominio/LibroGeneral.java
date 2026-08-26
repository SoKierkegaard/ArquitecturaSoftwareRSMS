package bo.edu.usfx.biblioteca.dominio;

import java.time.LocalDate;

/**
 * Libro general: prestable (7 dias) y renovable (7 dias adicionales).
 */
public record LibroGeneral(String signatura, String titulo)
        implements Material, Prestable, Renovable {

    @Override
    public LocalDate prestar(LocalDate hoy) {
        return hoy.plusDays(7);
    }

    @Override
    public LocalDate renovar(LocalDate limiteActual) {
        return limiteActual.plusDays(7);
    }
}
