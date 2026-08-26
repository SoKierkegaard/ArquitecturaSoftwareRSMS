package bo.edu.usfx.biblioteca.aplicacion;

import bo.edu.usfx.biblioteca.dominio.Libro;
import bo.edu.usfx.biblioteca.dominio.Notificador;
import bo.edu.usfx.biblioteca.dominio.Prestamo;
import bo.edu.usfx.biblioteca.dominio.RepositorioPrestamos;
import bo.edu.usfx.biblioteca.dominio.Usuario;
import bo.edu.usfx.biblioteca.presentacion.ReportePrestamosCsv;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Servicio de Aplicacion que orquesta los casos de uso de prestamos.
 */
public class ServicioPrestamos {

    private final RepositorioPrestamos repositorio;
    private final Notificador notificador;

    public ServicioPrestamos(RepositorioPrestamos repositorio, Notificador notificador) {
        this.repositorio = repositorio;
        this.notificador = notificador;
    }

    public Prestamo registrarPrestamo(Usuario usuario, Libro libro, LocalDate hoy) {
        int diasPermitidos;
        int maximoLibros;
        if ("ESTUDIANTE".equals(usuario.getTipo())) {
            diasPermitidos = 7;
            maximoLibros = 3;
        } else if ("DOCENTE".equals(usuario.getTipo())) {
            diasPermitidos = 15;
            maximoLibros = 5;
        } else if ("ADMINISTRATIVO".equals(usuario.getTipo())) {
            diasPermitidos = 10;
            maximoLibros = 2;
        } else if ("EXTERNO".equals(usuario.getTipo())) {
            diasPermitidos = 3;
            maximoLibros = 1;
        } else {
            throw new IllegalArgumentException("Tipo de usuario desconocido: " + usuario.getTipo());
        }

        if (!libro.isDisponible()) {
            throw new IllegalStateException("El ejemplar " + libro.getSignatura() + " no esta disponible");
        }

        long activos = repositorio.activosDe(usuario).size();
        if (activos >= maximoLibros) {
            throw new IllegalStateException("El usuario alcanzo su limite de " + maximoLibros + " ejemplares");
        }

        LocalDate limite = hoy.plusDays(diasPermitidos);
        Prestamo prestamo = new Prestamo(usuario, libro, hoy, limite);
        repositorio.guardar(prestamo);

        notificador.notificar(usuario.getCorreo(),
                "Prestamo registrado",
                "Estimado/a " + usuario.getNombre() + ", devuelva el ejemplar hasta el " + limite);

        return prestamo;
    }

    public double calcularMulta(Prestamo prestamo, LocalDate hoy) {
        long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy);
        if (diasRetraso <= 0) {
            return 0.0;
        }

        String tipo = prestamo.getUsuario().getTipo();
        double multa;
        if ("ESTUDIANTE".equals(tipo)) {
            multa = diasRetraso * 2.0;
        } else if ("DOCENTE".equals(tipo)) {
            multa = diasRetraso * 1.0;
        } else if ("ADMINISTRATIVO".equals(tipo)) {
            multa = diasRetraso * 1.5;
        } else if ("EXTERNO".equals(tipo)) {
            multa = diasRetraso * 5.0;
        } else {
            multa = diasRetraso * 3.0;
        }

        if (multa > 200.0) {
            multa = 200.0;
        }
        return multa;
    }

    public String registrarDevolucion(Prestamo prestamo, LocalDate hoy) {
        prestamo.setFechaDevolucion(hoy);
        prestamo.getLibro().setDisponible(true);

        double multa = calcularMulta(prestamo, hoy);
        repositorio.actualizarDevolucion(prestamo, multa);

        if (multa > 0) {
            notificador.notificar(prestamo.getUsuario().getCorreo(),
                    "Multa por retraso",
                    "Debe cancelar Bs " + multa + " en caja antes de su proximo prestamo.");
        }

        return "Devolucion registrada. Multa: Bs " + multa;
    }

    public String generarReporteMensual(int mes, int anio) {
        List<Prestamo> prestamos = repositorio.delMes(mes, anio);
        return ReportePrestamosCsv.formatear(prestamos, mes, anio);
    }

    public int enviarRecordatorios(LocalDate hoy) {
        int enviados = 0;
        for (Prestamo p : repositorio.todos()) {
            if (p.estaActivo() && p.getFechaLimite().minusDays(1).equals(hoy)) {
                notificador.notificar(p.getUsuario().getCorreo(),
                        "Su prestamo vence manana",
                        "Recuerde devolver: " + p.getLibro().getTitulo());
                enviados++;
            }
        }
        return enviados;
    }

    public RepositorioPrestamos getRepositorio() {
        return repositorio;
    }

    public Notificador getNotificador() {
        return notificador;
    }
}
