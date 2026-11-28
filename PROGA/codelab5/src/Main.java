
/*
 * Codelab 5 - POO y GUI
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
 * 1. Pasar unas buenas navidades!
 * 2. Volver con las baterías cargadas el año que viene!
*/