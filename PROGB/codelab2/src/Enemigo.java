public class Enemigo {

    private final TipoEnemigo tipo;

    public TipoEnemigo getTipo() {
        return tipo;
    }

    public Enemigo() {
        this.tipo = (Math.random() < 0.5) ? TipoEnemigo.TROLL : TipoEnemigo.TRASGO;
    }

    public void hacerDanio(Personaje objetivo) {
        int danio = tipo.getDanio();
        objetivo.recibirDanio(danio);
        System.out.printf("-------Un %s atacó a %s, que recibió %s-----%n", tipo, objetivo.getNombre(), danio);
    }
}
