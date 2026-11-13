public class Guerrero extends Personaje {

    private static final String CLASE_STRING="Guerrero";

    /**
     * Crea un guerrero con sus valores iniciales.
     *
     * @param nombre nombre del personaje
     */
    public Guerrero(String nombre) {
        super(nombre, CLASE_STRING, GameConfig.GUERRERO_VIDA_MAXIMA, GameConfig.GUERRERO_DANIO);
    }

    @Override
    protected int reducirDanioPorClase(int cantidad) {
        int danioReducido = cantidad > 0 ? (cantidad + 1) / 2 : cantidad;
        if (cantidad > 0) {
            ColorTerminal.JUGADOR.imprimirFormato(" El escudo reduce el daño de %d a %d.%n", cantidad, danioReducido);
        }
        return danioReducido;
    }
}
