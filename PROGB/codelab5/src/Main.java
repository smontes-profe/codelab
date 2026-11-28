/*
 * Codelab 5 - GUI y empaquetado
 * Fecha: 17-12-2026
 * Autor: Sergio Montes
 * TODO
 */
import javax.swing.SwingUtilities;

public class Main {

    /**
     * Abre la ventana del juego en el hilo de eventos de Swing.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaJuego().setVisible(true));
    }
}

/*
 * Ejercicios propuestos:
 * TODO
 */
