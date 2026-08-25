package bo.edu.usfx.biblioteca.legado;

/**
 * Usuario de la biblioteca.
 *
 * OLOR: "Primitive Obsession". El tipo de usuario es una cadena suelta.
 * Nada impide construir un Usuario con tipo "ESTUDIANTES" o "estudiante".
 */
public class Usuario {

    private final String codigo;
    private final String nombre;
    private final String correo;
    private final String tipo;      // "ESTUDIANTE" | "DOCENTE" | "ADMINISTRATIVO" | "EXTERNO"

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
