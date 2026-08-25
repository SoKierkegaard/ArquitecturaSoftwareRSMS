package bo.edu.usfx.biblioteca.legado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Cliente escrito contra el tipo base. Aqui se ve el dano de la violacion
 * de LSP: el codigo se llena de instanceof y de try/catch defensivos.
 */
public class CatalogoBiblioteca {

    private final List<MaterialBiblioteca> materiales = new ArrayList<>();

    public void agregar(MaterialBiblioteca material) {
        materiales.add(material);
    }

    /** Presta todo lo prestable... comprobando el tipo a mano. */
    public List<String> prestarTodo(LocalDate hoy) {
        List<String> comprobantes = new ArrayList<>();
        for (MaterialBiblioteca m : materiales) {
            // OLOR: comprobacion de tipo. Cada material nuevo obliga a volver aqui.
            if (m instanceof LibroReferencia) {
                continue;
            }
            try {
                comprobantes.add(m.getTitulo() + " -> " + m.prestar(hoy));
            } catch (UnsupportedOperationException e) {
                // "por si acaso": la red de seguridad que delata el mal diseno
            }
        }
        return comprobantes;
    }

    public List<MaterialBiblioteca> getMateriales() { return materiales; }
}
