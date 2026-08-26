package bo.edu.usfx.biblioteca.dominio;

import java.util.List;

/**
 * Puerto de persistencia de prestamos definido por el Dominio (DIP).
 */
public interface RepositorioPrestamos {

    void guardar(Prestamo prestamo);

    void actualizarDevolucion(Prestamo prestamo, double multa);

    List<Prestamo> activosDe(Usuario usuario);

    List<Prestamo> todos();

    List<Prestamo> delMes(int mes, int anio);
}
