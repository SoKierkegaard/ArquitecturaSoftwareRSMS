package bo.edu.usfx.biblioteca.legado;

/** El espejo del anterior: tampoco puede con la mitad de la interfaz. */
public class EjemplarDigital implements OperacionesBiblioteca {

    private final String signatura;

    public EjemplarDigital(String signatura) { this.signatura = signatura; }

    @Override public void prestar(String codigoUsuario)  { System.out.println("Licencia asignada " + signatura); }
    @Override public void devolver(String codigoUsuario) { System.out.println("Licencia liberada " + signatura); }

    @Override
    public void renovar(String codigoUsuario) {
        throw new UnsupportedOperationException("La licencia digital no se renueva, se vuelve a pedir");
    }

    @Override
    public void reservar(String codigoUsuario) {
        throw new UnsupportedOperationException("El material digital nunca esta ocupado");
    }

    @Override public byte[] descargarPdf() { return new byte[]{ 0x25, 0x50, 0x44, 0x46 }; }
    @Override public void enviarPorCorreo(String destinatario) { System.out.println("Enviado a " + destinatario); }

    @Override
    public void enviarARestauracion() {
        throw new UnsupportedOperationException("Un archivo no se restaura fisicamente");
    }
}
