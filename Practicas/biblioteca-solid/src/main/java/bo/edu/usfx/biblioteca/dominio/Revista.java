package bo.edu.usfx.biblioteca.dominio;

import java.time.LocalDate;

/**
 * Revista: prestable por 2 dias, pero NO renovable.
 */
public record Revista(String signatura, String titulo)
        implements Material, Prestable {

    @Override
    public LocalDate prestar(LocalDate hoy) {
        return hoy.plusDays(2);
    }
}
