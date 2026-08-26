package bo.edu.usfx.biblioteca.dominio;

import java.math.BigDecimal;

public class PoliticaExterno implements PoliticaPrestamo {

    @Override
    public boolean aplicaA(Usuario usuario) {
        return "EXTERNO".equalsIgnoreCase(usuario.getTipo());
    }

    @Override
    public int diasPermitidos() {
        return 3;
    }

    @Override
    public int maximoEjemplares() {
        return 1;
    }

    @Override
    public BigDecimal tarifaDiaria() {
        return new BigDecimal("5.0");
    }
}
