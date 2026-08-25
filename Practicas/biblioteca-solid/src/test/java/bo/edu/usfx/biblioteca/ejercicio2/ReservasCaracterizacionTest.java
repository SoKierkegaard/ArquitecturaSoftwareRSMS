package bo.edu.usfx.biblioteca.ejercicio2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * EJERCICIO 2 - pruebas de caracterizacion.
 *
 * Las pruebas marcadas "VIOLACION" describen el estado actual. Cuando
 * termines la refactorizacion NO deberian poder escribirse: sustituyelas
 * por pruebas que verifiquen la sustituibilidad y adjunta ambas versiones
 * como evidencia.
 */
@DisplayName("Caracterizacion del modulo de reservas")
class ReservasCaracterizacionTest {

    private final LocalDateTime INICIO = LocalDateTime.of(2026, 8, 25, 9, 0);

    @Test
    @DisplayName("la reserva individual honra el contrato base (1..8 horas)")
    void individualHonraElContrato() {
        Reserva reserva = new ReservaIndividual("CUB-01", INICIO);

        assertThat(reserva.confirmar(8)).isEqualTo(LocalDateTime.of(2026, 8, 25, 17, 0));
    }

    @Test
    @DisplayName("VIOLACION: la sala grupal endurece la precondicion")
    void grupalEndureceLaPrecondicion() {
        Reserva reserva = new ReservaSalaGrupal("SG-02", INICIO, 4);

        assertThatThrownBy(() -> reserva.confirmar(6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("VIOLACION: la sala grupal no puede cancelarse")
    void grupalNoCancela() {
        Reserva reserva = new ReservaSalaGrupal("SG-02", INICIO, 4);

        assertThatThrownBy(reserva::cancelar).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("VIOLACION: el auditorio no confirma nada")
    void auditorioNoConfirma() {
        Reserva reserva = new ReservaAuditorio("AUD-01", INICIO);

        assertThatThrownBy(() -> reserva.confirmar(2))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("la interfaz gorda declara 8 operaciones")
    void interfazGorda() {
        assertThat(ServicioReservas.class.getDeclaredMethods()).hasSize(8);
    }

    @Test
    @DisplayName("VIOLACION: el servicio rechaza 3 de las 8 operaciones")
    void servicioRechazaOperaciones() {
        ServicioReservasBiblioteca servicio = new ServicioReservasBiblioteca();
        String codigo = servicio.reservar("CUB-01", "218123", 2);

        assertThat(codigo).isEqualTo("R-1");
        assertThatThrownBy(() -> servicio.extender(codigo, 1)).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> servicio.notificarPorWhatsapp("70012345", "hola")).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> servicio.generarQr(codigo)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("el ticket conserva su formato")
    void formatoDelTicket() {
        ServicioReservasBiblioteca servicio = new ServicioReservasBiblioteca();
        String codigo = servicio.reservar("CUB-01", "218123", 2);

        assertThat(servicio.imprimirTicket(codigo)).isEqualTo("TICKET R-1 | CUB-01|218123|2");
    }
}
