package bo.edu.usfx.biblioteca.dominio.roles;

/**
 * Ejemplar digital: implementa solo los roles digitales sin excepciones ni metodos no soportados (ISP).
 */
public class EjemplarDigital implements Prestable, Descargable, Distribuible {

    private final String signatura;

    public EjemplarDigital(String signatura) {
        this.signatura = signatura;
    }

    @Override
    public void prestar(String codigoUsuario) {
        System.out.println("Licencia asignada " + signatura);
    }

    @Override
    public void devolver(String codigoUsuario) {
        System.out.println("Licencia liberada " + signatura);
    }

    @Override
    public byte[] descargarPdf() {
        return new byte[]{ 0x25, 0x50, 0x44, 0x46 };
    }

    @Override
    public void enviarPorCorreo(String destinatario) {
        System.out.println("Enviado a " + destinatario);
    }

    public String getSignatura() {
        return signatura;
    }
}
