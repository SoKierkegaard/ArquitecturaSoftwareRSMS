package bo.edu.usfx.biblioteca.ejercicio2;

import java.time.LocalDateTime;

/**
 * Sala grupal. Aqui alguien "endurecio" la precondicion: exige mas que la
 * clase base y ademas rompe la postcondicion cuando no le gusta el horario.
 */
public class ReservaSalaGrupal extends Reserva {

    private final int cantidadPersonas;

    public ReservaSalaGrupal(String codigoSala, LocalDateTime inicio, int cantidadPersonas) {
        super(codigoSala, inicio);
        this.cantidadPersonas = cantidadPersonas;
    }

    @Override
    public LocalDateTime confirmar(int horas) {
        // PRECONDICION ENDURECIDA: la base acepta 1..8, esta exige 1..4
        if (horas > 4) {
            throw new IllegalArgumentException("Las salas grupales se reservan por 4 horas como maximo");
        }
        if (cantidadPersonas < 3) {
            throw new IllegalStateException("La sala grupal requiere al menos 3 personas");
        }
        return inicio.plusHours(horas);
    }

    @Override
    public void cancelar() {
        // POSTCONDICION DEBILITADA: la base promete que cancelar siempre funciona
        throw new UnsupportedOperationException("Las salas grupales se cancelan en mesa de partes");
    }
}
