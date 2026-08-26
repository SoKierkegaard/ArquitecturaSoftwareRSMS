package bo.edu.usfx.biblioteca.dominio.roles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Paso 4 (ISP) - Segregacion de interfaces por rol")
class SegregacionInterfacesIspTest {

    @Test
    @DisplayName("ISP: EjemplarFisico soporta el 100% de sus interfaces sin excepciones (0% rechazados)")
    void ejemplarFisicoSoportaTodasSusOperaciones() {
        EjemplarFisico fisico = new EjemplarFisico("005.1 M379c");

        assertThatCode(() -> {
            fisico.prestar("218123");
            fisico.devolver("218123");
            fisico.renovar("218123");
            fisico.reservar("218123");
            fisico.enviarARestauracion();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ISP: EjemplarDigital soporta el 100% de sus interfaces sin excepciones (0% rechazados)")
    void ejemplarDigitalSoportaTodasSusOperaciones() {
        EjemplarDigital digital = new EjemplarDigital("EB-77");

        assertThatCode(() -> {
            digital.prestar("218123");
            digital.devolver("218123");
            digital.enviarPorCorreo("ana@usfx.bo");
        }).doesNotThrowAnyException();

        assertThat(digital.descargarPdf()).isNotEmpty();
    }

    @Test
    @DisplayName("ISP: Clientes usan interfaces por rol sin depender de metodos innecesarios")
    void clientesUsanInterfacesSegregadas() {
        MostradorPrestamos mostrador = new MostradorPrestamos();
        TallerRestauracion taller = new TallerRestauracion();

        EjemplarFisico libro = new EjemplarFisico("005.1");
        EjemplarDigital ebook = new EjemplarDigital("EB-1");

        assertThatCode(() -> {
            mostrador.atender(libro, "218123");
            mostrador.atender(ebook, "218123");
            taller.recibir(libro);
        }).doesNotThrowAnyException();
    }
}
