public class Troll extends Enemigo {

    private static final String NOMBRE_STR = "Troll";

    /** Crea un Troll con sus valores iniciales. */
    public Troll() {
        super(NOMBRE_STR, GameConfig.TROLL_VIDA, GameConfig.TROLL_DANIO);
    }

    @Override
    /**
     * Regenera la cantidad de vida propia definida para el Troll.
     *
     * @return cantidad de vida recuperada
     */
    public int regenerar() {
        return recuperarVida(GameConfig.TROLL_REGENERACION);
    }
}
