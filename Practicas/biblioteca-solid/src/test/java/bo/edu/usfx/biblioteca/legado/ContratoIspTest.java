package bo.edu.usfx.biblioteca.legado;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Evidencia medible de la interfaz gorda (paso 4 - ISP). */
@DisplayName("Contrato de OperacionesBiblioteca (paso 4 - ISP)")
class ContratoIspTest {

    @Test
    @DisplayName("la interfaz declara 7 operaciones y nadie las usa todas")
    void interfazGorda() {
        assertThat(OperacionesBiblioteca.class.getDeclaredMethods()).hasSize(7);
    }

    @Test
    @DisplayName("el ejemplar fisico rechaza 2 de las 7 operaciones")
    void ejemplarFisicoRechaza() {
        EjemplarFisico fisico = new EjemplarFisico("005.1 M379c");

        assertThatThrownBy(fisico::descargarPdf).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> fisico.enviarPorCorreo("ana@usfx.bo"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("el ejemplar digital rechaza 3 de las 7 operaciones")
    void ejemplarDigitalRechaza() {
        EjemplarDigital digital = new EjemplarDigital("EB-77");

        assertThatThrownBy(() -> digital.renovar("218123")).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> digital.reservar("218123")).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(digital::enviarARestauracion).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("metrica: porcentaje de metodos no soportados por implementador")
    void metricaDeSegregacion() {
        long total = Arrays.stream(OperacionesBiblioteca.class.getDeclaredMethods())
                .map(Method::getName).count();

        assertThat(total).isEqualTo(7);
        // Fisico: 2/7 = 28,6 %   Digital: 3/7 = 42,9 %   Ninguno usa la interfaz entera.
    }
}
