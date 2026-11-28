import java.util.Random;

public class Encuentro {

    private final Enemigo enemigo;
    private final boolean enemigoALejos;

    // Elegimos el enemigo y su distancia igual que en el juego de terminal.
    public Encuentro(Random random) {
        enemigo = random.nextBoolean() ? new Troll() : new Trasgo();
        enemigoALejos = random.nextBoolean();
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }

    public boolean isEnemigoALejos() {
        return enemigoALejos;
    }
}
