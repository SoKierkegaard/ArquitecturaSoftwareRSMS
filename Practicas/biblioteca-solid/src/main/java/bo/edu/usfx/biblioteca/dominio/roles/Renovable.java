package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Rol para operaciones de renovacion (ISP).
 */
public interface Renovable {

    void renovar(String codigoUsuario);
}
