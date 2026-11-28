/*
 * Codelab 5 - GUI
 * Fecha: 17-12-2026
 * Autor: Sergio Montes
 * GUI!
 */
import javax.swing.SwingUtilities;
import vista.VentanaJuego;

/** Punto de entrada de la aplicación. */
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
 * 1. Pasar unas buenas fiestas!
 * 2. Volver con energía el año que viene.
 */
