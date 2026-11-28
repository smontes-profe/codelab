package modelo;

import java.util.ArrayList;
import java.util.Objects;

/** Datos y acciones comunes de los personajes que controlan los jugadores. */
public abstract class Personaje {

    private final String nombre;
    private final Profesion profesion;
    private final int vidaMaxima;
    private final int danio;
    private int vida;
    private final ArrayList<Objeto> mochila;
    private boolean pocionAtaqueActiva;
    private boolean pocionDefensaActiva;
    /** Destino de los mensajes que también utilizan las clases hijas. */
    protected final RegistroJuego registro;

    /** @return los puntos de vida actuales */
    public int getVida() { return vida; }

    /** @return los puntos de vida máximos */
    public int getVidaMaxima() { return vidaMaxima; }

    /** @return el daño base de los ataques cuerpo a cuerpo */
    public int getDanio() { return danio; }

    /** @return el nombre visible de la profesión del personaje */
    public String getProfesion() { return profesion.toString(); }

    /** @return el nombre del personaje */
    public String getNombre() { return nombre; }

    /** @return la cantidad de objetos que hay en la mochila */
    public int getCantidadObjetos() { return mochila.size(); }

    /**
     * Devuelve el objeto situado en la posición indicada de la mochila.
     *
     * @param indice posición del objeto, empezando en cero
     * @return el objeto de esa posición
     */
    public Objeto getObjetoMochila(int indice) { return mochila.get(indice); }

    /**
     * Inicializa los datos comunes y copia el equipo inicial en la mochila.
     *
     * @param nombre nombre del personaje
     * @param miProfesion profesión del personaje
     * @param vidaMaxima vida inicial y máxima
     * @param danio daño base cuerpo a cuerpo
     * @param registro destinatario de los mensajes
     */
    protected Personaje(String nombre, Profesion miProfesion, int vidaMaxima, int danio, RegistroJuego registro) {
        this.nombre = nombre;
        this.profesion = miProfesion;
        this.registro = Objects.requireNonNull(registro, "El registro del juego es obligatorio.");
        this.vidaMaxima = vidaMaxima;
        this.danio = danio;
        this.vida = vidaMaxima;
        this.mochila = new ArrayList<>();
        for (Objeto objeto : GameConfig.EQUIPO_INICIAL) {
            mochila.add(objeto);
        }
    }

    /**
     * Ataca al enemigo indicado y aplica el daño correspondiente.
     *
     * @param objetivo enemigo que recibe el ataque
     */
    public void atacar(Enemigo objetivo) {
        int danioRealizado = calcularDanio(danio);
        registro.jugador(String.format(" %s golpea a %s y le hace %d de daño.",
                nombre, objetivo.getNombre(), danioRealizado));
        objetivo.recibirDanio(danioRealizado);
    }

    /**
     * Intenta atacar al enemigo desde lejos; el personaje base no tiene esa habilidad.
     *
     * @param objetivo enemigo que podría recibir el ataque
     * @return {@code true} si se realizó un ataque a distancia
     */
    public boolean atacarADistancia(Enemigo objetivo) {
        return false;
    }

    /**
     * Reduce la vida aplicando los efectos defensivos activos.
     *
     * @param cantidad cantidad de daño recibido
     */
    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            int danioRecibido = cantidad;
            if (pocionDefensaActiva) {
                danioRecibido = (danioRecibido + 1) / 2;
                registro.jugador(String.format(
                        " La poción de defensa reduce el daño de %d a %d.", cantidad, danioRecibido));
            }
            danioRecibido = reducirDanioPorClase(danioRecibido);
            vida = Math.max(0, vida - danioRecibido);
        }
    }

    /**
     * Permite que una clase reduzca el daño que recibe.
     *
     * @param cantidad daño después de aplicar la poción de defensa
     * @return daño tras aplicar la defensa propia de la clase
     */
    protected int reducirDanioPorClase(int cantidad) {
        return cantidad;
    }

    /**
     * Aplica al daño base la posible poción de ataque.
     *
     * @param danioBase daño antes de aplicar la poción
     * @return daño que se hará al enemigo
     */
    protected int calcularDanio(int danioBase) {
        if (!pocionAtaqueActiva) {
            return danioBase;
        }
        int danioAumentado = danioBase * 2;
        registro.jugador(String.format(
                " La poción de ataque duplica el daño de %d a %d.", danioBase, danioAumentado));
        return danioAumentado;
    }

    /**
     * Añade un objeto a la mochila.
     *
     * @param objeto objeto que se incorpora al inventario
     */
    public void agregarObjeto(Objeto objeto) {
        mochila.add(objeto);
    }

    /**
     * Usa el objeto de la posición indicada y lo retira si se puede utilizar.
     * La poción de curación no se consume si la vida ya está al máximo.
     *
     * @param indice posición del objeto, empezando en cero
     * @return mensaje que describe el resultado
     */
    public String usarObjeto(int indice) {

        Objeto objeto = mochila.get(indice);
        if (objeto == Objeto.POCION_CURACION && vida == vidaMaxima) {
            return " Ya tienes la vida al máximo; la poción se queda en la mochila.";
        }
        mochila.remove(indice);
        // Devolvemos el mensaje para que la ventana pueda mostrarlo.
        String mensaje = " Usas " + objeto.getNombre() + ".\n";

        if (objeto == Objeto.POCION_CURACION) {
            int vidaAnterior = vida;
            vida = Math.min(vidaMaxima, vida + GameConfig.CURACION_POCION);
            int vidaRecuperada = vida - vidaAnterior;
            return mensaje + " Recuperas " + vidaRecuperada + " puntos de vida.";
        }
        if (objeto == Objeto.POCION_ATAQUE) {
            pocionAtaqueActiva = true;
            return mensaje + " Tus ataques harán el doble de daño durante este encuentro.";
        }
        if (objeto == Objeto.POCION_DEFENSA) {
            pocionDefensaActiva = true;
            return mensaje + " Recibirás la mitad de daño durante este encuentro.";
        }
        throw new IllegalStateException("Intentando usar un objeto de la mochila que no existe");
    }

    /** Termina y anuncia los efectos temporales activos del encuentro. */
    public void finalizarEfectosDeEncuentro() {

        if (pocionAtaqueActiva) {
            registro.informacion(" El efecto de la poción de ataque ha terminado.");
            pocionAtaqueActiva = false;
        }
        if (pocionDefensaActiva) {
            registro.informacion(" El efecto de la poción de defensa ha terminado.");
            pocionDefensaActiva = false;
        }
    }

    /** @return {@code true} si el personaje conserva puntos de vida */
    public boolean estaVivo() {
        return vida > 0;
    }
}
