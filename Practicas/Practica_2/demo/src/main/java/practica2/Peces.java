package practica2;

public abstract class Peces extends Animal {
    double longitud;

    public Peces(String nombre, double longitud) {
        super(nombre);
        this.longitud = longitud;
    }

    public String nadar(){
        return nombre+ " está nadando!!!";
    }

    @Override
    public String toString(){
        return "Pez{nombre=" + nombre + ", longitud=" + longitud + "}";
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
