import java.util.Random;

public class GameEngine {  

    private boolean seHaRetirado;
    private boolean continuarJugando;
    private Heroe personaje;
    private Enemigo enemigoActual;
    
    private Interfaz miUI;

    public GameEngine(){
        GameConfig.aleatorio = new Random();
        miUI=Interfaz.getInstancia();
        seHaRetirado=false;
        continuarJugando=true;
        while(continuarJugando){
            iniciarPartida();
        }
        miUI.salir();
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
