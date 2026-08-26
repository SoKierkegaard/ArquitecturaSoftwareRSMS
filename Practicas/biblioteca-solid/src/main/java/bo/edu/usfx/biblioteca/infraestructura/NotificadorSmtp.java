package bo.edu.usfx.biblioteca.infraestructura;

import bo.edu.usfx.biblioteca.dominio.Notificador;
import bo.edu.usfx.biblioteca.legado.ServidorCorreoSMTP;

/**
 * Adaptador de notificaciones por correo SMTP (Responsable ante Comunicacion).
 */
public class NotificadorSmtp implements Notificador {

    private final ServidorCorreoSMTP servidorCorreo;

    public NotificadorSmtp(ServidorCorreoSMTP servidorCorreo) {
        this.servidorCorreo = servidorCorreo;
    }

    public NotificadorSmtp(String host, int puerto) {
        this(new ServidorCorreoSMTP(host, puerto));
    }

    public NotificadorSmtp() {
        this("smtp.usfx.bo", 587);
    }

    @Override
    public void notificar(String destino, String asunto, String mensaje) {
        servidorCorreo.enviar(destino, asunto, mensaje);
    }

    public ServidorCorreoSMTP getServidorCorreo() {
        return servidorCorreo;
    }
}
