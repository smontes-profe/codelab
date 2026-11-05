public class Enemigo {

    // El atributo tipo será de la nueva clase Enum que acabamos de crear
    // Por eso ahora al definirlo, nos sale como una clase más en el autocompletado
    private final TipoEnemigo tipo;

    // Probablemente necesite acceder al tipo de enemigo en mi instancia
    // así que es buena idea hacer un getter.
    // Sin embargo, no voy a querer que se pueda cambiar, por lo que no creo el
    // setter
    public TipoEnemigo getTipo() {
        return tipo;
    }

    public Enemigo() {
        // Determinamos el tipo al azar entre los valores del enum (50% de probabilidad)
        // si tuviéramos un parámetro de dificultad, sería fácil modificar el tipo de
        // enemigo
        // variando la probabilidad en función de la dificultad, no?
        this.tipo = (Math.random() < 0.5) ? TipoEnemigo.TROLL : TipoEnemigo.TRASGO;
    }

    // Siguiendo el enunciado del ejercicio: recibe un Personaje y le aplica el daño
    // correspondiente
    /**
     * Hace daño al personaje objetivo según el tipo de enemigo.
     * @param objetivo el personaje que recibirá el daño
     */
    public void hacerDanio(Personaje objetivo) {
        int danio = tipo.getDanio();
        objetivo.recibirDanio(danio);
        // lo típico para mostrar por pantalla el daño sería:
        // System.out.println("-------Un " + tipo + " atacó a " + objetivo.getNombre() +
        //  ". que recibió " + danio + "-----");
        // pero podemos hacerlo también así
        System.out.printf("-------Un %s atacó a %s, que recibió %s-----%n", tipo, objetivo.getNombre(), danio);
    }
}
