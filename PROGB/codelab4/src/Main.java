/*
 * Codelab 4 - Functions & try/catch
 * Fecha: 25-11-2026
 * Autor: Sergio Montes
 * En el codelab de hoy vamos a aprovechar que ya deberíais controlar de BBDDD
 * Vamos a crear una BBDD "codelabs" con una tabla única "ranking"
 * con estos campos: jugador, héroe, enemigos_derrotados
 * Al comenzar una partida, si hay algún registro de ranking, mostramos el top10
 * Y cuando termine una partida, antes de preguntar si reinicia partida,
 * si el héroe sobrevivió, actualizamos la tabla ranking con el record de esa partida. 
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
 * - TODO
 */
