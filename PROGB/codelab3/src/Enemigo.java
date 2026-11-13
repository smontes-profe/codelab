public abstract class Enemigo {

    private final String nombre;
    private final int danio;
    private final int vidaMaxima;
    private int vida;

    /** @return el nombre del enemigo */
    public String getNombre() { return nombre; }

    /** @return {@code true} si el enemigo conserva puntos de vida */
    public boolean estaVivo() { return vida > 0; }

    protected Enemigo(String nombre, int vida, int danio) {
        this.nombre = nombre;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.danio = danio;
    }  

    /**
     * Reduce la vida del enemigo por la cantidad de daño recibida.
     *
     * @param cantidad cantidad de daño
     */
    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    protected int recuperarVida(int cantidad) {
        if (cantidad <= 0 || !estaVivo()) {
            return 0;
        }
        int vidaAnterior = vida;
        vida = Math.min(vidaMaxima, vida + cantidad);
        return vida - vidaAnterior;
    }

    // Por defecto, los enemigos no tienen estas habilidades.
    /**
     * Intenta atacar a un personaje desde lejos; por defecto no hace nada.
     *
     * @param objetivo personaje al que se dirigiría el ataque
     */
    public void atacarADistancia(Personaje objetivo) {
    }

    /**
     * Regenera vida al final de un turno; por defecto no regenera.
     *
     * @return cantidad de vida recuperada
     */
    public int regenerar() {
        return 0;
    }

    /**
     * Ataca al personaje indicado y le aplica el daño del enemigo.
     *
     * @param objetivo personaje que recibe el ataque
     */
    public void hacerDanio(Personaje objetivo) {
        ColorTerminal.ENEMIGO.imprimirFormato(" ¡El %s ataca a %s y le hace %d de daño!%n",
                nombre, objetivo.getNombre(), danio);
        objetivo.recibirDanio(danio);
    }
}
