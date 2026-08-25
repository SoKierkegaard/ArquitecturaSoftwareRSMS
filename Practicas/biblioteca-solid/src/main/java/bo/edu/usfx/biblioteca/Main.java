package bo.edu.usfx.biblioteca;

import bo.edu.usfx.biblioteca.legado.GestorBiblioteca;
import bo.edu.usfx.biblioteca.legado.Libro;
import bo.edu.usfx.biblioteca.legado.Prestamo;
import bo.edu.usfx.biblioteca.legado.Usuario;

import java.time.LocalDate;

/**
 * Punto de entrada de demostracion. Ejecutalo ANTES de refactorizar y
 * guarda la salida: es tu linea base de comportamiento observable.
 *
 * NetBeans: clic derecho sobre el proyecto > Run  (o F6).
 */
public class Main {

    public static void main(String[] args) {

        GestorBiblioteca gestor = new GestorBiblioteca();

        Usuario ana = new Usuario("218123", "Ana Quispe", "ana.quispe@usfx.bo", "ESTUDIANTE");
        Libro clean = new Libro("005.1 M379c", "Clean Architecture", "Robert C. Martin");

        LocalDate hoy = LocalDate.of(2026, 8, 25);

        System.out.println(gestor.registrarPrestamo(ana, clean, hoy));

        Prestamo prestamo = gestor.getPrestamos().get(0);

        // El estudiante devuelve 5 dias tarde
        System.out.println(gestor.registrarDevolucion(prestamo, hoy.plusDays(12)));

        System.out.println();
        System.out.println("--- Reporte del mes ---");
        System.out.print(gestor.generarReporteMensual(8, 2026));
    }
}
