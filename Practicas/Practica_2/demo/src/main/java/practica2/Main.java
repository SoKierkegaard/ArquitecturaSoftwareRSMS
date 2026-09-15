package practica2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializamos el zoológico con datos básicos
        Zoologico zoo = new Zoologico("Zoológico Municipal", "Av. Principal #123", "71234567");

        // Fábricas usando el patrón Abstract Factory
        FabricaAnimal fabricaMamiferos = new FabricaMamiferos();
        FabricaAnimal fabricaAves = new FabricaAves();
        FabricaAnimal fabricaPeces = new FabricaPeces();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n========== MENÚ SISTEMA DE ZOOLÓGICO ==========");
            System.out.println("1. Añadir mamífero");
            System.out.println("2. Añadir ave");
            System.out.println("3. Añadir pez");
            System.out.println("4. Mostrar mamíferos");
            System.out.println("5. Mostrar aves");
            System.out.println("6. Mostrar peces");
            System.out.println("7. Mostrar información del zoológico");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Por favor ingrese una opción válida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n--- AÑADIR MAMÍFERO ---");
                    System.out.print("Tipo de mamífero (leon, oso, mono): ");
                    String tipoMamifero = scanner.nextLine().trim();

                    System.out.print("Nombre del animal: ");
                    String nombreMamifero = scanner.nextLine().trim();

                    System.out.print("Temperatura corporal (°C): ");
                    double temp = Double.parseDouble(scanner.nextLine().trim());

                    System.out.print("Número de patas: ");
                    double patas = Double.parseDouble(scanner.nextLine().trim());

                    System.out.print("Color: ");
                    String color = scanner.nextLine().trim();

                    Animal mamifero = fabricaMamiferos.crearAnimal(tipoMamifero, nombreMamifero, temp, patas, color);

                    if (mamifero != null) {
                        System.out.println("Datos de la jaula:");
                        System.out.print("Alto (m): ");
                        double alto = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Ancho (m): ");
                        double ancho = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Largo (m): ");
                        double largo = Double.parseDouble(scanner.nextLine().trim());

                        Jaula jaula = new Jaula(mamifero, alto, ancho, largo);
                        zoo.agregarJaula(jaula);
                        System.out.println("¡Mamífero y jaula agregados con éxito!");
                    } else {
                        System.out.println("Tipo de mamífero desconocido. No se pudo crear.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- AÑADIR AVE ---");
                    System.out.print("Tipo de ave (loro, aguila, condor): ");
                    String tipoAve = scanner.nextLine().trim();

                    System.out.print("Nombre del animal: ");
                    String nombreAve = scanner.nextLine().trim();

                    System.out.print("Peso (kg): ");
                    double peso = Double.parseDouble(scanner.nextLine().trim());

                    System.out.print("Tamaño de alas (m): ");
                    double tamAlas = Double.parseDouble(scanner.nextLine().trim());

                    Animal ave = fabricaAves.crearAnimal(tipoAve, nombreAve, peso, tamAlas, "");

                    if (ave != null) {
                        System.out.println("Datos de la jaula:");
                        System.out.print("Alto (m): ");
                        double alto = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Ancho (m): ");
                        double ancho = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Largo (m): ");
                        double largo = Double.parseDouble(scanner.nextLine().trim());

                        Jaula jaula = new Jaula(ave, alto, ancho, largo);
                        zoo.agregarJaula(jaula);
                        System.out.println("¡Ave y jaula agregadas con éxito!");
                    } else {
                        System.out.println("Tipo de ave desconocido. No se pudo crear.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- AÑADIR PEZ ---");
                    System.out.print("Tipo de pez (pacu, sabalo): ");
                    String tipoPez = scanner.nextLine().trim();

                    System.out.print("Nombre del animal: ");
                    String nombrePez = scanner.nextLine().trim();

                    System.out.print("Longitud (cm): ");
                    double longitud = Double.parseDouble(scanner.nextLine().trim());

                    Animal pez = fabricaPeces.crearAnimal(tipoPez, nombrePez, longitud, 0, "");

                    if (pez != null) {
                        System.out.println("Datos de la jaula / acuario:");
                        System.out.print("Alto (m): ");
                        double alto = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Ancho (m): ");
                        double ancho = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Largo (m): ");
                        double largo = Double.parseDouble(scanner.nextLine().trim());

                        Jaula jaula = new Jaula(pez, alto, ancho, largo);
                        zoo.agregarJaula(jaula);
                        System.out.println("¡Pez y jaula/acuario agregados con éxito!");
                    } else {
                        System.out.println("Tipo de pez desconocido. No se pudo crear.");
                    }
                    break;

                case 4:
                    zoo.mostrarMamiferos();
                    break;

                case 5:
                    zoo.mostrarAves();
                    break;

                case 6:
                    zoo.mostrarPeces();
                    break;

                case 7:
                    zoo.mostrarInformacion();
                    break;

                case 8:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Por favor elija un número entre 1 y 8.");
                    break;
            }
        }

        scanner.close();
    }
}
