package bo.edu.usfx.biblioteca.dominio;

import java.math.BigDecimal;

/**
 * Nueva categoria "EGRESADO" agregada por extension sin modificar ninguna clase previa (OCP).
 * Plazo: 5 dias, limite: 2 ejemplares, tarifa: Bs 3.0 por dia.
 */
public class PoliticaEgresado implements PoliticaPrestamo {

    @Override
    public boolean aplicaA(Usuario usuario) {
        return "EGRESADO".equalsIgnoreCase(usuario.getTipo());
    }

    @Override
    public int diasPermitidos() {
        return 5;
    }

    @Override
    public int maximoEjemplares() {
        return 2;
    }

    @Override
    public BigDecimal tarifaDiaria() {
        return new BigDecimal("3.0");
    }
}
