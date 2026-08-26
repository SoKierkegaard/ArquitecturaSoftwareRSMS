package bo.edu.usfx.biblioteca.dominio;

import java.math.BigDecimal;

public class PoliticaDocente implements PoliticaPrestamo {

    @Override
    public boolean aplicaA(Usuario usuario) {
        return "DOCENTE".equalsIgnoreCase(usuario.getTipo());
    }

    @Override
    public int diasPermitidos() {
        return 15;
    }

    @Override
    public int maximoEjemplares() {
        return 5;
    }

    @Override
    public BigDecimal tarifaDiaria() {
        return new BigDecimal("1.0");
    }
}
