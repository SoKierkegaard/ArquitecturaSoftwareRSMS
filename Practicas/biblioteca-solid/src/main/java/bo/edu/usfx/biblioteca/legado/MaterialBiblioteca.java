package bo.edu.usfx.biblioteca.legado;

import java.time.LocalDate;

/**
 * Jerarquia de materiales del acervo. Usada en el PASO 3 (LSP).
 *
 * El contrato de la clase base promete: "todo material se presta y devuelve
 * una fecha limite". Alguna subclase de abajo NO puede cumplir esa promesa.
 */
public abstract class MaterialBiblioteca {

    protected final String signatura;
    protected final String titulo;

    protected MaterialBiblioteca(String signatura, String titulo) {
        this.signatura = signatura;
        this.titulo = titulo;
    }

    /** Contrato: devuelve SIEMPRE la fecha limite de devolucion. */
    public abstract LocalDate prestar(LocalDate hoy);

    /** Contrato: extiende el prestamo y devuelve la nueva fecha limite. */
    public abstract LocalDate renovar(LocalDate limiteActual);

    public String getSignatura() { return signatura; }
    public String getTitulo()    { return titulo; }
}
