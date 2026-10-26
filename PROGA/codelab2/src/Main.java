
/* 
* Objetivo de esta sesión: pedimos al usuario nombre del personaje y dificultad engre 2 opciones
* En esta ocasión vanmos a tener dos tipos de enemigos: Trasgos y trolls. 
* Los trasgos nos haran entre 20 y 35 puntos de daño (nivel fácil)
* Los trolls nos harán entre 40 y 60 puntos de daño (nivel difícil)
* En nivel difícil el jugador tendrá menos pociones.
* La aventura consistirá en un bucle donde el personaje se enfrentará a enemigos aleatorios (entre los dos posibles)
 */

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Vamos a costumbrarnos a declarar siempre arriba del todo las constantes,
        // como vimos en el primer codelab
        final int MAX_VIDA = 100; // vida inicial del héroe y también máxima vida que puede tener (aunque una
                                  // poción lo curase de más)
        final int POCIONES_FACIL = 3; // número de pociones en la dificultad fácil
        final int POCIONES_DIFICIL = 2; // número de pociones en la dificultad difícil
        final int DANIO_MIN_TROLL = 40; // daño mínimo que puede hacer un troll
        final int DANIO_MAX_TROLL = 60; // daño máximo que puede hacer un troll
        final int DANIO_MIN_TRASGO = 20; // daño mínimo que puede hacer un trasgo
        final int DANIO_MAX_TRASGO = 35; // daño máximo que puede hacer un trasgo
        final int CURACION = 40; // cantidad de vida que recupera con una poción, aunque no puede superar la vida
                                 // máxima

        int dificultad; // variable para almacenar la dificultad elegida por el usuario
        // es mejor dejar estas variables sin asignar o no? qué gano con eso?
        int vidaHeroe = MAX_VIDA;
        int pocionesHeroe = POCIONES_FACIL;

        // esta variable... tiene sentido? Si la creé con un propósito inicial y ahora
        // no estoy seguro, una buena manera de comprobarlo es buscar todas las
        // referencias a esta variable en el código
        boolean estaVivo = true;
        // no me gusta mucho el nombre de esta variable, qué os parece? qué nombre le
        // pondríais vosotros?
        // si te la encuentras por ahí en el código, sabrías qué hace?
        Scanner sc = new Scanner(System.in);
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
        dificultad = sc.nextInt();
        // cómo me puedo asegurar si estoy cerrando el scanner?
        // sabéis cómo buscar todas las referencias de una variable? Lo acaabamos de ver
        // hace un momento!

        // Qué pasa si no introduzco ni 1 ni 2?
        // y si no introduzco un int?
        // Cómo podemos protegernos ante esto? lo sabéis?
        // SI no concéis aun el try/catch, podemos usar
        // sc.hasNextInt();

        // de nuevo números locos / magic numbers (por qué "2")
        // cómo solucionamos esto?
        if (dificultad == 2) {
            pocionesHeroe = POCIONES_DIFICIL;
            System.out.println("⚠️ Has elegido el camino del guerrero. ¡Suerte!");
        } else {
            // Valores por defecto
            System.out.println("✅ Partida cargada con valores fáciles. Chicken!");
        }
        // podríamos resolver esto con un switch en lugar de con un if/else?
        // cuál sería mejor práctica y por qué

        // --- 2. FICHA DEL HÉROE ---
        System.out.println("\n+|=========> FICHA DEL HÉROE <=========|+");
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│ Nombre:      " + nombre);
        System.out.println("│ Dificultad:  " + dificultad);
        System.out.println("│ Salud:       " + vidaHeroe + " HP");
        System.out.println("│ Inventario:  " + pocionesHeroe + " poción(es)");
        System.out.println("└───────────────────────────────────────┘");
        System.out.println("*****************************************");

        // ***************** BLOQUE: Bucles (La Batalla Continúa)
        // **********************
        System.out.println("\n+|=========> ¡COMIENZA LA BATALLA REAL!<=========|+");

        // realizamos el bucle de la batalla
        // conocéis los snippets? Por ejemplo, el del bucle while o del switch
        while (vidaHeroe > 0 && estaVivo) {
            // nos aseguramos de que el enemigo sea distinto del anterior
            System.out.println("\nUn enemigo te ataca...");
            // Generamos tipo de enemigo aleatorio: 1 = Trasgo, 2 = Troll
            int enemigo = new Random().nextInt(2) + 1;

            // aquí, por lo que sea me he equivocado y he nombrado golpeEnemigo2 la
            // variable, en lugar de simplemente golpeEnemigo..
            // cómo puedo corregir este error sin tener que
            // reemplazar su nombre en cada uso?
            int golpeEnemigo2;

            switch (enemigo) {
                case 1:
                    // aquí varias cosas que comentar: primero si conocéis Math.max y Random,
                    // segundo,
                    // qué diferencia hay entre usar Math.random() y new Random().nextInt()?
                    // pues la diferencia es que Math.random() devuelve un double entre 0 y 1,
                    // mientras que new Random().nextInt() devuelve un int entre 0
                    // y el número que le pasemos como parámetro (exclusivo).
                    // Si no habéis Random, entonces usand mejor Math.random() y haciendo un casting
                    // a int, como en el codelab1.

                    // otra cosa es que esta línea de código es tan larga que he preferido seperarla
                    // en dos líneas,
                    // para que sea más legible.
                    // es una práctica habitual, aunque no del gusto de todos los programadores.

                    // Y por último... entendéis realmente qué estoy haciendo aquí?
                    // que al final es lo más importante ;)
                    golpeEnemigo2 = Math.max(new Random().nextInt(DANIO_MAX_TRASGO - DANIO_MIN_TRASGO + 1) +
                            DANIO_MIN_TRASGO, DANIO_MIN_TRASGO);
                    // esta línea gigante, en cuanto aprendáis a usar funciones, la podríamos
                    // encapsular en una función
                    // que se llame calcularGolpe(enemigo) y nos devuelva el valor del golpe en
                    // función del enemigo.
                    // y resolverlo de manera mucho más elegante
                    // y no digamos ya cuando manejéis clases... pero no nos adelantemos, que aún no
                    // hemos llegado a eso!

                    System.out.println("¡Un Trasgo te ataca!");
                    // podríaos resolver de forma más elegante este mensaje, ya que se repite luego
                    // en el otro case
                    // cómo?
                    break;
                // break; // qué pasa si no pongo el break aquí? lo sabéis?
                case 2:
                    golpeEnemigo2 = Math.max(new Random().nextInt(DANIO_MAX_TROLL - DANIO_MIN_TROLL + 1) +
                            DANIO_MIN_TROLL, DANIO_MIN_TROLL);
                    System.out.println("¡Un Troll te ataca!");
                    break;
                default:
                    golpeEnemigo2 = 0;
                    System.out.println("El enemigo huye!");
            }

            // Procesamos el daño realizado y lo mostranmos en pantalla
            vidaHeroe -= golpeEnemigo2;
            System.out.println("Recibes " + golpeEnemigo2 + " de daño. Vida actual: " + vidaHeroe);

            // Lógica de curación automática
            // si el héroe tiene aun pociones disponibles, usa una, y le cura la vida
            // si no quedan pociones lanza un warning por pantalla
            if (pocionesHeroe > 0) {
                vidaHeroe += CURACION;
                pocionesHeroe--;
                System.out.println("Usando poción!!! ");
                System.out.println("Pociones restantes: " + pocionesHeroe + " | Vida: " + vidaHeroe);
            } else {
                System.out.println("Ojo!! No quedan pociones!!!!");
            }

            // Comprobamos si el héroe ha caído
            // Si ha caído, rompemos el bucle y mostramos un mensaje deprimente de game over
            // Si no, mostramos su estado actual
            if (vidaHeroe <= 0) {
                estaVivo = false;
                System.out.println("Has muerto. Fin del juego.");
                // necesitamos romper el bucle aquí con un break, por ejemplo?
                // Si no, cuando se os ocurre que se pueda usar break;
            } else {
                System.out.println("Vida actual: " + vidaHeroe + " | Pociones restantes: " + pocionesHeroe);
            }

            // sabéis qué hace esto? (el Thread.sleep(1000); ) por qué lo usamos aquí?
            // qué me he dejado por hacer de buenas prácticas aquí?
            // "Magic..."
            System.out.println("------------------------------------------------");
            Thread.sleep(2000);

        }
        // qué diferencia entre un bucle while y un do/while? cuál usaríais en este caso
        // y por qué?
        // qué diferencia entre un bucle while y un for? cuál usaríais en este caso y
        // por qué?
        // sabéis el snippet para el bucle "for" en vs code? (Spoiler:fori)
        sc.close();
    }

}

/*
 * EJERCICIOS PARA LA PRÓXIMA SESIÓN:
 * 1. Dejaré el código tal cual lo tenía al inicio. Refactorizadlo: corregidlo y
 * mejoradlo,
 * aplicando buenas prácticas de programación y comentarios explicativos que
 * hayamos hablado en la sesión.
 * 2. Añadir un contador de enemigos derrotados y mostrarlo al final de la
 * partida.
 * 3. (OPCIONAL): Se os ocurre alguna otra mejora (dejar más vistosos los
 * mensajes de muerte o victoria, por ejemplo...)
 * PERO sin introducir elementos de programación que aún no hayáis visto
 * (clases, arrays, etc.)?
 * ...
 * MUCHAS GRACIAS POR VUESTRA ATENCIÓN Y PARTICIPACIÓN. ¡NOS VEMOS EN LA PRÓXIMA
 * SESIÓN!
 */
