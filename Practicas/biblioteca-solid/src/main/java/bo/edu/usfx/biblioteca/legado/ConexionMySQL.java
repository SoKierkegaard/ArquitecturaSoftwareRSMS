package bo.edu.usfx.biblioteca.legado;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulacion de un driver MySQL. NO abre conexiones reales: guarda en memoria
 * las sentencias que recibe para que la practica corra sin instalar un motor.
 *
 * Para el diseno da lo mismo: lo relevante es que GestorBiblioteca CONOCE
 * esta clase concreta, la URL, el usuario, la clave y el dialecto SQL.
 */
public class ConexionMySQL {

    private final String url;
    private final String usuario;
    private final String clave;
    private final List<String> sentenciasEjecutadas = new ArrayList<>();

    public ConexionMySQL(String url, String usuario, String clave) {
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
    }

    public void ejecutar(String sql) {
        sentenciasEjecutadas.add(sql);
        System.out.println("[MySQL " + url + "] " + sql);
    }

    public List<String> consultar(String sql) {
        sentenciasEjecutadas.add(sql);
        System.out.println("[MySQL " + url + "] " + sql);
        return new ArrayList<>();
    }

    public List<String> getSentenciasEjecutadas() { return sentenciasEjecutadas; }
    public String getUrl() { return url; }
    public String getUsuario() { return usuario; }
    public String getClave() { return clave; }
}
