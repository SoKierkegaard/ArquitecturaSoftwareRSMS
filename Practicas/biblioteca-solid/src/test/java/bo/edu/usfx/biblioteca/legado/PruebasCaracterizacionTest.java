package bo.edu.usfx.biblioteca.legado;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * PRUEBAS DE CARACTERIZACION (Michael Feathers).
 *
 * No juzgan si el comportamiento es correcto: CONGELAN el comportamiento
 * actual para que la refactorizacion no lo altere sin que nos demos cuenta.
 *
 * REGLA DE LA PRACTICA: estas pruebas deben seguir en VERDE despues de
 * cada uno de los cinco pasos. Si una se pone roja, no refactorizaste:
 * cambiaste el programa.
 */
@DisplayName("Caracterizacion del modulo de prestamos legado")
class PruebasCaracterizacionTest {

    private final LocalDate HOY = LocalDate.of(2026, 8, 25);

    private Usuario estudiante() { return new Usuario("218123", "Ana Quispe", "ana@usfx.bo", "ESTUDIANTE"); }
    private Libro libro()        { return new Libro("005.1 M379c", "Clean Architecture", "Robert C. Martin"); }

    @Test
    @DisplayName("un estudiante recibe 7 dias de plazo")
    void plazoDelEstudiante() {
        GestorBiblioteca gestor = new GestorBiblioteca();
        gestor.registrarPrestamo(estudiante(), libro(), HOY);

        assertThat(gestor.getPrestamos().get(0).getFechaLimite())
                .isEqualTo(LocalDate.of(2026, 9, 1));
    }

    @Test
    @DisplayName("el comprobante conserva su formato exacto")
    void formatoDelComprobante() {
        GestorBiblioteca gestor = new GestorBiblioteca();

        String comprobante = gestor.registrarPrestamo(estudiante(), libro(), HOY);

        assertThat(comprobante).isEqualTo(
                  "=== BIBLIOTECA USFX ===\n"
                + "Usuario : Ana Quispe (218123)\n"
                + "Titulo  : Clean Architecture\n"
                + "Entrega : 2026-09-01\n"
                + "=======================");
    }

    @Test
    @DisplayName("el estudiante no puede tener mas de 3 ejemplares activos")
    void limiteDeEjemplares() {
        GestorBiblioteca gestor = new GestorBiblioteca();
        Usuario ana = estudiante();
        for (int i = 1; i <= 3; i++) {
            gestor.registrarPrestamo(ana, new Libro("SIG-" + i, "Titulo " + i, "Autor"), HOY);
        }

        assertThatThrownBy(() -> gestor.registrarPrestamo(ana, new Libro("SIG-4", "Cuarto", "Autor"), HOY))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("limite de 3");
    }

    @ParameterizedTest(name = "{0} con {1} dias de retraso paga Bs {2}")
    @DisplayName("tarifa de multa por tipo de usuario")
    @CsvSource({
            "ESTUDIANTE,     5, 10.0",
            "DOCENTE,        5,  5.0",
            "ADMINISTRATIVO, 5,  7.5",
            "EXTERNO,        5, 25.0",
            "ESTUDIANTE,     0,  0.0",
            "EXTERNO,      100, 200.0"   // tope de 200 Bs
    })
    void tarifaDeMulta(String tipo, int diasRetraso, double esperado) {
        GestorBiblioteca gestor = new GestorBiblioteca();
        Usuario usuario = new Usuario("999", "Prueba", "p@usfx.bo", tipo);
        Prestamo prestamo = new Prestamo(usuario, libro(), HOY, HOY.plusDays(7));

        double multa = gestor.calcularMulta(prestamo, HOY.plusDays(7 + diasRetraso));

        assertThat(multa).isEqualTo(esperado);
    }

    @Test
    @DisplayName("la devolucion libera el ejemplar y reporta la multa")
    void devolucion() {
        GestorBiblioteca gestor = new GestorBiblioteca();
        Libro ejemplar = libro();
        gestor.registrarPrestamo(estudiante(), ejemplar, HOY);
        Prestamo prestamo = gestor.getPrestamos().get(0);

        String recibo = gestor.registrarDevolucion(prestamo, HOY.plusDays(12));

        assertThat(recibo).isEqualTo("Devolucion registrada. Multa: Bs 10.0");
        assertThat(ejemplar.isDisponible()).isTrue();
    }

    @Test
    @DisplayName("el reporte mensual mantiene su cabecera CSV")
    void cabeceraDelReporte() {
        GestorBiblioteca gestor = new GestorBiblioteca();
        gestor.registrarPrestamo(estudiante(), libro(), HOY);

        assertThat(gestor.generarReporteMensual(8, 2026))
                .startsWith("codigo;titulo;fecha;limite;multa\n")
                .contains("218123;Clean Architecture;2026-08-25;2026-09-01");
    }
}
