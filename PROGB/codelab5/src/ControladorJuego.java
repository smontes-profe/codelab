import java.util.Random;

public class ControladorJuego {

    // Guardamos los datos de la partida para usarlos en los próximos pasos.
    private final Random random = new Random();
    private final RegistroJuego registro;
    private String jugador;
    private Personaje personaje;
    private Encuentro encuentro;
    private Combate combate;
    private boolean objetoElegido;
    private boolean turnoDistanciaPendiente;
    private boolean combateTerminado;

    public ControladorJuego(RegistroJuego registro) {
        this.registro = registro;
    }

    // Al crear otro personaje, comienza una nueva partida.
    public void iniciarPartida(String jugador, String nombrePersonaje, Profesion profesion) {
        this.jugador = jugador;
        this.personaje = profesion.crearPersonaje(nombrePersonaje);
        this.encuentro = null;
        this.combate = null;
        this.objetoElegido = false;
        this.turnoDistanciaPendiente = false;
        this.combateTerminado = false;
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
        combate = new Combate(personaje, encuentro.getEnemigo(), registro);
        turnoDistanciaPendiente = encuentro.isEnemigoALejos();
        return encuentro;
    }

    // El índice -1 representa la decisión de no usar ningún objeto.
    public String elegirObjeto(int indice) {
        if (encuentro == null || objetoElegido) {
            throw new IllegalStateException("No se puede elegir un objeto en este momento.");
        }
        if (indice < -1 || indice >= personaje.getCantidadObjetos()) {
            throw new IllegalArgumentException("La posición del objeto no es válida.");
        }
        String mensaje = indice == -1 ? "No usas ningún objeto." : personaje.usarObjeto(indice);
        objetoElegido = true;
        return mensaje;
    }

    // Ejecutamos un turno cada vez, para que la ventana pueda actualizarse.
    public boolean avanzarCombate() {
        if (combate == null || !objetoElegido || combateTerminado) {
            throw new IllegalStateException("No se puede avanzar el combate en este momento.");
        }

        if (turnoDistanciaPendiente) {
            combate.turnoADistancia();
            turnoDistanciaPendiente = false;
            if (!combate.haTerminado()) {
                registro.informacion(" El enemigo se acerca... ¡ahora está cuerpo a cuerpo!");
            }
        } else {
            combate.turnoCuerpoACuerpo();
        }

        if (combate.haTerminado()) {
            combateTerminado = true;
            if (!personaje.estaVivo()) {
                personaje.finalizarEfectosDeEncuentro();
                registro.informacion("Fin de la aventura.");
            } else {
                registro.jugador(" ¡Has derrotado al " + encuentro.getEnemigo().getNombre() + "!");
                personaje.finalizarEfectosDeEncuentro();
                registro.informacion("El botín y la continuación estarán disponibles en el siguiente paso.");
            }
            return false;
        }
        return true;
    }
}
