package modelo;

/** Personaje que reduce el daño recibido con su escudo. */
public class Guerrero extends Personaje {

    /**
     * Crea un guerrero con sus valores iniciales.
     *
     * @param nombre nombre del personaje
     * @param registro destino de los mensajes del juego
     */
    public Guerrero(String nombre, RegistroJuego registro) {
        super(nombre, Profesion.GUERRERO, GameConfig.GUERRERO_VIDA_MAXIMA, GameConfig.GUERRERO_DANIO, registro);
    }

    @Override
    protected int reducirDanioPorClase(int cantidad) {
        // El escudo reduce el daño a la mitad, redondeando hacia arriba.
        int danioReducido = cantidad > 0 ? (cantidad + 1) / 2 : cantidad;
        if (cantidad > 0) {
            registro.jugador(String.format(" El escudo reduce el daño de %d a %d.", cantidad, danioReducido));
        }
        return danioReducido;
    }
}
