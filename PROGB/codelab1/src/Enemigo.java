public class Enemigo {

    private final TipoEnemigo tipo;

    public Enemigo() {
        // Determinamos el tipo al azar entre los valores del enum (50% de probabilidad)
        this.tipo = (Math.random() < 0.5) ? TipoEnemigo.TROLL : TipoEnemigo.TRASGO;
    }

    public TipoEnemigo getTipo() {
        return tipo;
    }

    // Siguiendo el enunciado: recibe un Personaje y le aplica el daño correspondiente
    public void hacerDanio(Personaje objetivo) {
        objetivo.recibirDanio(tipo.getDanio());
    }
}
