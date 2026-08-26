package bo.edu.usfx.biblioteca.dominio;

import java.math.BigDecimal;

public class PoliticaEstudiante implements PoliticaPrestamo {

    @Override
    public boolean aplicaA(Usuario usuario) {
        return "ESTUDIANTE".equalsIgnoreCase(usuario.getTipo());
    }

    @Override
    public int diasPermitidos() {
        return 7;
    }

    @Override
    public int maximoEjemplares() {
        return 3;
    }

    @Override
    public BigDecimal tarifaDiaria() {
        return new BigDecimal("2.0");
    }
}
