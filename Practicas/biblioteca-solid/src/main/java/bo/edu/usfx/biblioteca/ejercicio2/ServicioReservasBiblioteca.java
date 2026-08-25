package bo.edu.usfx.biblioteca.ejercicio2;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementacion actual. Fijate cuantas veces aparece la palabra
 * UnsupportedOperationException y cuantas tecnologias concretas conoce.
 */
public class ServicioReservasBiblioteca implements ServicioReservas {

    private final Map<String, String> reservas = new HashMap<>();
    private int contador = 0;

    @Override
    public String reservar(String codigoSala, String codigoUsuario, int horas) {
        String codigo = "R-" + (++contador);
        reservas.put(codigo, codigoSala + "|" + codigoUsuario + "|" + horas);
        System.out.println("[MySQL] INSERT INTO reserva VALUES ('" + codigo + "')");
        System.out.println("[SMTP smtp.usfx.bo:587] Reserva " + codigo + " confirmada");
        return codigo;
    }

    @Override
    public void cancelar(String codigoReserva) {
        reservas.remove(codigoReserva);
        System.out.println("[MySQL] DELETE FROM reserva WHERE codigo = '" + codigoReserva + "'");
    }

    @Override
    public void extender(String codigoReserva, int horasExtra) {
        throw new UnsupportedOperationException("Aun no implementado en esta version");
    }

    @Override
    public void notificarPorCorreo(String destinatario, String mensaje) {
        System.out.println("[SMTP smtp.usfx.bo:587] " + destinatario + " :: " + mensaje);
    }

    @Override
    public void notificarPorSms(String telefono, String mensaje) {
        System.out.println("[SMS Twilio] " + telefono + " :: " + mensaje);
    }

    @Override
    public void notificarPorWhatsapp(String telefono, String mensaje) {
        throw new UnsupportedOperationException("La universidad no contrato la API de WhatsApp");
    }

    @Override
    public byte[] generarQr(String codigoReserva) {
        throw new UnsupportedOperationException("Requiere la libreria ZXing, no incluida");
    }

    @Override
    public String imprimirTicket(String codigoReserva) {
        return "TICKET " + codigoReserva + " | " + reservas.getOrDefault(codigoReserva, "?");
    }

    public Map<String, String> getReservas() { return reservas; }
}
