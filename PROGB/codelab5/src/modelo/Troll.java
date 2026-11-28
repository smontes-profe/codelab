package modelo;

/** Enemigo que puede recuperar vida después de cada turno cuerpo a cuerpo. */
public class Troll extends Enemigo {

    private static final String NOMBRE_STR = "Troll";

    /**
     * Crea un troll con sus valores iniciales.
     *
     * @param registro destinatario de sus mensajes
     */
    public Troll(RegistroJuego registro) {
        super(NOMBRE_STR, GameConfig.TROLL_VIDA, GameConfig.TROLL_DANIO, registro);
    }

    /**
     * Regenera la cantidad de vida propia definida para el Troll.
     *
     * @return cantidad de vida recuperada
     */
    @Override
    public int regenerar() {
        return recuperarVida(GameConfig.TROLL_REGENERACION);
    }
}
