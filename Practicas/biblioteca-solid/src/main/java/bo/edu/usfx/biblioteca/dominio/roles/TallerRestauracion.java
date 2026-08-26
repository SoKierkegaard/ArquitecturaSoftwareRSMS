package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Cliente de servicios de restauracion: solo depende del rol Restaurable (ISP).
 */
public class TallerRestauracion {

    public void recibir(Restaurable pieza) {
        pieza.enviarARestauracion();
    }
}
