package modelo;

public class Mago extends Personaje {

    /**
     * Crea un mago con sus valores iniciales.
     *
     * @param nombre nombre del personaje
     * @param registro destino de los mensajes del juego
     */
    public Mago(String nombre, RegistroJuego registro) {
        super(nombre, Profesion.MAGO, GameConfig.MAGO_VIDA_MAXIMA, GameConfig.MAGO_DANIO, registro);
    }

    @Override
    /**
     * Lanza un conjuro al enemigo desde lejos.
     *
     * @param objetivo enemigo que recibe el conjuro
     * @return {@code true} porque el mago puede atacar a distancia
     */
    public boolean atacarADistancia(Enemigo objetivo) {
        usarMagia(objetivo);
        return true;
    }
    
    private void usarMagia(Enemigo objetivo) {
        int danioMagico = calcularDanio(GameConfig.MAGO_DANIO_MAGIA);
        registro.jugador(String.format(" %s lanza un conjuro contra %s y le hace %d de daño.",
                getNombre(), objetivo.getNombre(), danioMagico));
        objetivo.recibirDanio(danioMagico);
    }
}
