package practica2;

public class FabricaAves implements FabricaAnimal {

    @Override
    public Animal crearAnimal(String tipo, String nombre, double atr1, double atr2, String atr3) {
        switch (tipo.toLowerCase()) {
            case "loro":
                return new Loro(nombre, atr1, atr2);
            case "aguila":
                return new Aguila(nombre, atr1, atr2);
            case "condor":
                return new Condor(nombre, atr1, atr2);
            default:
                return null;
        }
    }
}
