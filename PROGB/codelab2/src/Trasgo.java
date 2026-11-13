public class Trasgo extends Enemigo {
    private static final int VIDA = 50;
    private static final int DANIO = 10;
    private static final String NOMBRE_STR = "Trasgo";

    public Trasgo() {
        super(NOMBRE_STR, VIDA, DANIO);
    }

    @Override
    public void atacarADistancia(Personaje objetivo) {
        System.out.printf(" ¡El %s lanza una piedra a %s y le hace %d de daño!%n",
                getNombre(), objetivo.getNombre(), DANIO);
        objetivo.recibirDanio(DANIO);
    }
}
