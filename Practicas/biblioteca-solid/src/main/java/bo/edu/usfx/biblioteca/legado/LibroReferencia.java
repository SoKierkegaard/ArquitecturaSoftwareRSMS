package bo.edu.usfx.biblioteca.legado;

import java.time.LocalDate;

/**
 * Obra de referencia (diccionarios, enciclopedias, tesis): SOLO consulta
 * en sala. No sale de la biblioteca.
 *
 * VIOLACION CLASICA DE LSP: hereda de MaterialBiblioteca para reutilizar
 * signatura y titulo, pero no puede honrar el contrato de prestar().
 * Cualquier codigo escrito contra MaterialBiblioteca revienta si le llega
 * una instancia de esta clase.
 */
public class LibroReferencia extends MaterialBiblioteca {

    public LibroReferencia(String signatura, String titulo) {
        super(signatura, titulo);
    }

    @Override
    public LocalDate prestar(LocalDate hoy) {
        throw new UnsupportedOperationException(
                "Las obras de referencia no se prestan a domicilio");
    }

    @Override
    public LocalDate renovar(LocalDate limiteActual) {
        throw new UnsupportedOperationException(
                "Las obras de referencia no se renuevan");
    }
}
