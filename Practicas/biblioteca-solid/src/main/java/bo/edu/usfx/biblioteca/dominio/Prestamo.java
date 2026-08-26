package bo.edu.usfx.biblioteca.dominio;

import java.time.LocalDate;

/**
 * Registro de un prestamo en la capa de Dominio.
 */
public class Prestamo {

    private final Usuario usuario;
    private final Libro libro;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaLimite;
    private LocalDate fechaDevolucion;

    public Prestamo(Usuario usuario, Libro libro, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
    }

    public Usuario getUsuario()           { return usuario; }
    public Libro getLibro()               { return libro; }
    public LocalDate getFechaPrestamo()   { return fechaPrestamo; }
    public LocalDate getFechaLimite()     { return fechaLimite; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate f) { this.fechaDevolucion = f; }
    public boolean estaActivo() { return fechaDevolucion == null; }
}
