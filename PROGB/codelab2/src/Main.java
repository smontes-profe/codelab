import java.util.Scanner;
public class Main {

    private static final int NUM_ENEMIGOS = 4;
    private static Personaje miPersonaje;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        boolean jugarDeNuevo;
        do {
            jugarDeNuevo = gameLoop();
        } while (jugarDeNuevo);
        sc.close();
    }

    private static boolean gameLoop(){
        
        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║             LEYENDAS DEL CÓDIGO      ║ ");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print(" Introduce el nombre de tu personaje: ");
        String nombre = sc.nextLine();

        System.out.println();
        System.out.println("Elige tu profesión:");
        System.out.println("  1  Guerrero ");
        System.out.println("  2  Mago     ");
        String profesion = sc.nextLine();
        while (!"1".equals(profesion) && !"2".equals(profesion)) {
            System.out.println("XX Opción no válida. Escribe 1 para Guerrero o 2 para Mago.");
            profesion = sc.nextLine();
        }
        miPersonaje = "1".equals(profesion) ? new Guerrero(nombre) : new Mago(nombre);

        System.out.println();
        System.out.println("════════════   PREPARANDO LA EXPEDICIÓN   ════════════");
        miPersonaje.mostrarFicha();

        for (int i = 1; i <= NUM_ENEMIGOS; i++) {
            System.out.println();
            System.out.println("═══════════   ENFRENTAMIENTO " + i + " / " + NUM_ENEMIGOS + "  ═══════════");

            Enemigo enemigoActual = crearEnemigo();
            
            int distancia = Math.random() < 0.5 ? 0 : 1;
            if (distancia == 1) {
                System.out.println(" ¡El enemigo aparece a lo lejos!");
                if (!miPersonaje.atacarADistancia(enemigoActual)) {
                    System.out.println(" El guerrero espera: ¡solo puede luchar cuerpo a cuerpo!");
                }
                if (enemigoActual.estaVivo() && miPersonaje.estaVivo()) {
                    // El Trasgo puede atacar desde lejos; los demás enemigos no tienen esta habilidad.
                    enemigoActual.atacarADistancia(miPersonaje);
                }
                if (enemigoActual.estaVivo() && miPersonaje.estaVivo()) {
                    System.out.println(" El enemigo se acerca... ¡ahora está cuerpo a cuerpo!");
                }
            }

            while (miPersonaje.estaVivo() && enemigoActual.estaVivo()) {
                miPersonaje.atacar(enemigoActual);
                if (enemigoActual.estaVivo()) {
                    enemigoActual.hacerDanio(miPersonaje);
                }
                if (enemigoActual.estaVivo()) {
                    // El Troll regenera vida al final de cada ronda de combate.
                    int vidaRecuperada = enemigoActual.regenerar();
                    if (vidaRecuperada > 0) {
                        System.out.println(" El " + enemigoActual.getNombre()
                                + " regenera " + vidaRecuperada + " puntos de vida.");
                    }
                }
                if (miPersonaje.estaVivo() && enemigoActual.estaVivo() && miPersonaje.getPociones() > 0)  beberPocion();
            }

            if (!miPersonaje.estaVivo()) {
                System.out.println();
                System.out.println(" ═══════════════ FIN DE LA AVENTURA ═══════════════ ☠️");
                System.out.println("Has sobrevivido a " + (i - 1) + " enfrentamiento(s).");
                return finDePartida();
            }
            
            System.out.println(" ¡Has derrotado al " + enemigoActual.getNombre() + "!");
            miPersonaje.mostrarFicha();
            System.out.println("═══════════════  FIN DEL ENFRENTAMIENTO  ═══════════════");
        }

        System.out.println();
        System.out.println(" ═══════════════ ¡VICTORIA! ═══════════════ ");
        System.out.println(" ¡Has sobrevivido a toda la expedición, " + miPersonaje.getNombre() + "!");
        return finDePartida();
    }

    private static Enemigo crearEnemigo() {
        return Math.random() < 0.5 ? new Troll() : new Trasgo();
    }

    private static void beberPocion(){
        System.out.println(" ¿Quieres beber una poción? 1. Sí  2. No");
        String respuesta = sc.nextLine();
        while (!"1".equals(respuesta) && !"2".equals(respuesta)) {
            System.out.println("XX Opción no válida. Escribe 1 para Sí o 2 para No.");
            respuesta = sc.nextLine();
        }
        if ("1".equals(respuesta)) {
            if (miPersonaje.usarPocion()) {
                System.out.println("- " + miPersonaje.getNombre() + " bebe una poción y recupera fuerzas.");
            } else {
                System.out.println("- Ya tienes la vida al máximo.");
            }
        } else {
            System.out.println("-> Sigues luchando sin usar una poción.");
        }
    }
    private static boolean finDePartida(){
        System.out.println();
        System.out.println(" ¿Quieres volver a intentarlo?");
        System.out.println("  1  ¡A por otra aventura!");
        System.out.println("  2  Por hoy ya he tenido bastante.");
        String result = sc.nextLine();
        while (!"1".equals(result) && !"2".equals(result)) {
            System.out.println("XX Opción no válida. Escribe 1 o 2.");
            result = sc.nextLine();
        }
        if ("2".equals(result)) {
            System.out.println("- ¡Gracias por jugar! Hasta la próxima.");
        }
        return "1".equals(result);
    }
}
