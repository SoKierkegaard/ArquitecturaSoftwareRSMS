package practica2;

public abstract class Aves extends Animal {

    double peso;
    double tamanoAlas;

    public Aves(String nombre, double peso, double tamanoAlas) {
        super(nombre);
        this.peso = peso;
        this.tamanoAlas = tamanoAlas;
    }

    public String volar() {
        return nombre + " está volando!!!";
    }

    @Override
    public String toString() {
        return "Ave{nombre=" + nombre + ", peso=" + peso +
               ", tamanoAlas=" + tamanoAlas + "}";
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTamanoAlas() {
        return tamanoAlas;
    }

    public void setTamanoAlas(double tamanoAlas) {
        this.tamanoAlas = tamanoAlas;
    }
}
