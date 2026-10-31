//En esta ocasión, para practicar herencia de clases vamos a extender nuestro juego 
// haciendo que los personajes tengan una profesión. En principio tendremos dos: guerrero y mago.
// y lo programaremos de forma que sean clase que heredan de Personaje. 
// es decir, tendrán todas las propiedades y métodos de Personaje, pero 
// tendrán también algunas propias exclusivas:
// los magos, tinen menos vida, pero hacen más daño. Ademas,  al atacar lanzarán un conjuro con usarMagia(),
//  lo que permite atacar a distancia y hacer más daño.
// el guerrero solo ataca en melee, por lo que no pueden atacar a menos que el enemigo esté justo al lado.
// pero a cambio, tiene una habilidad especial reducirDaño(), gracias al escudo, 
// que les permite recibir menos daño al recibir un impacto.
// Los enemigos también los vamos a plantear con herencia esta vez. 
// Y además de distinto danio, también tendrán distinta vida

// al comienzo del juego, el jugador podrá elegir, además del nombre, 
// la clase (profesión) de su personaje.

//Y vamos a modificar también el loop de juego:
// Para cada enfrentamiento, el enemigo puede estar a distancia 0 o 1 (50% de probabilidad)
// Si está a 1, el personaje podría hacer un ataque a distancia "gratis", antes de que el enemigo lo alcance
// Al siguiente paso, el enemigo estaría a distancia 0
// Una vez a distancia 0, comienza el bucle de combate: 
// primero golpea el jugador, y si el enemigo sigue vivo, golpea el enemigo
// Después, si el personaje sigue vivo, podrá usar una poción si le quedan y se repite el bucle de combate 
// si el personaje muere, pantalla de game over
// si el enemigo muere, salimos del bucle de combate y pasamos al siguiente enfrentamiento
// si no quedan más enfrentamientos, el jugador gana.


import java.util.Scanner;
public class Main {

    private static final int NUM_ENEMIGOS = 4;
    private static Personaje miPersonaje;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        boolean jugarDeNuevo;
        do {
            jugarDeNuevo = gameLoop();
        } while (jugarDeNuevo);
        sc.close();
    }

    private static boolean gameLoop(){

        Enemigo enemigoActual;
        System.out.print("Introduce el nombre de tu personaje: ");

        miPersonaje = new Personaje(sc.nextLine());

        System.out.println("=== PREPARANDO LA EXPEDICIÓN ===");
        miPersonaje.mostrarFicha();

        for (int i = 1; i <= NUM_ENEMIGOS; i++) {
            System.out.println("-----Enfrentamiento número " + i + "----");

            enemigoActual = new Enemigo();

            enemigoActual.hacerDanio(miPersonaje);

            if ( !miPersonaje.estaVivo()){
                System.out.println("GAME OVER!!! Conseguiste sobrevivir a "+ (i-1)+ " enfrentamientos");
                return finDePartida();
            }

            beberPocion();
            miPersonaje.mostrarFicha();
            System.out.println("-----Fin del enfrentamiento-----");
        }
        System.out.println("ENHORABUENA!!! Has sobrevivido a la prueba!");
        return finDePartida();
    }

    private static void beberPocion(){
        if (miPersonaje.usarPocion()) {
            System.out.println(miPersonaje.getNombre() + " usa una poción.");
        } else {
            System.out.println("No quedan pociones.");
        }
    }
    private static boolean finDePartida(){
        System.out.println("Quieres intentarlo de nuevo? 1. A por ellos! 2. Ya me han zurrado bastante.");
        String result = sc.nextLine();
        while (!"1".equals(result) && !"2".equals(result)) {
            System.out.println("Valor inválido. Opciones: 1. A por ellos! 2. Ya me han zurrado bastante.");
            result = sc.nextLine();
        }
        return "1".equals(result);
    }
}
