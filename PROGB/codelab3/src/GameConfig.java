public final class GameConfig {

    // Con este Array controlamos el equipo inicial
    // al usar ENUMS para los posibles elementos de la mochila, 
    // es muy fácil cambiar el inventario de inicio
    public static final Objeto[] EQUIPO_INICIAL = {
        Objeto.POCION_CURACION,
        Objeto.POCION_CURACION
    };

    // de forma similar tengo otro Array para el posible loot 
    // (los elementos que sueltan los enemigos)
    public static final Objeto[] LOOT_POSIBLE = {
        Objeto.POCION_CURACION,
        Objeto.POCION_ATAQUE,
        Objeto.POCION_DEFENSA
    };

    // Y por último, otro Array para controlar las posibilidades
    // de que se suelte cada elemento en un random de 1 a 100:
    //  - De 1 a 50: Soltaría Objeto.POCION_CURACION (primer elemento de LOOT_POSIBLE)
    //  - De 51 a 75: Soltaría Objeto.POCION_ATAQUE
    //  - De 75 a 100: Soltaría Objeto.POCION_DEFENSA
    public static final int[] PROBABILIDADES_LOOT_ACUMULADAS = {50, 75, 100};

    // Estos 3 arrays no puede ver modificado su tamaño ni su contenido,
    // Dado que son constantes, tiene todo el sentido del mundo
    // haberlos creado como Arrays y no como ArrayList.
    
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

    private GameConfig() {
    }
}
