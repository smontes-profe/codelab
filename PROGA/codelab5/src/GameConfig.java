import java.util.Random;

public class GameConfig {

    public static final String COLOR_INFORMATIVO = "\u001B[37m";
    public static final String COLOR_OPCIONES = "\u001B[33m";
    public static final String COLOR_JUGADOR = "\u001B[32m";
    public static final String COLOR_ENEMIGO = "\u001B[31m";
    public static final int TIEMPO_ESPERA = 1000;
    public static final int VIDA_MAXIMA = 100;
    public static final int CURACION_POCION = 50;
    public static final int DANIO_ESPADA = 25;
    public static final double DEFENSA_ESCUDO = 0.25;
    public static final int DANIO_TRASGO = 20;
    public static final int DANIO_TROLL = 30;
    public static final String NOMBRE_TROLL = "Troll";
    public static final int VIDA_TRASGO = 50;
    public static final int VIDA_TROLL = 80;
    public static final int ENEMIGO_TRASGO = 1;
    public static final String NOMBRE_TRASGO = "Trasgo";
    public static final String INVENTARIO_POCION = "Poción de curación";
    public static final String INVENTARIO_ESPADA = "Espada";
    public static final String INVENTARIO_ESCUDO = "Escudo";
    public static final String INVENTARIO_ANILLO_DEFENSA = "Anillo de defensa";
    public static final String INVENTARIO_ANILLO_ATAQUE = "Anillo de ataque";
    public static final double INCREMENTO_ANILLO_DEFENSA = 1.5;
    public static final double INCREMENTO_ANILLO_ATAQUE = 1.5;
    public static final String[] INVENTARIO_INICIAL = {INVENTARIO_POCION,INVENTARIO_POCION, INVENTARIO_ESPADA, INVENTARIO_ESCUDO};
    public static final String[] LOOT = {INVENTARIO_POCION, INVENTARIO_ANILLO_DEFENSA, INVENTARIO_ANILLO_ATAQUE};
    public static final int[] LOOT_PROBABILIDADES = {50, 75, 100}; 

    public static Random aleatorio;


    public GameConfig(){}
}
