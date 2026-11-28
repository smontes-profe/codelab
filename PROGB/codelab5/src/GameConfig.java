public final class GameConfig {

    public static final Objeto[] EQUIPO_INICIAL = {
        Objeto.POCION_CURACION,
        Objeto.POCION_CURACION
    };

    public static final Objeto[] LOOT_POSIBLE = {
        Objeto.POCION_CURACION,
        Objeto.POCION_ATAQUE,
        Objeto.POCION_DEFENSA
    };

    public static final int[] PROBABILIDADES_LOOT_ACUMULADAS = {50, 75, 100};

    public static final int CURACION_POCION = 50;

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

    public static final int TIEMPO_ESPERA = 1000;

    private GameConfig() {
    }
}
