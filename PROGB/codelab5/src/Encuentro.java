import java.util.Random;

public class Encuentro {

    private final Enemigo enemigo;
    private final boolean enemigoALejos;

    // Elegimos al azar el enemigo y su distancia.
    public Encuentro(Random random, RegistroJuego registro) {
        enemigo = random.nextBoolean() ? new Troll(registro) : new Trasgo(registro);
        enemigoALejos = random.nextBoolean();
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }

    public boolean isEnemigoALejos() {
        return enemigoALejos;
    }
}
