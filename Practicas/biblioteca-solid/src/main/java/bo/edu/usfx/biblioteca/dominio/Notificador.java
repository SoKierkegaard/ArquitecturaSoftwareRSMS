package bo.edu.usfx.biblioteca.dominio;

/**
 * Puerto de notificacion definido por el Dominio (DIP).
 */
public interface Notificador {

    void notificar(String destino, String asunto, String mensaje);
}
