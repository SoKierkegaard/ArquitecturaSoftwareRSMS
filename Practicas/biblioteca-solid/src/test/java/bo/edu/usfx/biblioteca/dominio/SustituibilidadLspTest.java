package bo.edu.usfx.biblioteca.dominio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Paso 3 (LSP) - Sustituibilidad de la jerarquia de materiales")
class SustituibilidadLspTest {

    private final LocalDate HOY = LocalDate.of(2026, 8, 25);

    static Stream<Prestable> materialesPrestables() {
        return Stream.of(
                new LibroGeneral("005.1 M379c", "Clean Architecture"),
                new Revista("REV-12", "IEEE Software")
        );
    }

    @ParameterizedTest
    @MethodSource("materialesPrestables")
    @DisplayName("LSP: todo subtipo de Prestable cumple su contrato de prestamo sin excepciones")
    void todosLosPrestablesCumplenContrato(Prestable material) {
        LocalDate limite = material.prestar(HOY);
        assertThat(limite).isAfter(HOY);
    }

    @Test
    @DisplayName("LSP: libro general renueva correctamente su plazo")
    void renovacionCumpleContrato() {
        Renovable libro = new LibroGeneral("005.1", "Clean Architecture");
        LocalDate limiteActual = HOY.plusDays(7);

        LocalDate nuevoLimite = libro.renovar(limiteActual);

        assertThat(nuevoLimite).isEqualTo(limiteActual.plusDays(7));
    }

    @Test
    @DisplayName("LSP: el Catalogo atiende materiales prestables sin codigo defensivo")
    void catalogoPrestaSinExcepcionesDefensivas() {
        Catalogo catalogo = new Catalogo();
        catalogo.agregar(new LibroGeneral("005.1 M379c", "Clean Architecture"));
        catalogo.agregar(new Revista("REV-12", "IEEE Software"));
        catalogo.agregar(new LibroReferencia("R-030", "Enciclopedia Britanica"));

        List<String> comprobantes = catalogo.prestarTodo(HOY);

        assertThat(comprobantes).containsExactly(
                "Clean Architecture -> 2026-09-01",
                "IEEE Software -> 2026-08-27"
        );
    }

    @Test
    @DisplayName("LSP: Pattern matching exhaustivo sobre Material")
    void descripcionMateriales() {
        Catalogo catalogo = new Catalogo();
        Material libro = new LibroGeneral("005.1", "Clean Architecture");
        Material revista = new Revista("REV-1", "ACM Communications");
        Material ref = new LibroReferencia("REF-1", "Atlas Universal");

        assertThat(catalogo.describir(libro)).contains("préstamo 7 días, renovable");
        assertThat(catalogo.describir(revista)).contains("préstamo 2 días");
        assertThat(catalogo.describir(ref)).contains("solo consulta en sala");
    }
}
