package modelo;

import java.util.function.Consumer;

/**
 * Envía los mensajes del juego a los destinos proporcionados por la vista.
 * Los {@link Consumer} reciben un texto y deciden cómo mostrarlo.
 */
public class RegistroJuego {

    private final Consumer<String> jugador;
    private final Consumer<String> enemigo;
    private final Consumer<String> informacion;

    /**
     * Recibe un destino para cada tipo de mensaje.
     *
     * @param jugador destino de los mensajes del personaje
     * @param enemigo destino de los mensajes del enemigo
     * @param informacion destino de los mensajes informativos
     */
    public RegistroJuego(Consumer<String> jugador, Consumer<String> enemigo, Consumer<String> informacion) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.informacion = informacion;
    }

    /**
     * Envía un mensaje del personaje.
     *
     * @param mensaje texto que se va a mostrar
     */
    public void jugador(String mensaje) {
        jugador.accept(mensaje);
    }

    /**
     * Envía un mensaje del enemigo.
     *
     * @param mensaje texto que se va a mostrar
     */
    public void enemigo(String mensaje) {
        enemigo.accept(mensaje);
    }

    /**
     * Envía un mensaje informativo.
     *
     * @param mensaje texto que se va a mostrar
     */
    public void informacion(String mensaje) {
        informacion.accept(mensaje);
    }
}
