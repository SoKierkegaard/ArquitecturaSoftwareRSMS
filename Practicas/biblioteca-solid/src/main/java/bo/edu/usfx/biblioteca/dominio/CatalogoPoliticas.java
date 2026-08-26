package bo.edu.usfx.biblioteca.dominio;

import java.util.List;

/**
 * Catalogo y selector polimorfico de politicas de prestamo (OCP).
 */
public class CatalogoPoliticas {

    private final List<PoliticaPrestamo> politicas;

    public CatalogoPoliticas(List<PoliticaPrestamo> politicas) {
        this.politicas = List.copyOf(politicas);
    }

    public CatalogoPoliticas() {
        this(List.of(
                new PoliticaEstudiante(),
                new PoliticaDocente(),
                new PoliticaAdministrativo(),
                new PoliticaExterno()
        ));
    }

    public PoliticaPrestamo para(Usuario usuario) {
        return politicas.stream()
                .filter(p -> p.aplicaA(usuario))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de usuario desconocido: " + usuario.getTipo()));
    }
}
