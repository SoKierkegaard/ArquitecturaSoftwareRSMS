package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Ejemplar fisico: implementa solo los roles fisicos sin excepciones ni metodos no soportados (ISP).
 */
public class EjemplarFisico implements Prestable, Renovable, Reservable, Restaurable {

    private final String signatura;

    public EjemplarFisico(String signatura) {
        this.signatura = signatura;
    }

    @Override
    public void prestar(String codigoUsuario) {
        System.out.println("Prestado " + signatura);
    }

    @Override
    public void devolver(String codigoUsuario) {
        System.out.println("Devuelto " + signatura);
    }

    @Override
    public void renovar(String codigoUsuario) {
        System.out.println("Renovado " + signatura);
    }

    @Override
    public void reservar(String codigoUsuario) {
        System.out.println("Reservado " + signatura);
    }

    @Override
    public void enviarARestauracion() {
        System.out.println("A restauracion: " + signatura);
    }

    public String getSignatura() {
        return signatura;
    }
}
