package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Rol para operaciones de prestamo y devolucion (ISP).
 */
public interface Prestable {

    void prestar(String codigoUsuario);

    void devolver(String codigoUsuario);
}
