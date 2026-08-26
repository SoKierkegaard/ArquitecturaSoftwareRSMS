package bo.edu.usfx.biblioteca.infraestructura;

import bo.edu.usfx.biblioteca.dominio.Prestamo;
import bo.edu.usfx.biblioteca.dominio.RepositorioPrestamos;
import bo.edu.usfx.biblioteca.dominio.Usuario;
import bo.edu.usfx.biblioteca.legado.ConexionMySQL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adaptador de persistencia JDBC / MySQL (Responsable ante la Direccion de TI).
 */
public class RepositorioPrestamosJdbc implements RepositorioPrestamos {

    private final ConexionMySQL conexion;
    private final List<Prestamo> prestamos = new ArrayList<>();

    public RepositorioPrestamosJdbc(ConexionMySQL conexion) {
        this.conexion = conexion;
    }

    public RepositorioPrestamosJdbc() {
        this(new ConexionMySQL("jdbc:mysql://10.0.0.7:3306/biblioteca", "root", "usfx2026"));
    }

    @Override
    public void guardar(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.getLibro().setDisponible(false);

        conexion.ejecutar("INSERT INTO prestamo (codigo_usuario, signatura, fecha, limite) VALUES ('"
                + prestamo.getUsuario().getCodigo() + "', '"
                + prestamo.getLibro().getSignatura() + "', '"
                + prestamo.getFechaPrestamo() + "', '"
                + prestamo.getFechaLimite() + "')");
        conexion.ejecutar("UPDATE libro SET disponible = 0 WHERE signatura = '"
                + prestamo.getLibro().getSignatura() + "'");
    }

    @Override
    public void actualizarDevolucion(Prestamo prestamo, double multa) {
        conexion.ejecutar("UPDATE prestamo SET devolucion = '" + prestamo.getFechaDevolucion() + "', multa = " + multa
                + " WHERE signatura = '" + prestamo.getLibro().getSignatura() + "'");
        conexion.ejecutar("UPDATE libro SET disponible = 1 WHERE signatura = '"
                + prestamo.getLibro().getSignatura() + "'");
    }

    @Override
    public List<Prestamo> activosDe(Usuario usuario) {
        return prestamos.stream()
                .filter(p -> p.getUsuario().getCodigo().equals(usuario.getCodigo()))
                .filter(Prestamo::estaActivo)
                .toList();
    }

    @Override
    public List<Prestamo> todos() {
        return Collections.unmodifiableList(prestamos);
    }

    @Override
    public List<Prestamo> delMes(int mes, int anio) {
        conexion.consultar("SELECT * FROM prestamo WHERE MONTH(fecha) = " + mes
                + " AND YEAR(fecha) = " + anio);
        return prestamos.stream()
                .filter(p -> p.getFechaPrestamo().getMonthValue() == mes && p.getFechaPrestamo().getYear() == anio)
                .toList();
    }

    public ConexionMySQL getConexion() {
        return conexion;
    }
}
