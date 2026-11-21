import java.util.ArrayList;

public abstract class Personaje {

    private final String nombre;
    private final String profesion;
    private final int vidaMaxima;
    private final int danio;
    private int vida;
    //introducimos aquí la mochila como ArrayList
    private final ArrayList<Objeto> mochila;
    // variable para controlar si el personaje bebió una poción de ataque al comienzo del turno
    private boolean pocionAtaqueActiva;
    // lo mismo para la de defensa
    private boolean pocionDefensaActiva;

    /** @return los puntos de vida actuales */
    public int getVida() { return vida; }

    /** @return la profesión del personaje */
    public String getProfesion() { return profesion; }

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

    protected Personaje(String nombre, String miProfesion,  int vidaMaxima, int danio) {
        this.nombre = nombre;
        this.profesion = miProfesion;
        this.vidaMaxima = vidaMaxima;
        this.danio = danio;
        this.vida = vidaMaxima;
        this.mochila = new ArrayList<>();
        // recorremos el array de equipo inicial que tenemos en GameConfig
        // y añadimos los elementos a la mochila
        for (Objeto objeto : GameConfig.EQUIPO_INICIAL) {
            mochila.add(objeto);
        }
        // se podría haber hecho también así:
        // this.mochila = new ArrayList<>(Arrays.asList(GameConfig.EQUIPO_INICIAL));
        // pero me parece más confuso y tal como está practicáis cómo recorrer el array
    }

    /** Muestra por terminal la ficha y el inventario del personaje. */
    public void mostrarFicha() {
        ColorTerminal.FICHA.imprimirLinea("┌──────────────  FICHA DEL HÉROE ──────────────┐");
        ColorTerminal.FICHA.imprimirLinea("   " + nombre + " — " + profesion);
        ColorTerminal.FICHA.imprimirLinea("    Vida: " + vida + "/" + vidaMaxima);
        ColorTerminal.FICHA.imprimirLinea("   Mochila:");
        if (mochila.isEmpty()) {
            ColorTerminal.FICHA.imprimirLinea("    (vacía)");
        } else {
            for (int i = 0; i < mochila.size(); i++) {
                ColorTerminal.FICHA.imprimirLinea("    " + (i + 1) + ". " + mochila.get(i).getNombre());
            }
        }
        ColorTerminal.FICHA.imprimirLinea("└────────────────────────────────────────────────┘");
        System.out.println();
    }
    
    /**
     * Ataca al enemigo indicado y aplica el daño correspondiente.
     *
     * @param objetivo enemigo que recibe el ataque
     */
    public void atacar(Enemigo objetivo) {
        int danioRealizado = calcularDanio(danio);
        ColorTerminal.JUGADOR.imprimirFormato(" %s golpea a %s y le hace %d de daño.%n",
                nombre, objetivo.getNombre(), danioRealizado);
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
                ColorTerminal.JUGADOR.imprimirFormato(
                        " La poción de defensa reduce el daño de %d a %d.%n", cantidad, danioRecibido);
            }
            danioRecibido = reducirDanioPorClase(danioRecibido);
            vida = Math.max(0, vida - danioRecibido);
        }
    }

    protected int reducirDanioPorClase(int cantidad) {
        return cantidad;
    }

    protected int calcularDanio(int danioBase) {
        if (!pocionAtaqueActiva) {
            return danioBase;
        }
        int danioAumentado = danioBase * 2;
        ColorTerminal.JUGADOR.imprimirFormato(
                " La poción de ataque duplica el daño de %d a %d.%n", danioBase, danioAumentado);
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
     * Usa y retira de la mochila el objeto de la posición indicada.
     *
     * @param indice posición del objeto, empezando en cero
     */
    public void usarObjeto(int indice) {

        // Éste es el método que tiene más chicha
        // Localizamos el objeto gracias a la propiedad indice
        Objeto objeto = mochila.get(indice);
        //Si el objeto es una opción de curación, pero la vida está al máximo, pues:
        if (objeto == Objeto.POCION_CURACION && vida == vidaMaxima) {
            ColorTerminal.JUGADOR.imprimirLinea(" Ya tienes la vida al máximo; la poción se queda en la mochila.");
            return;
        }
        // Como hacemos return en el if, no tenemos que hacer else y así 
        // evitamos un if else if else encadenado de la muerte

        // Eliminamos el objeto usando su índice usando el método remove de los ArrayList
        mochila.remove(indice);
        ColorTerminal.JUGADOR.imprimirLinea(" Usas " + objeto.getNombre() + ".");

        // Si el objeto es una poción de curación y hemos llegado aquí, es que podemos usarla
        if (objeto == Objeto.POCION_CURACION) {
            int vidaAnterior = vida;
            vida = Math.min(vidaMaxima, vida + GameConfig.CURACION_POCION);
            int vidaRecuperada = vida - vidaAnterior;
            ColorTerminal.JUGADOR.imprimirLinea(" Recuperas " + vidaRecuperada + " puntos de vida.");
            return;
        }
        if (objeto == Objeto.POCION_ATAQUE) {
            pocionAtaqueActiva = true;
            ColorTerminal.JUGADOR.imprimirLinea(" Tus ataques harán el doble de daño durante este encuentro.");
            return;
        }
        if (objeto == Objeto.POCION_DEFENSA) {
            pocionDefensaActiva = true;
            ColorTerminal.JUGADOR.imprimirLinea(" Recibirás la mitad de daño durante este encuentro.");
            return;
        }
        // Si hemos llegado aquí es que estamos intentando usar un objeto de la mochila
        // Y no tenemos controlada la lógica de su tipo, por lo que lanzamos un aviso de error
        throw new IllegalStateException("Intentando usar un objeto de la mochila que no existe");
    }

    /** Termina y anuncia los efectos temporales activos del encuentro. */
    public void finalizarEfectosDeEncuentro() {

        // Éste es fácil: vemos qué poción tenemos activa y la desactivamos.
        if (pocionAtaqueActiva) {
            ColorTerminal.INFORMACION.imprimirLinea(" El efecto de la poción de ataque ha terminado.");
            pocionAtaqueActiva = false;
        }
        if (pocionDefensaActiva) {
            ColorTerminal.INFORMACION.imprimirLinea(" El efecto de la poción de defensa ha terminado.");
            pocionDefensaActiva = false;
        }
    }

    /** @return {@code true} si el personaje conserva puntos de vida */
    public boolean estaVivo() {
        return vida > 0;
    }
}
