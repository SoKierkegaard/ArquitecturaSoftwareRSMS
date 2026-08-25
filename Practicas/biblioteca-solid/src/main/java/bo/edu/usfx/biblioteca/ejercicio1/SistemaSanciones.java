package bo.edu.usfx.biblioteca.ejercicio1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================================
 *  EJERCICIO 1 - Modulo de sanciones y solvencias
 * =====================================================================
 *  La Direccion de Bibliotecas sanciona a quien devuelve tarde, danna o
 *  pierde un ejemplar. Este modulo calcula la sancion, la guarda, avisa
 *  al usuario y emite el certificado de solvencia.
 *
 *  Tu tarea: refactorizarlo aplicando SRP, OCP y DIP.
 *  NO cambies el comportamiento observable: las pruebas de
 *  SistemaSancionesTest deben seguir en verde despues de cada commit.
 * =====================================================================
 */
public class SistemaSanciones {

    private final List<String> bitacora = new ArrayList<>();

    /**
     * Calcula los dias de suspension segun el tipo de infraccion.
     * Cada nueva infraccion que inventa el reglamento obliga a editar aqui.
     */
    public int calcularSuspension(String tipoInfraccion, int diasRetraso, double valorEjemplar) {

        int dias;
        if ("RETRASO".equals(tipoInfraccion)) {
            dias = diasRetraso * 2;
        } else if ("DANNO_LEVE".equals(tipoInfraccion)) {
            dias = 15;
        } else if ("DANNO_GRAVE".equals(tipoInfraccion)) {
            dias = 30;
        } else if ("PERDIDA".equals(tipoInfraccion)) {
            dias = 90;
        } else if ("PRESTAMO_A_TERCEROS".equals(tipoInfraccion)) {
            dias = 60;
        } else {
            dias = 0;
        }

        if (dias > 180) {
            dias = 180;
        }
        return dias;
    }

    /** Calcula el monto en bolivianos que debe reponer el usuario. */
    public double calcularReposicion(String tipoInfraccion, double valorEjemplar) {
        if ("PERDIDA".equals(tipoInfraccion)) {
            return valorEjemplar * 1.5;
        } else if ("DANNO_GRAVE".equals(tipoInfraccion)) {
            return valorEjemplar * 0.8;
        } else if ("DANNO_LEVE".equals(tipoInfraccion)) {
            return 50.0;
        }
        return 0.0;
    }

    /**
     * Aplica la sancion: calcula, guarda en MySQL, manda un SMS y devuelve
     * el acta en texto plano. Cuatro actores en un solo metodo.
     */
    public String aplicarSancion(String codigoUsuario, String telefono, String tipoInfraccion,
                                 int diasRetraso, double valorEjemplar, LocalDate hoy) {

        int suspension = calcularSuspension(tipoInfraccion, diasRetraso, valorEjemplar);
        double reposicion = calcularReposicion(tipoInfraccion, valorEjemplar);
        LocalDate habilitado = hoy.plusDays(suspension);

        // --- persistencia: SQL a mano, conexion construida aqui mismo ---
        String sql = "INSERT INTO sancion (codigo, tipo, dias, monto, hasta) VALUES ('"
                + codigoUsuario + "', '" + tipoInfraccion + "', " + suspension + ", "
                + reposicion + ", '" + habilitado + "')";
        System.out.println("[MySQL jdbc:mysql://10.0.0.7:3306/biblioteca] " + sql);
        bitacora.add(sql);

        // --- notificacion: proveedor concreto, cableado en la clase ---
        System.out.println("[SMS Twilio +591" + telefono + "] Suspension de " + suspension
                + " dias hasta el " + habilitado);
        bitacora.add("SMS:" + telefono);

        // --- presentacion ---
        return "ACTA DE SANCION\n"
             + "Usuario     : " + codigoUsuario + "\n"
             + "Infraccion  : " + tipoInfraccion + "\n"
             + "Suspension  : " + suspension + " dias\n"
             + "Reposicion  : Bs " + reposicion + "\n"
             + "Habilitado  : " + habilitado;
    }

    /** Certificado de solvencia en HTML: otro formato, otro actor. */
    public String emitirSolvencia(String codigoUsuario, String nombre, LocalDate hoy) {
        System.out.println("[MySQL] SELECT COUNT(*) FROM sancion WHERE codigo = '" + codigoUsuario + "'");
        return "<html><body><h1>SOLVENCIA BIBLIOTECARIA</h1>"
             + "<p>" + nombre + " (" + codigoUsuario + ") no registra sanciones pendientes.</p>"
             + "<p>Sucre, " + hoy + "</p></body></html>";
    }

    public List<String> getBitacora() { return bitacora; }
}
