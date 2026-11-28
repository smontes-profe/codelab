package modelo;

/** Aplica las reglas de los turnos de un personaje contra un enemigo. */
public class Combate {

    private final Personaje personaje;
    private final Enemigo enemigo;
    private final RegistroJuego registro;

    /**
     * Prepara un combate entre los participantes indicados.
     *
     * @param personaje personaje que lucha
     * @param enemigo enemigo al que se enfrenta
     * @param registro destinatario de los mensajes del combate
     */
    public Combate(Personaje personaje, Enemigo enemigo, RegistroJuego registro) {
        this.personaje = personaje;
        this.enemigo = enemigo;
        this.registro = registro;
    }

    /** Ejecuta la fase a distancia; solo atacan quienes tienen esa habilidad. */
    public void turnoADistancia() {
        if (!personaje.atacarADistancia(enemigo)) {
            registro.jugador(" El guerrero espera: ¡solo puede luchar cuerpo a cuerpo!");
        }
        if (enemigo.estaVivo() && personaje.estaVivo()) {
            enemigo.atacarADistancia(personaje);
        }
    }

    /** Ejecuta los ataques cuerpo a cuerpo y la posible regeneración del enemigo. */
    public void turnoCuerpoACuerpo() {
        personaje.atacar(enemigo);
        if (enemigo.estaVivo()) {
            enemigo.hacerDanio(personaje);
        }
        if (enemigo.estaVivo()) {
            int vidaRecuperada = enemigo.regenerar();
            if (vidaRecuperada > 0) {
                registro.enemigo(" El " + enemigo.getNombre()
                        + " regenera " + vidaRecuperada + " puntos de vida.");
            }
        }
    }

    /** @return {@code true} si el personaje o el enemigo ha perdido toda su vida */
    public boolean haTerminado() {
        return !personaje.estaVivo() || !enemigo.estaVivo();
    }
}
