package practica2;

public class FabricaMamiferos implements FabricaAnimal {

    @Override
    public Animal crearAnimal(String tipo, String nombre, double atr1, double atr2, String atr3) {
        int numeroPatas = (int) atr2;

        switch (tipo.toLowerCase()) {
            case "leon":
                return new Leon(nombre, atr1, numeroPatas, atr3);
            case "oso":
                return new Oso(nombre, atr1, numeroPatas, atr3);
            case "mono":
                return new Mono(nombre, atr1, numeroPatas, atr3);
            default:
                return null;
        }
    }
}
