package bo.edu.usfx.biblioteca.ejercicio2;

import java.time.LocalDateTime;

/**
 * =====================================================================
 *  EJERCICIO 2 - Modulo de reservas de salas de estudio
 * =====================================================================
 *  Contrato de la clase base, tal como lo asume TODO el codigo cliente:
 *
 *    - confirmar(horas) acepta cualquier valor entre 1 y 8.
 *    - devuelve SIEMPRE la hora de fin de la reserva.
 *    - nunca lanza excepciones por el numero de horas dentro de ese rango.
 *
 *  Tu tarea: aplicar LSP e ISP (y DIP donde corresponda) para que ningun
 *  subtipo mienta sobre lo que puede hacer.
 * =====================================================================
 */
public abstract class Reserva {

    protected final String codigoSala;
    protected final LocalDateTime inicio;

    protected Reserva(String codigoSala, LocalDateTime inicio) {
        this.codigoSala = codigoSala;
        this.inicio = inicio;
    }

    /** Contrato: 1 <= horas <= 8. Devuelve la hora de fin. */
    public abstract LocalDateTime confirmar(int horas);

    /** Contrato: cancelar siempre es posible antes del inicio. */
    public abstract void cancelar();

    public String getCodigoSala() { return codigoSala; }
    public LocalDateTime getInicio() { return inicio; }
}
