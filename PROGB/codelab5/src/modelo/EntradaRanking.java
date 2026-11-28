package modelo;

/** Datos de un resultado guardado en el ranking. */
public class EntradaRanking {

    private final String jugador;
    private final String personaje;
    private final int enemigosDerrotados;

    /**
     * Crea una entrada del ranking.
     *
     * @param jugador nombre del jugador
     * @param personaje nombre del personaje
     * @param enemigosDerrotados número de enemigos derrotados
     */
    public EntradaRanking(String jugador, String personaje, int enemigosDerrotados) {
        this.jugador = jugador;
        this.personaje = personaje;
        this.enemigosDerrotados = enemigosDerrotados;
    }

    /** @return nombre del jugador */
    public String getJugador() {
        return jugador;
    }

    /** @return nombre del personaje */
    public String getPersonaje() {
        return personaje;
    }

    /** @return número de enemigos derrotados */
    public int getEnemigosDerrotados() {
        return enemigosDerrotados;
    }
}
