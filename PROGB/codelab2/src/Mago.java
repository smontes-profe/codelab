public class Mago extends Personaje {

    private static final int VIDA_MAXIMA = 70;
    private static final int DANIO = 30;
    private static final int DANIO_MAGIA = 40;
    private static final String CLASE_STRING="Mago";

    public Mago(String nombre) {
        super(nombre,CLASE_STRING, VIDA_MAXIMA, DANIO);
    }

    @Override
    public boolean atacarADistancia(Enemigo objetivo) {
        usarMagia(objetivo);
        return true;
    }
    
    private void usarMagia(Enemigo objetivo) {
        System.out.printf(" %s lanza un conjuro contra %s y le hace %d de daño.%n",
                getNombre(), objetivo.getNombre(), DANIO_MAGIA);
        objetivo.recibirDanio(DANIO_MAGIA);
    }
}
