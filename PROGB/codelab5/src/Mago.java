public class Mago extends Personaje {

    /**
     * Crea un mago con sus valores iniciales.
     *
     * @param nombre nombre del personaje
     */
    public Mago(String nombre) {
        super(nombre, Profesion.MAGO, GameConfig.MAGO_VIDA_MAXIMA, GameConfig.MAGO_DANIO);
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
