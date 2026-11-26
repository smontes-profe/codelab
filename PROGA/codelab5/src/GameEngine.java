import java.util.Random;

public class GameEngine {  

    private boolean seHaRetirado;
    private boolean continuarJugando;
    private Heroe personaje;
    private Enemigo enemigoActual;
    private int trasgosDerrotados;
    private int trollsDerrotados;
    
    private Interfaz miUI;

    public GameEngine(){
        GameConfig.aleatorio = new Random();
        miUI=Interfaz.getInstancia();
        miUI.mostrarInicio(this);
        // seHaRetirado=false;
        // continuarJugando=true;
        // while(continuarJugando){
        //     iniciarPartida();
        // }
        // miUI.salir();
    }
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
        }
    }

    private void iniciarEncuentro() {
        enemigoActual = new Enemigo();
        miUI.mostrarEnemigo(enemigoActual);
    }

    /**
     * Continúa la aventura después de ganar un encuentro.
     * @param usarPocion si se debe consumir una poción antes del nuevo encuentro
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

    /** Termina la aventura después de ganar un encuentro. */
    public void retirarse() {
        comprobarFinEncuentro();
        seHaRetirado = true;
        miUI.mostrarRetirada(personaje);
    }

    private void comprobarFinEncuentro() {
        if (personaje == null || enemigoActual == null || !personaje.estaVivo()
                || enemigoActual.estaVivo() || seHaRetirado) {
            throw new IllegalStateException("No hay un encuentro ganado pendiente de decisión.");
        }
    }

    private void iniciarPartida(){
        personaje = miUI.inicializarHeroe();
        do {
           realizarGameLoop(); 
        }while (personaje.estaVivo() && !seHaRetirado);
        continuarJugando= miUI.mostrarGameOver();           
    }

    private void realizarGameLoop() {
        miUI.imprimirFicha();
        enemigoActual = new Enemigo();
        /* ----------COMBAT LOOP---------- */
        miUI.iniciarNuevoEncuentro(enemigoActual);
        while (personaje.estaVivo() && enemigoActual.estaVivo()) {
            realizarCombatLoop();
        }
        if (personaje.estaVivo()) {
            int opcionSeleccionada = miUI.mostrarOpcionesEncuentro();
            seHaRetirado= (opcionSeleccionada==1); 
            if (opcionSeleccionada==3)  personaje.beberPocion();
        }  
    }
    private void realizarCombatLoop() {
        int danioHeroe = personaje.hacerDanio(enemigoActual);
        miUI.mostrarDanioAlEnemigo(danioHeroe);
        if (!enemigoActual.estaVivo()) {
            personaje.enemigosDerrotados++;
            miUI.mostrarLoot(personaje.getNuevoLoot());
        }else {
            int DanioEnemigo = personaje.recibirDanio(enemigoActual.getDanio());
            miUI.mostrarDanioAlHeroe(DanioEnemigo);
        }
        miUI.finalizarCombatLoop();
    }
}
