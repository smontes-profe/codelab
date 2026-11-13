/*
 * Cambios principales del juego:
 * - Vamos a dejar todo más limpio y con buenas prácticas: 
 *      -Main queda como punto de entrada
 *      -GameLoop gestiona el flujo de las partidas.
 *      -GameConfig contentrá todos los parámetros del juego.
 *      -Añadimos JavaDoc a los métodos públicos.
 * - Los mensajes de terminal usan colores según su tipo y pasan a gestionarse con ColorTerminal
 * - El combate se resuelve automáticamente, con pausas entre turnos.
 * - La aventura continúa sin límite de encuentros hasta la derrota o la retirada.
 * - Vamos a centrarnos como "features" nuevas en Array y AarrayList, mediante la "mochila".
 * - La mochila usa un ArrayList (puede variar su tamaño y contenido en el tiempo);
 * - El equipo inicial y el loot posible se definen en arrays (son fijas y no cambian su tamaño).
 * - Se puede usar un objeto de la mochila por encuentro: curación, ataque o defensa.
 * - Los enemigos dejan loot y los efectos temporales terminan al acabar el encuentro.
 */
import java.util.Scanner;

public class Main {

    /**
     * Inicia el juego y cierra la entrada estándar al terminar.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     * @throws InterruptedException si se interrumpe una pausa durante el juego
     */
    public static void main(String[] args) throws InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            new GameLoop(scanner).run();
        }
    }
}

/*
 * Ejercicios propuestos:
 * - Modificar el equipo inicial para que sea una poción de curación y otra de ataque,
 *    en lugar de dos de curación.
 * - Añadir un nuevo tipo de loot: anillo de resurrección.
 * - Asignarle una probabilidad de un 5%, 
 *    y las pociones pasarían a tener un 45% en lugar de un 50%
 * - Efectos del anillo:
 *    Si el usuario lo activa en un encuentro, 
 *    y su personaje muere, el anillo lo resucita automáticamente, 
 *    pero es destruido.
 */
