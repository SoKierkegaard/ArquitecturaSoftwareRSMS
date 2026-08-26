package bo.edu.usfx.biblioteca.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Catalogo de materiales de la biblioteca sin excepciones defensivas (LSP).
 */
public class Catalogo {

    private final List<Material> materiales = new ArrayList<>();

    public void agregar(Material m) {
        materiales.add(m);
    }

    public List<String> prestarTodo(LocalDate hoy) {
        return materiales.stream()
                .filter(Prestable.class::isInstance)
                .map(Prestable.class::cast)
                .map(p -> ((Material) p).titulo() + " -> " + p.prestar(hoy))
                .toList();
    }

    public String describir(Material m) {
        if (m instanceof LibroGeneral l) {
            return l.titulo() + " (préstamo 7 días, renovable)";
        } else if (m instanceof Revista r) {
            return r.titulo() + " (préstamo 2 días)";
        } else if (m instanceof LibroReferencia x) {
            return x.titulo() + " (solo consulta en sala)";
        }
        return m.titulo();
    }

    public List<Material> getMateriales() {
        return Collections.unmodifiableList(materiales);
    }
}
