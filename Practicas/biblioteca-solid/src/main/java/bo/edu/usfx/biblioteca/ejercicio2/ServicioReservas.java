package bo.edu.usfx.biblioteca.ejercicio2;

/**
 * Interfaz gorda del modulo de reservas: mezcla el ciclo de vida de la
 * reserva con TRES canales de notificacion y DOS formatos de comprobante.
 *
 * Ningun cliente usa los ocho metodos.
 */
public interface ServicioReservas {

    String reservar(String codigoSala, String codigoUsuario, int horas);

    void cancelar(String codigoReserva);

    void extender(String codigoReserva, int horasExtra);

    void notificarPorCorreo(String destinatario, String mensaje);

    void notificarPorSms(String telefono, String mensaje);

    void notificarPorWhatsapp(String telefono, String mensaje);

    byte[] generarQr(String codigoReserva);

    String imprimirTicket(String codigoReserva);
}
