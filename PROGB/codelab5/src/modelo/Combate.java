package modelo;

public class Combate {

    private final Personaje personaje;
    private final Enemigo enemigo;
    private final RegistroJuego registro;

    public Combate(Personaje personaje, Enemigo enemigo, RegistroJuego registro) {
        this.personaje = personaje;
        this.enemigo = enemigo;
        this.registro = registro;
    }

    // En la fase a distancia, solo atacan los que tienen esa habilidad.
    public void turnoADistancia() {
        if (!personaje.atacarADistancia(enemigo)) {
            registro.jugador(" El guerrero espera: ¡solo puede luchar cuerpo a cuerpo!");
        }
        if (enemigo.estaVivo() && personaje.estaVivo()) {
            enemigo.atacarADistancia(personaje);
        }
    }

    // Un turno cuerpo a cuerpo sigue el mismo orden que el juego original.
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

    public boolean haTerminado() {
        return !personaje.estaVivo() || !enemigo.estaVivo();
    }
}
