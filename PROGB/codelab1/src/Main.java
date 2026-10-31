import java.util.Scanner;

//OJO!!! Lo primero que observaréis es que ya no está la clase Personaje
//La he movido al archivoPersonaje.java
//Está separada por buenas prácticas. No estaba mal cuando teníamos solo dos clases y el código era escaso
// pero ahora que vaos a tener ya varias es mejor separarlas.
// Quién me dice por qué?

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
        // Creación del personaje usando Scanner
        Enemigo enemigoActual;
        System.out.print("Introduce el nombre de tu personaje: ");
        // podríamos crear una constante o una variable para guardar el nombre
        // nombre = sc.nextLine();
        // pero en este caso da igual ya que solo se va a usar una vez
        // ... da igual o es mejor de una manera u otra?
        miPersonaje = new Personaje(sc.nextLine());

        // Comienzo de la aventura
        System.out.println("=== PREPARANDO LA EXPEDICIÓN ===");
        miPersonaje.mostrarFicha();

        // bucle de enfrentamientos
        // Hacemos 4 enfrentamientos, usando un bucle for
        for (int i = 1; i <= NUM_ENEMIGOS; i++) {
            System.out.println("-----Enfrentamiento número " + i + "----");
            // creo una instancia de Enemigo. El constructor ya se encarga de asignarle el tipo
            //podría haber delarado e instanciado aquí la variable a la vez haciendo
            // Enemido enemigoActual = new Enemigo() ?
            enemigoActual = new Enemigo();
            // llamamos al método para hacer daño de la clase Enemigo,
            // que ya se encarga de todo (calcular el daño según el tipo y mostrar por pantalla el ataque)
            enemigoActual.hacerDanio(miPersonaje);
            //Comprobamos si el personaje sigue vivo
            //si no, rompemos el bucle y procesamos la muerte
            if ( !miPersonaje.estaVivo()){
                System.out.println("GAME OVER!!! Conseguiste sobrevivir a "+ (i-1)+ " enfrentamientos");
                return finDePartida();
            }
            //podría haber puesto un else aquí, pero como el if anterior ya tiene break si se cumple...
            // para qué enredar todo metiéndolo en otras llaves más?
            
            //Podría haber dejado el código de este método así sin crearlo aparte? Sí,
            // por ahora solo por limpieza y aligerar el método main que está quedando monolítico, 
            // casi que merece la pena. 
            // Pero es que además el instinto me dice que voy a necesitar esto en el futuro para llamarlo
            // desde más de un sitio
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
        //Esta forma de resolver la petición de nueva partida o fin de la misma
        // me parece bastante limpia. Con el try catch cubrimos cualquier opción no numérica
        // y luego con un simple return solventamos las opciones válidas
        // System.out.println("Quieres intentarlo de nuevo? 1. A por ellos! 2. (u otro valor) Ya me han zurrado bastante.");
        // try {
        //     return (sc.nextInt()==1);
        // } catch (Exception e) {
        //     return true;
        // }

        ///Otra manera, más complicada, pero desde el punto de vista de diseño de juegos, mejor, sería esta:
        System.out.println("Quieres intentarlo de nuevo? 1. A por ellos! 2. Ya me han zurrado bastante.");
        String result = sc.nextLine();
        while (!"1".equals(result) && !"2".equals(result)) {
            System.out.println("Valor inválido. Opciones: 1. A por ellos! 2. Ya me han zurrado bastante.");
            result = sc.nextLine();
        }
        return "1".equals(result);
    }
}
