import java.util.function.Consumer;

public class RegistroJuego {

    private final Consumer<String> jugador;
    private final Consumer<String> enemigo;
    private final Consumer<String> informacion;

    // Por defecto, los mensajes se muestran en la terminal con sus colores.
    public RegistroJuego() {
        jugador = ColorTerminal.JUGADOR::imprimirLinea;
        enemigo = ColorTerminal.ENEMIGO::imprimirLinea;
        informacion = ColorTerminal.INFORMACION::imprimirLinea;
    }

    // La ventana puede mostrar cada tipo de mensaje con su propio estilo.
    public RegistroJuego(Consumer<String> jugador, Consumer<String> enemigo, Consumer<String> informacion) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.informacion = informacion;
    }

    public void jugador(String mensaje) {
        jugador.accept(mensaje);
    }

    public void enemigo(String mensaje) {
        enemigo.accept(mensaje);
    }

    public void informacion(String mensaje) {
        informacion.accept(mensaje);
    }
}
