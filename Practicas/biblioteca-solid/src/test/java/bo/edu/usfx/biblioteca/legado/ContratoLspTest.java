package bo.edu.usfx.biblioteca.legado;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Esta clase DOCUMENTA la violacion de LSP del paso 3.
 *
 * Las dos primeras pruebas pasan hoy porque congelan el comportamiento roto.
 * Despues de refactorizar la jerarquia, tendras que reescribirlas: ese cambio
 * es parte de la evidencia que se entrega.
 */
@DisplayName("Contrato de la jerarquia MaterialBiblioteca (paso 3 - LSP)")
class ContratoLspTest {

    private final LocalDate HOY = LocalDate.of(2026, 8, 25);

    @Test
    @DisplayName("un subtipo rompe el contrato de prestar()")
    void subtipoRompeElContrato() {
        MaterialBiblioteca material = new LibroReferencia("R-030", "Enciclopedia Britanica");

        assertThatThrownBy(() -> material.prestar(HOY))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("otro subtipo promete menos: renovar() no renueva nada")
    void subtipoPrometeMenos() {
        MaterialBiblioteca revista = new Revista("REV-12", "IEEE Software");

        LocalDate limite = revista.prestar(HOY);

        assertThat(revista.renovar(limite)).isEqualTo(limite);   // no avanzo ni un dia
    }

    @Test
    @DisplayName("el cliente se defiende con instanceof y try/catch")
    void elClienteSeLlenaDeComprobaciones() {
        CatalogoBiblioteca catalogo = new CatalogoBiblioteca();
        catalogo.agregar(new LibroGeneral("005.1 M379c", "Clean Architecture"));
        catalogo.agregar(new Revista("REV-12", "IEEE Software"));
        catalogo.agregar(new LibroReferencia("R-030", "Enciclopedia Britanica"));

        List<String> comprobantes = catalogo.prestarTodo(HOY);

        assertThat(comprobantes).hasSize(2);   // el de referencia se cayo por el camino
    }
}
