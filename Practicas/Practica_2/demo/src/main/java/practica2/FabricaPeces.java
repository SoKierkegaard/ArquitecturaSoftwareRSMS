package practica2;

public class FabricaPeces implements FabricaAnimal {

    @Override
    public Animal crearAnimal(String tipo, String nombre, double atr1, double atr2, String atr3) {
        switch (tipo.toLowerCase()) {
            case "pacu":
                return new Pacu(nombre, atr1);
            case "sabalo":
                return new Sabalo(nombre, atr1);
            default:
                return null;
        }
    }
}
