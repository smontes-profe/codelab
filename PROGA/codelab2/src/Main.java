
import java.util.Random;
import java.util.Scanner;

//Objetivo de esta sesión: pedimos al usuario nombre del personaje y dificultad engre 2 opciones
//En esta ocasión vanmos a tener dos tipos de enemigos: Trasgos y trolls. 
//Los trasgos nos haran entre 20 y 35 puntos de daño (nivel fácil)
//Los trolls nos harán entre 40 y 60 puntos de daño (nivel difícil)
//En nivel difícil el jugador tendrá menos pociones.
//La aventura consistirá en un bucle donde el personaje se enfrentará a enemigos aleatorios (entre los dos posibles)

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Vamos a costumbrarnos a declarar siempre arriba del todo las constantes,
        // variables y funciones (cuando sepáis lo que son)
        // Por qué os pido esto? Alguien lo sabe? Os da curiosidad? Yo sí lo sé y es
        // importante para cuando programéis
        // SPOILER: el orden importa.

        final int MAX_VIDA = 100;
        final int POCIONES_FACIL = 3;
        final int POCIONES_DIFICIL = 2;
        final int DANIO_MIN_TROLL = 40;
        final int DANIO_MAX_TROLL = 60;
        final int DANIO_MIN_TRASGO = 20;
        final int DANIO_MAX_TRASGO = 35;
        final int CURACION = 40;

        int opcion;
        int vida = MAX_VIDA;
        // es mejor dejar estas variables sin asignar o no? qué gano con eso?
        int pociones = POCIONES_FACIL;

        // esta variable... tiene sentido?
        boolean estaVivo = true;
        // scanner a veces puede ser interesante declararlo, usarlo y cerrarlo
        Scanner sc;

        sc = new Scanner(System.in);
        System.out.println("=================================================");
        System.out.println("   ⚔️ BIENVENIDO AL REINO DE JAVA THE HUT ⚔️   ");
        System.out.println("=================================================");
        System.out.println("➤ Introduce el nombre de tu héroe: ");
        String nombre = sc.nextLine();
        System.out.println("¿Cómo prefieres iniciar tu gesta?");
        System.out.println("1. Modo Estándar (100 Vida / 3 Pociones)");
        System.out.println("2. Modo Difícil (Elegir vida hasta 50 / 1 Poción)");
        System.out.print("Selecciona una opción (1 o 2): ");

        // Cómo funcionaría esto? lo sabéis?
        opcion = sc.nextInt();
        // cómo me puedo asegurar si estoy cerrando el scanner?
        // sabéis cómo buscar todas las referencias de una variable?

        // Qué pasa si no introduzco ni 1 ni 2?
        // y si no introduzco un int?
        // Cómo podemos protegernos ante esto? lo sabéis?
        // SI no concéis aun el try/catch, podemos usar
        // sc.hasNextInt();

        // de nuevo números locos / magic numbers
        // os acordáis de cómo podemos solucionar esto?
        if (opcion == 2) {
            pociones = POCIONES_DIFICIL;
            System.out.println("⚠️ Has elegido el camino del guerrero. ¡Suerte!");
        } else {
            // Valores por defecto
            System.out.println("✅ Partida cargada con valores fáciles. Chicken!");
        }
        // podríamos resolver esto con un switch en lugar de con un if/else?
        // cuál sería mejor práctic ay por qué

        // --- 2. FICHA DEL HÉROE ---
        System.out.println("       📜 FICHA DE PERSONAJE 📜       ");
        System.out.println(nombre);
        System.out.println(vida);
        System.out.println("pociones " + pociones);

        // ***************** BLOQUE: Bucles (La Batalla Continúa)
        // **********************
        System.out.println("--- ¡COMIENZA LA BATALLA REAL! ---");

        // realizamos el bucle de la batalla
        while (vida > 0 && estaVivo) {
            // nos aseguramos de que el enemigo sea distinto del anterior

            System.out.println("\nUn enemigo te ataca...");
            // Generamos tipo de enemigo aleatorio: 1 = Trasgo, 2 = Troll
            int enemigo = new Random().nextInt(2) + 1;

            // aquí, por lo que sea me he equivocado y he nombrado golpeEnemigo2 la
            // variable, en lugar de
            // simplemente golpeEnemigo.. cómo puedo corregir este error sin tener que
            // reemplazar su
            // nombre en cada uso?
            int golpeEnemigo2;

            switch (enemigo) {
                case 1:
                    golpeEnemigo2 = new Random().nextInt(DANIO_MAX_TRASGO - DANIO_MIN_TRASGO + 1) + DANIO_MIN_TRASGO;
                    System.out.println("¡Un Trasgo te ataca!");
                    break;
                case 2:
                    golpeEnemigo2 = new Random().nextInt(DANIO_MAX_TROLL - DANIO_MIN_TROLL + 1) + DANIO_MIN_TROLL;
                    System.out.println("¡Un Troll te ataca!");
                    break;
                default:
                    golpeEnemigo2 = 0;
                    System.out.println("El enemigo huye.");
            }

            // Procesamos el daño realizado y lo mostranmos en pantalla
            vida -= golpeEnemigo2;
            System.out.println("Recibes " + golpeEnemigo2 + " de daño. Vida actual: " + vida);

            // Lógica de curación automática
            // si el héroe tiene aun pociones disponibles, usa una, y le cura la vida
            // si no quedan pociones lanza un warning por pantalla
            if (pociones > 0) {
                vida += CURACION;
                pociones--;
                System.out.println("Usando poción!!! ");
                System.out.println("Pociones restantes: " + pociones + " | Vida: " + vida);
            } else {
                System.out.println("Ojo!! No quedan pociones!!!!");
            }

            // Comprobamos si el héroe ha caído
            // Si ha caído, rompemos el bucle y mostramos un mensaje deprimente de game over
            // Si no, mostramos su estado actual
            if (vida <= 0) {
                estaVivo = false;
                System.out.println("Has muerto. Fin del juego.");
                // necesitamos romper el bucle aquí con un break, por ejemplo?
                // Si no, cuando se os ocurre que se pueda usar break;
            } else {
                System.out.println("Vida actual: " + vida + " | Pociones restantes: " + pociones);
            }

            // sabéis qué hace esto?qué me he dejado por hacer de buenas prácticas aquí?
            // "Magic..."
            Thread.sleep(1000);

        }
        sc.close();
    }

}