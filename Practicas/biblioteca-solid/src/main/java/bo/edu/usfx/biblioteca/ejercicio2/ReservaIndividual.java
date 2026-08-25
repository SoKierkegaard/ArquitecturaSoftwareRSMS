package bo.edu.usfx.biblioteca.ejercicio2;

import java.time.LocalDateTime;

/** Cubiculo individual: cumple el contrato de la clase base. */
public class ReservaIndividual extends Reserva {

    private boolean cancelada = false;

    public ReservaIndividual(String codigoSala, LocalDateTime inicio) {
        super(codigoSala, inicio);
    }

    @Override
    public LocalDateTime confirmar(int horas) {
        return inicio.plusHours(horas);
    }

    @Override
    public void cancelar() { cancelada = true; }

    public boolean estaCancelada() { return cancelada; }
}
