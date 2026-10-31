public class Mago extends Personaje {

    private static final int VIDA_MAXIMA = 70;
    private static final int DANIO = 30;
    private static final int DANIO_MAGIA = 40;
    private static final String CLASE_STRING="Mago";

    public Mago(String nombre) {
        super(nombre,CLASE_STRING, VIDA_MAXIMA, DANIO);
    }

    //De forma similar al Guerrero, sobreescribimos la clase atacar a distancia
    @Override
    public boolean atacarADistancia(Enemigo objetivo) {
        // Pero a diferencia, del Guerrero, aquí lo resolvemos de otra forma
        //en lugar de llamar a super, es más facil llamar a una propia, ya que super no hace nada
        usarMagia(objetivo);
        return true;
    }
    
    //Y aquí aplicamos el daño de la magia con un mensajito dando ambientación
    public void usarMagia(Enemigo objetivo) {
        System.out.printf("✨ %s lanza un conjuro contra %s y le hace %d de daño.%n",
                getNombre(), objetivo.getNombre(), DANIO_MAGIA);
        objetivo.recibirDanio(DANIO_MAGIA);
    }
}
