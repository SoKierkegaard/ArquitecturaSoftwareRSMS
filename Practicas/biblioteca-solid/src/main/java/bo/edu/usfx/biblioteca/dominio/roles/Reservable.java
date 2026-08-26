package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Rol para operaciones de reserva de ejemplares (ISP).
 */
public interface Reservable {

    void reservar(String codigoUsuario);
}
