import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Personaje> expedicion = new ArrayList<>();
        expedicion.add(new Guerrero("Bruna", 100, 18));
        expedicion.add(new Mago("Izan", 75, 12));

        char[][] mapa = {
                {'S', '.', '#', '.', 'T'},
                {'.', '.', '#', '.', '.'},
                {'#', '.', '.', '.', 'E'}
        };

        try (Scanner scanner = new Scanner(System.in)) {
            gestionarExpedicion(expedicion, scanner);
        }

        System.out.println("\n=== EQUIPO PREPARADO ===");
        mostrarExpedicion(expedicion);

        System.out.println("\n=== MAPA DEL LABERINTO ===");
        System.out.println("S: inicio | E: salida | #: muro | T: tesoro | .: pasillo");
        mostrarMapa(mapa);
        System.out.println("\nArrayList permite que el equipo crezca o se reduzca durante la partida.");
    }

    private static void gestionarExpedicion(ArrayList<Personaje> expedicion, Scanner scanner) {
        boolean configurando = true;

        while (configurando) {
            System.out.println("\n1. Reclutar guerrero");
            System.out.println("2. Reclutar mago");
            System.out.println("3. Retirar el último miembro");
            System.out.println("4. Mostrar expedición");
            System.out.println("0. Empezar la aventura");
            System.out.print("Elige una opción: ");

            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    expedicion.add(new Guerrero(pedirNombre(scanner), 100, 18));
                    System.out.println("Guerrero añadido. Total: " + expedicion.size());
                    break;
                case "2":
                    expedicion.add(new Mago(pedirNombre(scanner), 75, 12));
                    System.out.println("Mago añadido. Total: " + expedicion.size());
                    break;
                case "3":
                    if (expedicion.isEmpty()) {
                        System.out.println("La expedición ya está vacía.");
                    } else {
                        Personaje retirado = expedicion.remove(expedicion.size() - 1);
                        System.out.println(retirado.getNombre() + " deja la expedición.");
                    }
                    break;
                case "4":
                    mostrarExpedicion(expedicion);
                    break;
                case "0":
                    configurando = false;
                    break;
                default:
                    System.out.println("Opción no válida. Elige un número del menú.");
            }
        }
    }

    private static String pedirNombre(Scanner scanner) {
        System.out.print("Nombre del aventurero: ");
        String nombre = scanner.nextLine().trim();
        return nombre.isEmpty() ? "Aventurero sin nombre" : nombre;
    }

    private static void mostrarExpedicion(ArrayList<Personaje> expedicion) {
        if (expedicion.isEmpty()) {
            System.out.println("La expedición está vacía.");
            return;
        }

        System.out.println("Miembros: " + expedicion.size());
        for (int i = 0; i < expedicion.size(); i++) {
            Personaje personaje = expedicion.get(i);
            System.out.print((i + 1) + ". ");
            personaje.mostrarFicha();
            System.out.println("Ataque: " + personaje.atacar());
        }
    }

    private static void mostrarMapa(char[][] mapa) {
        for (char[] fila : mapa) {
            for (char casilla : fila) {
                System.out.print(casilla + " ");
            }
            System.out.println();
        }
    }
}

class Personaje {
    private final String nombre;
    private int vida;

    public Personaje(String nombre, int vida) {
        this.nombre = nombre;
        this.vida = vida;
    }

    public String getNombre() {
        return nombre;
    }

    public void mostrarFicha() {
        System.out.println(nombre + " | Vida: " + vida);
    }

    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    public int atacar() {
        return 8;
    }
}

class Guerrero extends Personaje {
    private final int puntosArmadura;

    public Guerrero(String nombre, int vida, int puntosArmadura) {
        super(nombre, vida);
        this.puntosArmadura = puntosArmadura;
    }

    public void mostrarArmadura() {
        System.out.println(getNombre() + " tiene " + puntosArmadura + " puntos de armadura.");
    }

    @Override
    public int atacar() {
        return 20;
    }
}

class Mago extends Personaje {
    private int mana;

    public Mago(String nombre, int vida, int mana) {
        super(nombre, vida);
        this.mana = mana;
    }

    public void meditar() {
        mana += 10;
        System.out.println(getNombre() + " medita y recupera mana. Mana: " + mana);
    }

    @Override
    public int atacar() {
        return 14;
    }
}
