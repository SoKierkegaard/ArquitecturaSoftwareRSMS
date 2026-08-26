package bo.edu.usfx.biblioteca.dominio;

/**
 * Contrato base para todo material bibliografico con jerarquia sellada (LSP).
 */
public sealed interface Material permits LibroGeneral, Revista, LibroReferencia {

    String signatura();

    String titulo();
}
