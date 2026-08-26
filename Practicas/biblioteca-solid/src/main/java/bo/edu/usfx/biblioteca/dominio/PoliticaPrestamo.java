package bo.edu.usfx.biblioteca.dominio;

import java.math.BigDecimal;

/**
 * Abstraccion de Politica de Prestamo segun el tipo de usuario (OCP).
 */
public interface PoliticaPrestamo {

    BigDecimal TOPE = new BigDecimal("200.0");

    boolean aplicaA(Usuario usuario);

    int diasPermitidos();

    int maximoEjemplares();

    BigDecimal tarifaDiaria();

    default BigDecimal multa(long diasRetraso) {
        if (diasRetraso <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal calculada = tarifaDiaria().multiply(BigDecimal.valueOf(diasRetraso));
        return calculada.min(TOPE);
    }
}
