package bo.edu.usfx.biblioteca.dominio;

/**
 * Ejemplar de libro en la capa de Dominio.
 */
public class Libro {

    private final String signatura;
    private final String titulo;
    private final String autor;
    private boolean disponible = true;

    public Libro(String signatura, String titulo, String autor) {
        this.signatura = signatura;
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getSignatura() { return signatura; }
    public String getTitulo()    { return titulo; }
    public String getAutor()     { return autor; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
