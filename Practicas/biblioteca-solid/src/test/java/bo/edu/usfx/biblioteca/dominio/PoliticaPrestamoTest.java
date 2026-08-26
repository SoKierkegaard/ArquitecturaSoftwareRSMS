package bo.edu.usfx.biblioteca.dominio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Paso 2 (OCP) - Pruebas de PoliticaPrestamo")
class PoliticaPrestamoTest {

    @ParameterizedTest(name = "{0} con {1} dias de retraso paga Bs {2}")
    @CsvSource({
            "ESTUDIANTE,     5, 10.0",
            "DOCENTE,        5,  5.0",
            "ADMINISTRATIVO, 5,  7.5",
            "EXTERNO,        5, 25.0",
            "ESTUDIANTE,     0,  0.0",
            "EXTERNO,      100, 200.0"
    })
    void tarifasYMultas(String tipo, long diasRetraso, double esperado) {
        CatalogoPoliticas catalogo = new CatalogoPoliticas();
        Usuario usuario = new Usuario("123", "Test", "test@usfx.bo", tipo);

        BigDecimal multa = catalogo.para(usuario).multa(diasRetraso);

        assertThat(multa.doubleValue()).isEqualTo(esperado);
    }

    @Test
    @DisplayName("Plazos y limites correctos por politica")
    void plazosYLimites() {
        CatalogoPoliticas catalogo = new CatalogoPoliticas();

        Usuario estudiante = new Usuario("1", "Ana", "a@u.bo", "ESTUDIANTE");
        assertThat(catalogo.para(estudiante).diasPermitidos()).isEqualTo(7);
        assertThat(catalogo.para(estudiante).maximoEjemplares()).isEqualTo(3);

        Usuario docente = new Usuario("2", "Carlos", "c@u.bo", "DOCENTE");
        assertThat(catalogo.para(docente).diasPermitidos()).isEqualTo(15);
        assertThat(catalogo.para(docente).maximoEjemplares()).isEqualTo(5);
    }
}
