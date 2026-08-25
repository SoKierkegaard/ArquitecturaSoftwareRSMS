package bo.edu.usfx.biblioteca.legado;

/**
 * Interfaz "gorda" del modulo de servicios. Usada en el PASO 4 (ISP).
 *
 * Se escribio pensando en el ejemplar fisico de coleccion general y despues
 * se le fueron colgando operaciones. Hoy NINGUN implementador la usa entera.
 */
public interface OperacionesBiblioteca {

    void prestar(String codigoUsuario);

    void devolver(String codigoUsuario);

    void renovar(String codigoUsuario);

    void reservar(String codigoUsuario);

    /** Solo tiene sentido para material digitalizable. */
    byte[] descargarPdf();

    /** Solo tiene sentido para material digital. */
    void enviarPorCorreo(String destinatario);

    /** Solo tiene sentido para material fisico. */
    void enviarARestauracion();
}
