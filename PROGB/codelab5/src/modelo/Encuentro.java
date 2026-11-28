package modelo;

import java.util.Random;

/** Conserva el enemigo y la distancia inicial de un encuentro. */
public class Encuentro {

    private final Enemigo enemigo;
    private final boolean enemigoALejos;

    /**
     * Elige al azar el tipo de enemigo y si aparece lejos.
     *
     * @param random generador usado para las elecciones
     * @param registro destinatario de los mensajes del enemigo
     */
    public Encuentro(Random random, RegistroJuego registro) {
        enemigo = random.nextBoolean() ? new Troll(registro) : new Trasgo(registro);
        enemigoALejos = random.nextBoolean();
    }

    /** @return enemigo del encuentro */
    public Enemigo getEnemigo() {
        return enemigo;
    }

    /** @return {@code true} si el enemigo apareció a distancia */
    public boolean isEnemigoALejos() {
        return enemigoALejos;
    }
}
