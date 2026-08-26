package bo.edu.usfx.biblioteca.dominio;

import java.time.LocalDate;

/**
 * Rol para materiales que salen de la biblioteca en prestamo (LSP / ISP).
 */
public interface Prestable {

    LocalDate prestar(LocalDate hoy);
}
