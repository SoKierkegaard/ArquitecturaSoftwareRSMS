package bo.edu.usfx.biblioteca.dominio;

/**
 * Usuario de la biblioteca en la capa de Dominio.
 */
public class Usuario {

    private final String codigo;
    private final String nombre;
    private final String correo;
    private final String tipo;

    public Usuario(String codigo, String nombre, String correo, String tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.tipo = tipo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getTipo()   { return tipo; }
}
