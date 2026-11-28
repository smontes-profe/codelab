package modelo;

public class EntradaRanking {

    private final String jugador;
    private final String personaje;
    private final int enemigosDerrotados;

    public EntradaRanking(String jugador, String personaje, int enemigosDerrotados) {
        this.jugador = jugador;
        this.personaje = personaje;
        this.enemigosDerrotados = enemigosDerrotados;
    }

    public String getJugador() {
        return jugador;
    }

    public String getPersonaje() {
        return personaje;
    }

    public int getEnemigosDerrotados() {
        return enemigosDerrotados;
    }
}
