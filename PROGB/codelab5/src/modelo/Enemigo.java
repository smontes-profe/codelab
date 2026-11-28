package modelo;

import java.util.Objects;

/** Datos y acciones comunes de los enemigos de la aventura. */
public abstract class Enemigo {

    private final String nombre;
    private final int danio;
    private final int vidaMaxima;
    private int vida;
    /** Destino de los mensajes que también utilizan las clases hijas. */
    protected final RegistroJuego registro;

    /** @return el nombre del enemigo */
    public String getNombre() { return nombre; }

    /** @return {@code true} si el enemigo conserva puntos de vida */
    public boolean estaVivo() { return vida > 0; }

    /**
     * Inicializa los datos comunes de un enemigo.
     *
     * @param nombre nombre del enemigo
     * @param vida vida inicial y máxima
     * @param danio daño de sus ataques cuerpo a cuerpo
     * @param registro destinatario de sus mensajes
     */
    protected Enemigo(String nombre, int vida, int danio, RegistroJuego registro) {
        this.nombre = nombre;
        this.registro = Objects.requireNonNull(registro, "El registro del juego es obligatorio.");
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

    /**
     * Recupera vida sin superar el máximo; un enemigo derrotado no se regenera.
     *
     * @param cantidad cantidad que se intenta recuperar
     * @return cantidad de vida realmente recuperada
     */
    protected int recuperarVida(int cantidad) {
        if (cantidad <= 0 || !estaVivo()) {
            return 0;
        }
        int vidaAnterior = vida;
        vida = Math.min(vidaMaxima, vida + cantidad);
        return vida - vidaAnterior;
    }

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
        registro.enemigo(String.format(" ¡El %s ataca a %s y le hace %d de daño!",
                nombre, objetivo.getNombre(), danio));
        objetivo.recibirDanio(danio);
    }
}
