package bo.edu.usfx.biblioteca.legado;

/** Implementador forzado a declarar metodos que no significan nada para el. */
public class EjemplarFisico implements OperacionesBiblioteca {

    private final String signatura;

    public EjemplarFisico(String signatura) { this.signatura = signatura; }

    @Override public void prestar(String codigoUsuario)  { System.out.println("Prestado " + signatura); }
    @Override public void devolver(String codigoUsuario) { System.out.println("Devuelto " + signatura); }
    @Override public void renovar(String codigoUsuario)  { System.out.println("Renovado " + signatura); }
    @Override public void reservar(String codigoUsuario) { System.out.println("Reservado " + signatura); }

    @Override
    public byte[] descargarPdf() {
        throw new UnsupportedOperationException("Un ejemplar fisico no se descarga");
    }

    @Override
    public void enviarPorCorreo(String destinatario) {
        throw new UnsupportedOperationException("Un ejemplar fisico no se envia por correo");
    }

    @Override
    public void enviarARestauracion() { System.out.println("A restauracion: " + signatura); }
}
