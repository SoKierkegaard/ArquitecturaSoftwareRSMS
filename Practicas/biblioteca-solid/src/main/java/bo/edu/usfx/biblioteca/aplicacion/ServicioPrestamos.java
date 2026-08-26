package bo.edu.usfx.biblioteca.aplicacion;

import bo.edu.usfx.biblioteca.dominio.CatalogoPoliticas;
import bo.edu.usfx.biblioteca.dominio.Libro;
import bo.edu.usfx.biblioteca.dominio.Notificador;
import bo.edu.usfx.biblioteca.dominio.PoliticaPrestamo;
import bo.edu.usfx.biblioteca.dominio.Prestamo;
import bo.edu.usfx.biblioteca.dominio.RepositorioPrestamos;
import bo.edu.usfx.biblioteca.dominio.Usuario;
import bo.edu.usfx.biblioteca.presentacion.ReportePrestamosCsv;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Servicio de Aplicacion que orquesta los casos de uso de prestamos cumpliendo SRP y OCP.
 */
public class ServicioPrestamos {

    private final RepositorioPrestamos repositorio;
    private final Notificador notificador;
    private final CatalogoPoliticas catalogoPoliticas;

    public ServicioPrestamos(RepositorioPrestamos repositorio, Notificador notificador, CatalogoPoliticas catalogoPoliticas) {
        this.repositorio = repositorio;
        this.notificador = notificador;
        this.catalogoPoliticas = catalogoPoliticas;
    }

    public ServicioPrestamos(RepositorioPrestamos repositorio, Notificador notificador) {
        this(repositorio, notificador, new CatalogoPoliticas());
    }

    public Prestamo registrarPrestamo(Usuario usuario, Libro libro, LocalDate hoy) {
        PoliticaPrestamo politica = catalogoPoliticas.para(usuario);

        if (!libro.isDisponible()) {
            throw new IllegalStateException("El ejemplar " + libro.getSignatura() + " no esta disponible");
        }

        long activos = repositorio.activosDe(usuario).size();
        if (activos >= politica.maximoEjemplares()) {
            throw new IllegalStateException("El usuario alcanzo su limite de " + politica.maximoEjemplares() + " ejemplares");
        }

        LocalDate limite = hoy.plusDays(politica.diasPermitidos());
        Prestamo prestamo = new Prestamo(usuario, libro, hoy, limite);
        repositorio.guardar(prestamo);

        notificador.notificar(usuario.getCorreo(),
                "Prestamo registrado",
                "Estimado/a " + usuario.getNombre() + ", devuelva el ejemplar hasta el " + limite);

        return prestamo;
    }

    public double calcularMulta(Prestamo prestamo, LocalDate hoy) {
        long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy);
        PoliticaPrestamo politica = catalogoPoliticas.para(prestamo.getUsuario());
        return politica.multa(diasRetraso).doubleValue();
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
        return ReportePrestamosCsv.formatear(prestamos, mes, anio, catalogoPoliticas);
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

    public CatalogoPoliticas getCatalogoPoliticas() {
        return catalogoPoliticas;
    }
}
