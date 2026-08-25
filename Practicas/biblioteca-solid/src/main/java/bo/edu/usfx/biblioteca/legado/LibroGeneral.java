package bo.edu.usfx.biblioteca.legado;

import java.time.LocalDate;

/** Libro de coleccion general: se presta 7 dias y se renueva una vez. */
public class LibroGeneral extends MaterialBiblioteca {

    public LibroGeneral(String signatura, String titulo) {
        super(signatura, titulo);
    }

    @Override
    public LocalDate prestar(LocalDate hoy) {
        return hoy.plusDays(7);
    }

    @Override
    public LocalDate renovar(LocalDate limiteActual) {
        return limiteActual.plusDays(7);
    }
}
