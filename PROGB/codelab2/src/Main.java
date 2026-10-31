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
        
        // Para escribir estos caracters, por si tenéis curiosidad:
        // Abre Mapa de caracteres desde el menú Inicio.
        // Para escribir iconos, Pulsa Windows + . (punto) o Windows + ; (punto y coma).
        //Aparte de ponerlo más "bonico", este bloque es casi igual a como lo teníamos
        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        ⚔️  LEYENDAS DEL CÓDIGO  ⚔️        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("🧙 Introduce el nombre de tu personaje: ");
        String nombre = sc.nextLine();

        //Aquí metemos la parte donde el jugador selecciona su clase
        System.out.println();
        System.out.println("Elige tu profesión:");
        System.out.println("  1️⃣  Guerrero 🛡️");
        System.out.println("  2️⃣  Mago     ✨");
        String profesion = sc.nextLine();
        while (!"1".equals(profesion) && !"2".equals(profesion)) {
            System.out.println("❌ Opción no válida. Escribe 1 para Guerrero o 2 para Mago.");
            profesion = sc.nextLine();
        }
        //Utilizamos directamente el constructor de su clase heredada en función de la opción
        miPersonaje = "1".equals(profesion) ? new Guerrero(nombre) : new Mago(nombre);

        System.out.println();
        System.out.println("════════════ 🗺️  PREPARANDO LA EXPEDICIÓN  🗺️ ════════════");
        miPersonaje.mostrarFicha();

        //El bucle principal es similar, solo que tendremos que introducir un bucle adicional de combate 
        // por cada enemigo, en lugar de resolver daño y listo, como teníamos antes.
        // además de introducir la lógica de combate a distancia
        for (int i = 1; i <= NUM_ENEMIGOS; i++) {
            System.out.println();
            System.out.println("━━━━━━━━━━━━ ⚔️  ENFRENTAMIENTO " + i + " / " + NUM_ENEMIGOS + " ⚔️ ━━━━━━━━━━━━");

            Enemigo enemigoActual = crearEnemigo();
            
            //seleccionamos la distancia de aparición del enemigo
            int distancia = Math.random() < 0.5 ? 0 : 1;
            //si está a distancia (distancia=1), resolvemos si el personaje puede atacar desde elejos
            // dependiendo de su clase
            if (distancia == 1) {
                System.out.println("👀 ¡El enemigo aparece a lo lejos!");
                if (!miPersonaje.atacarADistancia(enemigoActual)) {
                    System.out.println("🛡️ El guerrero espera: ¡solo puede luchar cuerpo a cuerpo!");
                }
                if (enemigoActual.estaVivo()) {
                    System.out.println("🏃 El enemigo se acerca... ¡ahora está cuerpo a cuerpo!");
                }
            }

            //Y aquí, una vez el enemigo ya está a distancia 0, introducimos un nuevo bucle
            // para reoslver el combate paso a paso
            // dando la elección al jugador de tomarse la opción al final de cada "round"
            //las condiciones para continuar son que tanto el personaje como el enemigo sigan vivos
            while (miPersonaje.estaVivo() && enemigoActual.estaVivo()) {
                miPersonaje.atacar(enemigoActual);
                if (enemigoActual.estaVivo()) {
                    enemigoActual.hacerDanio(miPersonaje);
                }
                // esta línea es un pelín larga, pero me parecía que merecía la pena dejarla en una sola línea
                // y la lógica para ver si el jugador decide beber o no, la he preferido delegar a un método aparte
                // para no dejar esto extremadamente monolítico
                if (miPersonaje.estaVivo() && enemigoActual.estaVivo() && miPersonaje.getPociones() > 0)  beberPocion();
            }

            // una vez uno de los dos "palma". 
            //Comprobamos si el personaje sigue vivo. Si no, GAME OVER, man.
            if (!miPersonaje.estaVivo()) {
                System.out.println();
                System.out.println("☠️ ═══════════════ FIN DE LA AVENTURA ═══════════════ ☠️");
                System.out.println("Has sobrevivido a " + (i - 1) + " enfrentamiento(s).");
                return finDePartida();
            }
            
            //Si sigue vivo, palmadita en la espalda
            System.out.println("🎉 ¡Has derrotado al " + enemigoActual.getNombre() + "!");
            miPersonaje.mostrarFicha();
            System.out.println("━━━━━━━━━━━━ 🏁 FIN DEL ENFRENTAMIENTO 🏁 ━━━━━━━━━━━━");
        }

        //Y si hemos llegado hasta aquí, es que el personaje ha sobrevivido a todos los encuentros
        // así que.. congratulations!
        System.out.println();
        System.out.println("🏆 ═══════════════ ¡VICTORIA! ═══════════════ 🏆");
        System.out.println("🌟 ¡Has sobrevivido a toda la expedición, " + miPersonaje.getNombre() + "!");
        return finDePartida();
    }

    private static Enemigo crearEnemigo() {
        return Math.random() < 0.5 ? new Troll() : new Trasgo();
    }

    private static void beberPocion(){
        //básicamente, le preguntamos al jugador si quiere beber poción
        //la comprobación de si tiene pociones ya la hemos hecho antes
        System.out.println("🧪 ¿Quieres beber una poción? 1. Sí  2. No");
        String respuesta = sc.nextLine();
        while (!"1".equals(respuesta) && !"2".equals(respuesta)) {
            System.out.println("❌ Opción no válida. Escribe 1 para Sí o 2 para No.");
            respuesta = sc.nextLine();
        }
        if ("1".equals(respuesta)) {
            //como ya teníamos un método para tomar pociones que nos delvía false
            //si la vida estaba a tope, o no le quedaban pociones
            // nos permite lanzarle el segundo mensajito
            // Ya que sé seguro que tiene pociones, por lo que si devuelve false,
            // es porque está a toe de vida
            if (miPersonaje.usarPocion()) {
                System.out.println("💚 " + miPersonaje.getNombre() + " bebe una poción y recupera fuerzas.");
            } else {
                System.out.println("💚 Ya tienes la vida al máximo.");
            }
        } else {
            System.out.println("➡️ Sigues luchando sin usar una poción.");
        }
    }
    private static boolean finDePartida(){
        //Aquí lo único que he hecho es dejarlo más bonico. Poco más.
        System.out.println();
        System.out.println("🔁 ¿Quieres volver a intentarlo?");
        System.out.println("  1️⃣  ¡A por otra aventura!");
        System.out.println("  2️⃣  Por hoy ya he tenido bastante.");
        String result = sc.nextLine();
        while (!"1".equals(result) && !"2".equals(result)) {
            System.out.println("❌ Opción no válida. Escribe 1 o 2.");
            result = sc.nextLine();
        }
        if ("2".equals(result)) {
            System.out.println("👋 ¡Gracias por jugar! Hasta la próxima.");
        }
        return "1".equals(result);
    }
}

//Tareítas para la próxima:
// 1. Hacer que los Trasgos puedan atacar a distancia
//    Similar a los magos, y habría que introducir la lógica en el bloque de ataque a distancia del gameloop
// 2. Hacer que los Trolls regeneren vida por cada ronda de combate
//    Ésta es nueva: implicaría una comprobación cada ronda para ver si el enemigo regenera (recupera vida)
//    Ojo: cada ronda de combate, no cada enfrentamiento
// 3. Juega con los valores de daño, regeneración, resistencia al daño, vida, ... para que el juego
//    esté balanceado y ninguna clase (mago o guerrero) está "OP"
