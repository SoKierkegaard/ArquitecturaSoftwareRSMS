package bo.edu.usfx.biblioteca.presentacion;

import bo.edu.usfx.biblioteca.dominio.CatalogoPoliticas;
import bo.edu.usfx.biblioteca.dominio.Prestamo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Generacion del reporte mensual en formato CSV (Responsable ante Kardex / Administracion).
 */
public class ReportePrestamosCsv {

    public static String formatear(List<Prestamo> prestamos, int mes, int anio, CatalogoPoliticas catalogoPoliticas) {
        StringBuilder csv = new StringBuilder("codigo;titulo;fecha;limite;multa\n");
        for (Prestamo p : prestamos) {
            if (p.getFechaPrestamo().getMonthValue() == mes && p.getFechaPrestamo().getYear() == anio) {
                long diasRetraso = ChronoUnit.DAYS.between(p.getFechaLimite(), LocalDate.now());
                double multa = catalogoPoliticas.para(p.getUsuario()).multa(diasRetraso).doubleValue();
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

    public static String formatear(List<Prestamo> prestamos, int mes, int anio) {
        return formatear(prestamos, mes, anio, new CatalogoPoliticas());
    }
}
