package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Rol para envio digital por correo (ISP).
 */
public interface Distribuible {

    void enviarPorCorreo(String destinatario);
}
