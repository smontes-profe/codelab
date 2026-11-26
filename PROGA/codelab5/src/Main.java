
/*
 * Codelab 5 - Functions & try/catch
 * Fecha: 25-11-2026
 * Autor: Sergio Montes
 * POO y GUI
 */

import javax.swing.SwingUtilities;

public class Main {
    /**
     * Inicia el juego en el hilo de eventos de Swing.
     * @param args argumentos de inicio; el juego no los utiliza
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameEngine());
    }
}

/*
 * Ejercicios propuestos:
 * Pasar unas buenas navidades!
*/