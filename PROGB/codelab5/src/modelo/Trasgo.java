package modelo;

/** Enemigo que puede lanzar una piedra a distancia. */
public class Trasgo extends Enemigo {
    private static final String NOMBRE_STR = "Trasgo";

    /**
     * Crea un trasgo con sus valores iniciales.
     *
     * @param registro destinatario de sus mensajes
     */
    public Trasgo(RegistroJuego registro) {
        super(NOMBRE_STR, GameConfig.TRASGO_VIDA, GameConfig.TRASGO_DANIO, registro);
    }

    /**
     * Lanza una piedra al personaje desde lejos.
     *
     * @param objetivo personaje que recibe el ataque
     */
    @Override
    public void atacarADistancia(Personaje objetivo) {
        registro.enemigo(String.format(" ¡El %s lanza una piedra a %s y le hace %d de daño!",
                getNombre(), objetivo.getNombre(), GameConfig.TRASGO_DANIO));
        objetivo.recibirDanio(GameConfig.TRASGO_DANIO);
    }
}
