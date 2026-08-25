package bo.edu.usfx.biblioteca.legado;

import java.time.LocalDate;

/** Revista: prestamo corto, sin renovacion... o eso dice el reglamento. */
public class Revista extends MaterialBiblioteca {

    public Revista(String signatura, String titulo) {
        super(signatura, titulo);
    }

    @Override
    public LocalDate prestar(LocalDate hoy) {
        return hoy.plusDays(2);
    }

    @Override
    public LocalDate renovar(LocalDate limiteActual) {
        // "Promete menos" que la clase base: devuelve la misma fecha en lugar
        // de una nueva. El cliente cree que renovo y no renovo nada.
        return limiteActual;
    }
}
