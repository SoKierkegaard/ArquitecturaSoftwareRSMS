package bo.edu.usfx.biblioteca;

import bo.edu.usfx.biblioteca.aplicacion.ServicioPrestamos;
import bo.edu.usfx.biblioteca.dominio.CatalogoPoliticas;
import bo.edu.usfx.biblioteca.dominio.Libro;
import bo.edu.usfx.biblioteca.dominio.Notificador;
import bo.edu.usfx.biblioteca.dominio.Prestamo;
import bo.edu.usfx.biblioteca.dominio.RepositorioPrestamos;
import bo.edu.usfx.biblioteca.dominio.Usuario;
import bo.edu.usfx.biblioteca.infraestructura.NotificadorSmtp;
import bo.edu.usfx.biblioteca.infraestructura.RepositorioPrestamosJdbc;
import bo.edu.usfx.biblioteca.presentacion.ComprobantePrestamo;

import java.time.LocalDate;

/**
 * Composition Root (Paso 5 - DIP):
 * Main es el UNICO lugar de ensamble donde se construyen las implementaciones
 * concretas y se inyectan a la capa de aplicacion.
 */
public class Main {

    public static void main(String[] args) {

        // --- Ensamblado de dependencias (Inversion de Dependencias) ---
        RepositorioPrestamos repositorio = new RepositorioPrestamosJdbc();
        Notificador notificador = new NotificadorSmtp();
        CatalogoPoliticas catalogoPoliticas = new CatalogoPoliticas();

        ServicioPrestamos servicio = new ServicioPrestamos(
                repositorio,
                notificador,
                catalogoPoliticas
        );

        Usuario ana = new Usuario("218123", "Ana Quispe", "ana.quispe@usfx.bo", "ESTUDIANTE");
        Libro clean = new Libro("005.1 M379c", "Clean Architecture", "Robert C. Martin");

        LocalDate hoy = LocalDate.of(2026, 8, 25);

        Prestamo prestamo = servicio.registrarPrestamo(ana, clean, hoy);
        System.out.println(ComprobantePrestamo.formatear(prestamo));

        // El estudiante devuelve 5 dias tarde
        System.out.println(servicio.registrarDevolucion(prestamo, hoy.plusDays(12)));

        System.out.println();
        System.out.println("--- Reporte del mes ---");
        System.out.print(servicio.generarReporteMensual(8, 2026));
    }
}
