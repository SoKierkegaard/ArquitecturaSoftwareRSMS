package bo.edu.usfx.biblioteca.legado;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================================
 *  CODIGO LEGADO - PUNTO DE PARTIDA DE LA PRACTICA GUIADA
 * =====================================================================
 *
 *  Este es el modulo de prestamos del Sistema de Biblioteca de la USFX
 *  tal como lo dejo el ultimo grupo de pasantes. Funciona. Esta en
 *  produccion. Y cada cambio que pide la Direccion de Bibliotecas
 *  obliga a volver a tocar ESTE archivo.
 *
 *  Tu trabajo NO es reescribirlo desde cero: es refactorizarlo paso a
 *  paso, sin cambiar el comportamiento observable, guiado por SOLID.
 *
 *  El nombre ya es la primera pista: "Gestor".
 *  (Tema 3, diapositiva 12: senales de violacion del SRP)
 * =====================================================================
 */
public class GestorBiblioteca {

    /* El modulo de alto nivel CONSTRUYE sus propios detalles de bajo nivel. */
    private final ConexionMySQL conexion =
            new ConexionMySQL("jdbc:mysql://10.0.0.7:3306/biblioteca", "root", "usfx2026");

    private final ServidorCorreoSMTP correo =
            new ServidorCorreoSMTP("smtp.usfx.bo", 587);

    private final List<Prestamo> prestamos = new ArrayList<>();

    // -----------------------------------------------------------------
    // 1. REGISTRAR UN PRESTAMO
    // -----------------------------------------------------------------
    public String registrarPrestamo(Usuario usuario, Libro libro, LocalDate hoy) {

        // --- politica de prestamo segun el tipo de usuario ---
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

        // --- validaciones ---
        if (!libro.isDisponible()) {
            throw new IllegalStateException("El ejemplar " + libro.getSignatura() + " no esta disponible");
        }
        long activos = prestamos.stream()
                .filter(p -> p.getUsuario().getCodigo().equals(usuario.getCodigo()))
                .filter(Prestamo::estaActivo)
                .count();
        if (activos >= maximoLibros) {
            throw new IllegalStateException("El usuario alcanzo su limite de " + maximoLibros + " ejemplares");
        }

        // --- registro en memoria y en la base ---
        LocalDate limite = hoy.plusDays(diasPermitidos);
        Prestamo prestamo = new Prestamo(usuario, libro, hoy, limite);
        prestamos.add(prestamo);
        libro.setDisponible(false);

        conexion.ejecutar("INSERT INTO prestamo (codigo_usuario, signatura, fecha, limite) VALUES ('"
                + usuario.getCodigo() + "', '" + libro.getSignatura() + "', '" + hoy + "', '" + limite + "')");
        conexion.ejecutar("UPDATE libro SET disponible = 0 WHERE signatura = '" + libro.getSignatura() + "'");

        // --- notificacion ---
        correo.enviar(usuario.getCorreo(),
                "Prestamo registrado",
                "Estimado/a " + usuario.getNombre() + ", devuelva el ejemplar hasta el " + limite);

        // --- comprobante impreso ---
        return "=== BIBLIOTECA USFX ===\n"
             + "Usuario : " + usuario.getNombre() + " (" + usuario.getCodigo() + ")\n"
             + "Titulo  : " + libro.getTitulo() + "\n"
             + "Entrega : " + limite + "\n"
             + "=======================";
    }

    // -----------------------------------------------------------------
    // 2. CALCULAR LA MULTA POR RETRASO
    // -----------------------------------------------------------------
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

        // tope: la multa nunca supera los 200 Bs
        if (multa > 200.0) {
            multa = 200.0;
        }
        return multa;
    }

    // -----------------------------------------------------------------
    // 3. REGISTRAR LA DEVOLUCION
    // -----------------------------------------------------------------
    public String registrarDevolucion(Prestamo prestamo, LocalDate hoy) {
        prestamo.setFechaDevolucion(hoy);
        prestamo.getLibro().setDisponible(true);

        double multa = calcularMulta(prestamo, hoy);

        conexion.ejecutar("UPDATE prestamo SET devolucion = '" + hoy + "', multa = " + multa
                + " WHERE signatura = '" + prestamo.getLibro().getSignatura() + "'");
        conexion.ejecutar("UPDATE libro SET disponible = 1 WHERE signatura = '"
                + prestamo.getLibro().getSignatura() + "'");

        if (multa > 0) {
            correo.enviar(prestamo.getUsuario().getCorreo(),
                    "Multa por retraso",
                    "Debe cancelar Bs " + multa + " en caja antes de su proximo prestamo.");
        }

        return "Devolucion registrada. Multa: Bs " + multa;
    }

    // -----------------------------------------------------------------
    // 4. REPORTE MENSUAL (CSV escrito a disco)
    // -----------------------------------------------------------------
    public String generarReporteMensual(int mes, int anio) {
        conexion.consultar("SELECT * FROM prestamo WHERE MONTH(fecha) = " + mes
                + " AND YEAR(fecha) = " + anio);

        StringBuilder csv = new StringBuilder("codigo;titulo;fecha;limite;multa\n");
        for (Prestamo p : prestamos) {
            if (p.getFechaPrestamo().getMonthValue() == mes && p.getFechaPrestamo().getYear() == anio) {
                csv.append(p.getUsuario().getCodigo()).append(';')
                   .append(p.getLibro().getTitulo()).append(';')
                   .append(p.getFechaPrestamo()).append(';')
                   .append(p.getFechaLimite()).append(';')
                   .append(calcularMulta(p, LocalDate.now())).append('\n');
            }
        }
        // ademas de calcular, decide el formato Y el destino
        System.out.println("[FileWriter] C:/reportes/biblioteca_" + anio + "_" + mes + ".csv");
        return csv.toString();
    }

    // -----------------------------------------------------------------
    // 5. RECORDATORIOS
    // -----------------------------------------------------------------
    public int enviarRecordatorios(LocalDate hoy) {
        int enviados = 0;
        for (Prestamo p : prestamos) {
            if (p.estaActivo() && p.getFechaLimite().minusDays(1).equals(hoy)) {
                correo.enviar(p.getUsuario().getCorreo(),
                        "Su prestamo vence manana",
                        "Recuerde devolver: " + p.getLibro().getTitulo());
                enviados++;
            }
        }
        return enviados;
    }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public ConexionMySQL getConexion() { return conexion; }
    public ServidorCorreoSMTP getCorreo() { return correo; }
}
