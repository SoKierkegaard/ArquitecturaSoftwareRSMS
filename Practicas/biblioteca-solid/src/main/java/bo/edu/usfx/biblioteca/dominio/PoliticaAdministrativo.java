package bo.edu.usfx.biblioteca.dominio;

import java.math.BigDecimal;

public class PoliticaAdministrativo implements PoliticaPrestamo {

    @Override
    public boolean aplicaA(Usuario usuario) {
        return "ADMINISTRATIVO".equalsIgnoreCase(usuario.getTipo());
    }

    @Override
    public int diasPermitidos() {
        return 10;
    }

    @Override
    public int maximoEjemplares() {
        return 2;
    }

    @Override
    public BigDecimal tarifaDiaria() {
        return new BigDecimal("1.5");
    }
}
