import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// --- 1. DEFINICIÓN DEL OBJETO PERSONAJE ---
class Personaje {
    String nombre;
    int vida;
    int pociones;
    ArrayList<String> inventario;

    // Constructor: Cómo se crea un personaje
    public Personaje(String nombre, int vida, int pociones) {
        this.nombre = nombre;
        this.vida = vida;
        this.pociones = pociones;
        this.inventario = new ArrayList<>();
        // Items iniciales
        this.inventario.add("Mapa arrugado");
        this.inventario.add("Daga de hierro");
    }

    // El personaje ahora "sabe" mostrar su propia ficha
    public void mostrarFicha() {
        System.out.println("\n       📜 FICHA DE PERSONAJE 📜       ");
        System.out.println("Héroe: " + this.nombre);
        System.out.println("Vida: " + this.vida);
        System.out.println("Pociones: " + this.pociones);
        System.out.println("Inventario: " + this.inventario);
        System.out.println("--------------------------------------\n");
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        mostrarBienvenida();

        // --- 2. CREACIÓN DEL OBJETO (INSTANCIA) ---
        Personaje heroe = configurarPartida();
        heroe.mostrarFicha();

        while (heroe.vida > 0) {
            verificarEstadoCritico(heroe);
            ejecutarBatalla(heroe);

            if (heroe.vida <= 0) {
                mostrarMensajeFinal("¡" + heroe.nombre + " ha sido derrotado! ☠️");
                break;
            }

            gestionarInventario(heroe);
            eventoCofre(heroe);
        }
    }

    // --- MÉTODOS DE CONFIGURACIÓN ---

    public static void mostrarBienvenida() {
        System.out.println("==========================================");
        System.out.println("   ⚔️ BIENVENIDO AL REINO DE JAVA ⚔️   ");
        System.out.println("==========================================");
    }

    public static Personaje configurarPartida() {
        System.out.print("\n➤ Introduce el nombre de tu héroe: ");
        String nombre = sc.nextLine();
        int vidaInicial = 100;
        int pocionesIniciales = 3;

        try {
            System.out.println("\n¿Dificultad? 1. Estándar | 2. Difícil");
            int opcion = sc.nextInt();
            if (opcion == 2) {
                vidaInicial = 50;
                pocionesIniciales = 1;
                System.out.println("⚠️ Modo difícil activado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Error de datos. Penalización aplicada.");
            vidaInicial = 10;
            pocionesIniciales = 0;
            sc.nextLine();
        }

        // Retornamos un NUEVO objeto Personaje con los datos elegidos
        return new Personaje(nombre, vidaInicial, pocionesIniciales);
    }

    // --- LÓGICA DE JUEGO (Pasamos el objeto 'heroe' como parámetro) ---

    public static void verificarEstadoCritico(Personaje h) {
        if (h.vida < 20 && h.pociones > 0) {
            System.out.println("\n[SISTEMA] ¡Vida baja! Usando poción automática...");
            h.vida = 50;
            h.pociones--;
            System.out.println(">>> Nueva vida: " + h.vida);
        }
    }

    public static void ejecutarBatalla(Personaje h) throws InterruptedException {
        System.out.println("\n--- ¡BATALLA! ---");
        int golpe = random.nextInt(21) + 10;
        h.vida -= golpe;
        System.out.println(h.nombre + " recibe " + golpe + " de daño. Vida: " + h.vida);
        Thread.sleep(1000);
    }

    public static void gestionarInventario(Personaje h) {
        System.out.println("\n✨ ¡Encuentras un 'Amuleto'! ¿Recoger? (1: Sí | 2: No)");
        if (sc.nextInt() == 1) {
            h.inventario.add("Amuleto de Java");
            System.out.println("📦 Guardado en tu mochila.");
        }
    }

    public static void eventoCofre(Personaje h) throws InterruptedException {
        System.out.println("\n🎁 ¿Abres el cofre misterioso? (1: Sí | 2: No)");
        if (sc.nextInt() == 1) {
            int suerte = random.nextInt(2);
            if (suerte == 0) {
                System.out.println("💰 ¡Tesoro! Espada añadida.");
                h.inventario.add("Espada Pro");
            } else {
                System.out.println("👾 ¡Trampa! Pierdes 10 de vida.");
                h.vida -= 10;
            }
        }
    }

    public static void mostrarMensajeFinal(String mensaje) {
        for (char letra : mensaje.toCharArray()) {
            System.out.print(letra);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
        System.out.println();
    }
}