package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Cliente de servicios de prestamo: solo depende del rol Prestable (ISP).
 */
public class MostradorPrestamos {

    public void atender(Prestable material, String codigoUsuario) {
        material.prestar(codigoUsuario);
    }

    public void recibir(Prestable material, String codigoUsuario) {
        material.devolver(codigoUsuario);
    }
}
