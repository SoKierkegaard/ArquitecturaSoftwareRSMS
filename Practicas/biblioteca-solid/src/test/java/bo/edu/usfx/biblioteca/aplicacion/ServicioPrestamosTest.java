package bo.edu.usfx.biblioteca.aplicacion;

import bo.edu.usfx.biblioteca.dominio.CatalogoPoliticas;
import bo.edu.usfx.biblioteca.dominio.Libro;
import bo.edu.usfx.biblioteca.dominio.Notificador;
import bo.edu.usfx.biblioteca.dominio.PoliticaEstudiante;
import bo.edu.usfx.biblioteca.dominio.Prestamo;
import bo.edu.usfx.biblioteca.dominio.RepositorioPrestamos;
import bo.edu.usfx.biblioteca.dominio.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Paso 5 (DIP) - Pruebas unitarias ultrarrapidas con Mockito sin BD ni SMTP")
class ServicioPrestamosTest {

    @Mock
    private RepositorioPrestamos repositorio;

    @Mock
    private Notificador notificador;

    @Test
    @DisplayName("DIP: registra el prestamo y notifica al estudiante sin BD ni servidor SMTP real")
    void registraYNotifica() {
        when(repositorio.activosDe(any())).thenReturn(List.of());
        ServicioPrestamos servicio = new ServicioPrestamos(
                repositorio,
                notificador,
                new CatalogoPoliticas(List.of(new PoliticaEstudiante()))
        );

        Usuario ana = new Usuario("218123", "Ana Quispe", "ana@usfx.bo", "ESTUDIANTE");
        Libro libro = new Libro("005.1 M379c", "Clean Architecture", "R. C. Martin");
        Prestamo prestamo = servicio.registrarPrestamo(ana, libro, LocalDate.of(2026, 8, 25));

        assertThat(prestamo.getFechaLimite()).isEqualTo(LocalDate.of(2026, 9, 1));
        verify(repositorio).guardar(prestamo);
        verify(notificador).notificar(eq("ana@usfx.bo"), anyString(), anyString());
    }

    @Test
    @DisplayName("DIP: lanza excepcion si el usuario supero el maximo de ejemplares")
    void validaLimiteDeEjemplaresConMock() {
        Usuario ana = new Usuario("218123", "Ana Quispe", "ana@usfx.bo", "ESTUDIANTE");
        Prestamo p1 = new Prestamo(ana, new Libro("1", "L1", "A"), LocalDate.now(), LocalDate.now().plusDays(7));
        Prestamo p2 = new Prestamo(ana, new Libro("2", "L2", "A"), LocalDate.now(), LocalDate.now().plusDays(7));
        Prestamo p3 = new Prestamo(ana, new Libro("3", "L3", "A"), LocalDate.now(), LocalDate.now().plusDays(7));

        when(repositorio.activosDe(ana)).thenReturn(List.of(p1, p2, p3));

        ServicioPrestamos servicio = new ServicioPrestamos(repositorio, notificador);
        Libro cuarto = new Libro("4", "L4", "A");

        assertThatThrownBy(() -> servicio.registrarPrestamo(ana, cuarto, LocalDate.now()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("limite de 3 ejemplares");
    }

    @Test
    @DisplayName("DIP: calcula devolucion y notifica multa por retraso")
    void devolucionConMultaNotificada() {
        Usuario ana = new Usuario("218123", "Ana Quispe", "ana@usfx.bo", "ESTUDIANTE");
        Libro libro = new Libro("1", "L1", "A");
        LocalDate fechaPrestamo = LocalDate.of(2026, 8, 25);
        LocalDate fechaLimite = LocalDate.of(2026, 9, 1);
        Prestamo prestamo = new Prestamo(ana, libro, fechaPrestamo, fechaLimite);

        ServicioPrestamos servicio = new ServicioPrestamos(repositorio, notificador);

        String recibo = servicio.registrarDevolucion(prestamo, LocalDate.of(2026, 9, 6)); // 5 dias retraso

        assertThat(recibo).isEqualTo("Devolucion registrada. Multa: Bs 10.0");
        verify(repositorio).actualizarDevolucion(prestamo, 10.0);
        verify(notificador).notificar(eq("ana@usfx.bo"), eq("Multa por retraso"), anyString());
    }
}
