/*
 * Codelab 5 - GUI y empaquetado
 * Fecha: 17-12-2026
 * Autor: Sergio Montes
 * TODO
 */
import java.util.Scanner;

public class Main {

    /**
     * Inicia el juego y cierra la entrada estándar al terminar.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new GameLoop(scanner).run();
        }
    }
}

/*
 * Ejercicios propuestos:
 * TODO
 */
