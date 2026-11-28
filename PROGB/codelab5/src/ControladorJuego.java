import java.util.Random;

public class ControladorJuego {

    // Guardamos los datos de la partida para usarlos en los próximos pasos.
    private final Random random = new Random();
    private String jugador;
    private Personaje personaje;
    private Encuentro encuentro;

    // Al crear otro personaje, comienza una nueva partida.
    public void iniciarPartida(String jugador, String nombrePersonaje, Profesion profesion) {
        this.jugador = jugador;
        this.personaje = profesion.crearPersonaje(nombrePersonaje);
        this.encuentro = null;
    }

    public String getJugador() {
        return jugador;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    // Conservamos el encuentro para poder continuar con él más adelante.
    public Encuentro prepararEncuentro() {
        if (personaje == null) {
            throw new IllegalStateException("Primero hay que crear un personaje.");
        }
        if (encuentro != null) {
            throw new IllegalStateException("Ya hay un encuentro en curso.");
        }
        encuentro = new Encuentro(random);
        return encuentro;
    }
}
