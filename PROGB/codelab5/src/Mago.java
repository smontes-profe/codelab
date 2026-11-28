public class Mago extends Personaje {

    private static final String CLASE_STRING="Mago";

    /**
     * Crea un mago con sus valores iniciales.
     *
     * @param nombre nombre del personaje
     */
    public Mago(String nombre) {
        super(nombre, CLASE_STRING, GameConfig.MAGO_VIDA_MAXIMA, GameConfig.MAGO_DANIO);
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
        ColorTerminal.JUGADOR.imprimirFormato(" %s lanza un conjuro contra %s y le hace %d de daño.%n",
                getNombre(), objetivo.getNombre(), danioMagico);
        objetivo.recibirDanio(danioMagico);
    }
}
