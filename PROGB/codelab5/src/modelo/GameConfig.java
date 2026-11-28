package modelo;

/** Valores compartidos para configurar las reglas y el ritmo del juego. */
public final class GameConfig {

    /** Objetos que recibe cada personaje al comenzar. */
    public static final Objeto[] EQUIPO_INICIAL = {
        Objeto.POCION_CURACION,
        Objeto.POCION_CURACION
    };

    /** Posibles objetos de botín, en el mismo orden que sus límites acumulados. */
    public static final Objeto[] LOOT_POSIBLE = {
        Objeto.POCION_CURACION,
        Objeto.POCION_ATAQUE,
        Objeto.POCION_DEFENSA
    };

    /** Límites de la tirada de 1 a 100 para cada objeto de botín. */
    public static final int[] PROBABILIDADES_LOOT_ACUMULADAS = {50, 75, 100};

    /** Puntos de vida que puede recuperar una poción de curación. */
    public static final int CURACION_POCION = 50;

    // Estadísticas iniciales de personajes y enemigos.
    public static final int GUERRERO_VIDA_MAXIMA = 100;
    public static final int GUERRERO_DANIO = 20;

    public static final int MAGO_VIDA_MAXIMA = 70;
    public static final int MAGO_DANIO = 30;
    public static final int MAGO_DANIO_MAGIA = 40;

    public static final int TRASGO_VIDA = 60;
    public static final int TRASGO_DANIO = 15;

    public static final int TROLL_VIDA = 100;
    public static final int TROLL_DANIO = 20;
    public static final int TROLL_REGENERACION = 5;

    /** Tiempo entre turnos automáticos, expresado en milisegundos. */
    public static final int TIEMPO_ESPERA = 1000;

    private GameConfig() {
    }
}
