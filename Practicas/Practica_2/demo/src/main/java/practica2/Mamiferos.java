package practica2;

public abstract class Mamiferos extends Animal {
    double temperatura;
    int numeroPatas;
    String color;

    public Mamiferos(String nombre, double temperatura, int numeroPatas, String color) {
        super(nombre);
        this.temperatura = temperatura;
        this.numeroPatas = numeroPatas;
        this.color = color;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getNumeroPatas() {
        return numeroPatas;
    }

    public void setNumeroPatas(int numeroPatas) {
        this.numeroPatas = numeroPatas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Mamífero{nombre=" + nombre + ", temperatura=" + temperatura +
               ", patas=" + numeroPatas + ", color=" + color + "}";
    }
}
