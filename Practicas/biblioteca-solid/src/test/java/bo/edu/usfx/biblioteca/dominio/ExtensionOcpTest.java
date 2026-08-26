package bo.edu.usfx.biblioteca.dominio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Paso 2 (OCP) - Prueba de extension sin modificacion")
class ExtensionOcpTest {

    @Test
    @DisplayName("OCP: se agrega EGRESADO sin tocar el codigo existente")
    void extensionSinModificacion() {
        CatalogoPoliticas catalogo = new CatalogoPoliticas(List.of(
                new PoliticaEstudiante(),
                new PoliticaDocente(),
                new PoliticaAdministrativo(),
                new PoliticaExterno(),
                new PoliticaEgresado()
        ));

        Usuario juan = new Usuario("205001", "Juan", "juan@usfx.bo", "EGRESADO");
        assertThat(catalogo.para(juan).diasPermitidos()).isEqualTo(5);
        assertThat(catalogo.para(juan).maximoEjemplares()).isEqualTo(2);
        assertThat(catalogo.para(juan).multa(4)).isEqualByComparingTo("12.0");
    }
}
