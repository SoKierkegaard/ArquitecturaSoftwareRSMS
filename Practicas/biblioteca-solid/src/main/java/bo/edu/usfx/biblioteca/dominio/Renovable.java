package bo.edu.usfx.biblioteca.dominio;

import java.time.LocalDate;

/**
 * Rol para materiales cuyo prestamo puede ser renovado (LSP / ISP).
 */
public interface Renovable {

    LocalDate renovar(LocalDate limiteActual);
}
