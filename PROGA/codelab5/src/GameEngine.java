import java.util.Random;

public class GameEngine {  

    private boolean seHaRetirado;
    private Heroe personaje;
    private Enemigo enemigoActual;
    private int trasgosDerrotados;
    private int trollsDerrotados;
    
    private Interfaz miUI;

    /** Inicializa el generador aleatorio y muestra la pantalla de inicio. */
    public GameEngine(){
        GameConfig.aleatorio = new Random();
        miUI=Interfaz.getInstancia();
        miUI.mostrarInicio(this);
    }
    /**
     * Crea un héroe, reinicia los contadores y comienza el primer encuentro.
     * @param nombre nombre elegido para el héroe
     */
    public void comenzarPartida(String nombre) {
        seHaRetirado = false;
        personaje = new Heroe(nombre);
        trasgosDerrotados = 0;
        trollsDerrotados = 0;

        miUI.mostrarVistaPrincipal(personaje, this);
        miUI.actualizarContadores(trasgosDerrotados, trollsDerrotados);

        iniciarEncuentro();
    }

    /**
     * Realiza un turno de combate y actualiza la información de la ventana.
     * El enemigo solo contraataca si ha sobrevivido al ataque del héroe.
     * @throws IllegalStateException si no hay un combate activo
     */
    public void atacar() {
        if (personaje == null || enemigoActual == null
                || !personaje.estaVivo() || !enemigoActual.estaVivo()) {
            throw new IllegalStateException("No hay un combate activo.");
        }

        int danioHeroe = personaje.hacerDanio(enemigoActual);
        int danioEnemigo = 0;
        if (enemigoActual.estaVivo()) {
            danioEnemigo = personaje.recibirDanio(enemigoActual.getDanio());
        }

        String recompensa = null;
        if (!enemigoActual.estaVivo()) {
            personaje.enemigosDerrotados++;
            if (enemigoActual.getNombre().equals(GameConfig.NOMBRE_TRASGO)) {
                trasgosDerrotados++;
            } else if (enemigoActual.getNombre().equals(GameConfig.NOMBRE_TROLL)) {
                trollsDerrotados++;
            }
            recompensa = personaje.getNuevoLoot();
        }

        miUI.actualizarFicha(personaje);
        miUI.actualizarEnemigo(enemigoActual);
        miUI.mostrarResultadoTurno(personaje, enemigoActual, danioHeroe, danioEnemigo);
        if (recompensa != null) {
            miUI.actualizarContadores(trasgosDerrotados, trollsDerrotados);
            miUI.mostrarRecompensa(recompensa);
            miUI.mostrarOpcionesFinEncuentro(personaje.tienePociones());
        } else if (!personaje.estaVivo()) {
            miUI.mostrarFinPartida(personaje, false);
        }
    }

    private void iniciarEncuentro() {
        enemigoActual = new Enemigo();
        miUI.mostrarEnemigo(enemigoActual);
    }

    /**
     * Continúa la aventura después de ganar un encuentro.
     * @param usarPocion si se debe consumir una poción antes del nuevo encuentro
     * @throws IllegalStateException si no hay un encuentro ganado pendiente
     *         o si se solicita una poción y no queda ninguna
     */
    public void continuarAventura(boolean usarPocion) {
        comprobarFinEncuentro();
        if (usarPocion) {
            if (!personaje.tienePociones()) {
                throw new IllegalStateException("No quedan pociones en el inventario.");
            }
            personaje.beberPocion();
            miUI.actualizarFicha(personaje);
        }
        iniciarEncuentro();
    }

    /**
     * Termina la aventura después de ganar un encuentro.
     * @throws IllegalStateException si no hay un encuentro ganado pendiente
     */
    public void retirarse() {
        comprobarFinEncuentro();
        seHaRetirado = true;
        miUI.mostrarFinPartida(personaje, true);
    }

    /**
     * Vuelve a la pantalla de inicio después de una derrota o retirada.
     * @throws IllegalStateException si la partida no ha terminado
     */
    public void reiniciar() {
        if (personaje == null || (personaje.estaVivo() && !seHaRetirado)) {
            throw new IllegalStateException("La partida todavía no ha terminado.");
        }
        personaje = null;
        enemigoActual = null;
        seHaRetirado = false;
        trasgosDerrotados = 0;
        trollsDerrotados = 0;
        miUI.mostrarInicio(this);
    }

    private void comprobarFinEncuentro() {
        if (personaje == null || enemigoActual == null || !personaje.estaVivo()
                || enemigoActual.estaVivo() || seHaRetirado) {
            throw new IllegalStateException("No hay un encuentro ganado pendiente de decisión.");
        }
    }

}
