package bo.edu.usfx.biblioteca.ejercicio2;

import java.time.LocalDateTime;

/** Auditorio: en realidad ni siquiera es una reserva, requiere autorizacion. */
public class ReservaAuditorio extends Reserva {

    public ReservaAuditorio(String codigoSala, LocalDateTime inicio) {
        super(codigoSala, inicio);
    }

    @Override
    public LocalDateTime confirmar(int horas) {
        throw new UnsupportedOperationException("El auditorio requiere autorizacion de Vicerrectorado");
    }

    @Override
    public void cancelar() { /* silencio: no hace nada */ }
}
