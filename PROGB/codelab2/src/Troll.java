public class Troll extends Enemigo {

    private static final int VIDA = 80;
    private static final int DANIO = 20;
    private static final int REGENERACION = 5;
    private static final String NOMBRE_STR = "Troll";

    public Troll() {
        super(NOMBRE_STR, VIDA, DANIO);
    }

    @Override
    public int regenerar() {
        return recuperarVida(REGENERACION);
    }
}
