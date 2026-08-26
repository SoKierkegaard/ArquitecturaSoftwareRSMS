package bo.edu.usfx.biblioteca.presentacion;

import bo.edu.usfx.biblioteca.dominio.Prestamo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Generacion del reporte mensual en formato CSV (Responsable ante Kardex / Administracion).
 */
public class ReportePrestamosCsv {

    public static String formatear(List<Prestamo> prestamos, int mes, int anio) {
        StringBuilder csv = new StringBuilder("codigo;titulo;fecha;limite;multa\n");
        for (Prestamo p : prestamos) {
            if (p.getFechaPrestamo().getMonthValue() == mes && p.getFechaPrestamo().getYear() == anio) {
                double multa = calcularMulta(p, LocalDate.now());
                csv.append(p.getUsuario().getCodigo()).append(';')
                   .append(p.getLibro().getTitulo()).append(';')
                   .append(p.getFechaPrestamo()).append(';')
                   .append(p.getFechaLimite()).append(';')
                   .append(multa).append('\n');
            }
        }
        System.out.println("[FileWriter] C:/reportes/biblioteca_" + anio + "_" + mes + ".csv");
        return csv.toString();
    }

    private static double calcularMulta(Prestamo prestamo, LocalDate hoy) {
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
}
