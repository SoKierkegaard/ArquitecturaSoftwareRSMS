package practica2;

import java.util.ArrayList;

public class Zoologico {
    private String nombre;
    private String direccion;
    private String telefono;
    private ArrayList<Jaula> listaJaulas;

    public Zoologico(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.listaJaulas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Jaula> getListaJaulas() {
        return listaJaulas;
    }

    public void setListaJaulas(ArrayList<Jaula> listaJaulas) {
        this.listaJaulas = listaJaulas;
    }

    public void agregarJaula(Jaula jaula) {
        listaJaulas.add(jaula);
    }

    public void mostrarMamiferos() {
        System.out.println("\n========== MAMÍFEROS EN EL ZOOLÓGICO ==========");
        boolean encontrado = false;
        for (Jaula jaula : listaJaulas) {
            if (jaula.getAnimal() instanceof Mamiferos) {
                Mamiferos m = (Mamiferos) jaula.getAnimal();
                System.out.println(jaula + " | Temperatura: " + m.getTemperatura() + " °C");
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay mamíferos registrados actualmente.");
        }
    }

    public void mostrarAves() {
        System.out.println("\n========== AVES EN EL ZOOLÓGICO ==========");
        boolean encontrado = false;
        for (Jaula jaula : listaJaulas) {
            if (jaula.getAnimal() instanceof Aves) {
                Aves a = (Aves) jaula.getAnimal();
                System.out.println(jaula + " | " + a.volar());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay aves registradas actualmente.");
        }
    }

    public void mostrarPeces() {
        System.out.println("\n========== PECES EN EL ZOOLÓGICO ==========");
        boolean encontrado = false;
        for (Jaula jaula : listaJaulas) {
            if (jaula.getAnimal() instanceof Peces) {
                Peces p = (Peces) jaula.getAnimal();
                System.out.println(jaula + " | " + p.nadar());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay peces registrados actualmente.");
        }
    }

    public void mostrarInformacion() {
        System.out.println("\n===============================================");
        System.out.println("           INFORMACIÓN DEL ZOOLÓGICO           ");
        System.out.println("===============================================");
        System.out.println("Nombre:    " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Teléfono:  " + telefono);
        System.out.println("Cantidad de jaulas: " + listaJaulas.size());
        System.out.println("---------------- Jaulas ----------------------");
        if (listaJaulas.isEmpty()) {
            System.out.println("No hay jaulas en el zoológico.");
        } else {
            for (int i = 0; i < listaJaulas.size(); i++) {
                System.out.println((i + 1) + ". " + listaJaulas.get(i));
            }
        }
        System.out.println("===============================================");
    }

    @Override
    public String toString() {
        return "Zoologico{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", totalJaulas=" + listaJaulas.size() +
                '}';
    }
}
