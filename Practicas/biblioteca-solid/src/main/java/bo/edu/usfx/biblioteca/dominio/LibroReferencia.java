package bo.edu.usfx.biblioteca.dominio;

/**
 * Obra de referencia: material de consulta en sala.
 * No implementa Prestable ni Renovable; no miente en su contrato ni lanza excepciones (LSP).
 */
public record LibroReferencia(String signatura, String titulo)
        implements Material {
}
