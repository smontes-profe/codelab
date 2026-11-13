public abstract class Enemigo {

    private final String nombre;
    private final int danio;
    private final int vidaMaxima;
    private int vida;

    public String getNombre() { return nombre; }
    public boolean estaVivo() { return vida > 0; }

    protected Enemigo(String nombre, int vida, int danio) {
        this.nombre = nombre;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.danio = danio;
    }  

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
    public void atacarADistancia(Personaje objetivo) {
    }

    public int regenerar() {
        return 0;
    }

    public void hacerDanio(Personaje objetivo) {
        objetivo.recibirDanio(danio);
        System.out.printf(" ¡El %s ataca a %s y le hace %d de daño!%n",
                nombre, objetivo.getNombre(), danio);
    }
}
