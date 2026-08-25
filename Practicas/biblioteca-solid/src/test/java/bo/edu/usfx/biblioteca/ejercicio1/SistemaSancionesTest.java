package bo.edu.usfx.biblioteca.ejercicio1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EJERCICIO 1 - pruebas de caracterizacion.
 * Deben seguir en verde despues de tu refactorizacion.
 * Puedes AGREGAR pruebas nuevas; no puedes borrar ni relajar estas.
 */
@DisplayName("Caracterizacion del modulo de sanciones")
class SistemaSancionesTest {

    private final LocalDate HOY = LocalDate.of(2026, 8, 25);
    private final SistemaSanciones sistema = new SistemaSanciones();

    @ParameterizedTest(name = "{0} => {2} dias de suspension")
    @CsvSource({
            "RETRASO,              10,  20",
            "DANNO_LEVE,            0,  15",
            "DANNO_GRAVE,           0,  30",
            "PERDIDA,               0,  90",
            "PRESTAMO_A_TERCEROS,   0,  60",
            "RETRASO,             200, 180"    // tope de 180 dias
    })
    @DisplayName("dias de suspension por tipo de infraccion")
    void suspensionPorInfraccion(String tipo, int diasRetraso, int esperado) {
        assertThat(sistema.calcularSuspension(tipo, diasRetraso, 300.0)).isEqualTo(esperado);
    }

    @ParameterizedTest(name = "{0} sobre un ejemplar de Bs 300 => Bs {1}")
    @CsvSource({
            "PERDIDA,     450.0",
            "DANNO_GRAVE, 240.0",
            "DANNO_LEVE,   50.0",
            "RETRASO,       0.0"
    })
    @DisplayName("monto de reposicion por tipo de infraccion")
    void reposicionPorInfraccion(String tipo, double esperado) {
        assertThat(sistema.calcularReposicion(tipo, 300.0)).isEqualTo(esperado);
    }

    @Test
    @DisplayName("el acta de sancion conserva su formato")
    void formatoDelActa() {
        String acta = sistema.aplicarSancion("218123", "70012345", "DANNO_LEVE", 0, 300.0, HOY);

        assertThat(acta).isEqualTo(
                  "ACTA DE SANCION\n"
                + "Usuario     : 218123\n"
                + "Infraccion  : DANNO_LEVE\n"
                + "Suspension  : 15 dias\n"
                + "Reposicion  : Bs 50.0\n"
                + "Habilitado  : 2026-09-09");
    }

    @Test
    @DisplayName("la solvencia se emite en HTML")
    void solvenciaEnHtml() {
        assertThat(sistema.emitirSolvencia("218123", "Ana Quispe", HOY))
                .contains("<h1>SOLVENCIA BIBLIOTECARIA</h1>")
                .contains("Ana Quispe (218123)");
    }
}
