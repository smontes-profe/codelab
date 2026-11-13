public class Trasgo extends Enemigo {
    private static final String NOMBRE_STR = "Trasgo";

    /** Crea un Trasgo con sus valores iniciales. */
    public Trasgo() {
        super(NOMBRE_STR, GameConfig.TRASGO_VIDA, GameConfig.TRASGO_DANIO);
    }

    @Override
    /**
     * Lanza una piedra al personaje desde lejos.
     *
     * @param objetivo personaje que recibe el ataque
     */
    public void atacarADistancia(Personaje objetivo) {
        ColorTerminal.ENEMIGO.imprimirFormato(" ¡El %s lanza una piedra a %s y le hace %d de daño!%n",
                getNombre(), objetivo.getNombre(), GameConfig.TRASGO_DANIO);
        objetivo.recibirDanio(GameConfig.TRASGO_DANIO);
    }
}
