
/*
 * Codelab 4 - Functions & try/catch
 * Fecha: 25-11-2026
 * Autor: Sergio Montes
 * En el codelab de hoy vamos a hacer uso de las funciones (por fin!)
 * y de la instrucción try/catch
 * Qué haremos en esta sesión:
 *  * Vamos simplemente a refactorizar nuestro código para usar funciones y try/catch.
 *  * Puede parecer poco, pero ya veréis cómo nuestro código va a quedar irreconocible,
 *  * y por supuesto, mucho más legible y optimizado.
 *  * Aparte de eso (que ya es bastante), vamos a añadir una opción 
 *  * al final de la partida (al retirarse o morir) para poder reiniciarla
 */


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;

public class Main {

    // Lo ideal sería mover todas estas constantes a una clase aparte GameConfig
    // pero como aun no habéis visto POO, no nos queda otra que dejarlas accesibles
    // en toda la clase colocándolas aquí ya haciénolas estáticas
    private static final String COLOR_INFORMATIVO = "\u001B[37m";
    private static final String COLOR_OPCIONES = "\u001B[33m";
    private static final String COLOR_JUGADOR = "\u001B[32m";
    private static final String COLOR_ENEMIGO = "\u001B[31m";
    private static final int TIEMPO_ESPERA = 1000;
    private static final int VIDA_MAXIMA = 100;
    private static final int CURACION_POCION = 50;
    private static final int DANIO_ESPADA = 25;
    private static final double DEFENSA_ESCUDO = 0.25;
    private static final int DANIO_TRASGO = 20;
    private static final int DANIO_TROLL = 30;
    private static final String NOMBRE_TROLL = "Troll";
    private static final int VIDA_TRASGO = 50;
    private static final int VIDA_TROLL = 80;
    private static final int ENEMIGO_TRASGO = 1;
    private static final String NOMBRE_TRASGO = "Trasgo";
    private static final String INVENTARIO_POCION = "Poción de curación";
    private static final String INVENTARIO_ESPADA = "Espada";
    private static final String INVENTARIO_ESCUDO = "Escudo";
    private static final String INVENTARIO_ANILLO_DEFENSA = "Anillo de defensa";
    private static final String INVENTARIO_ANILLO_ATAQUE = "Anillo de ataque";
    private static final double INCREMENTO_ANILLO_DEFENSA = 1.5;
    private static final double INCREMENTO_ANILLO_ATAQUE = 1.5;
    private static final String[] INVENTARIO_INICIAL = {INVENTARIO_POCION,INVENTARIO_POCION, INVENTARIO_ESPADA, INVENTARIO_ESCUDO};
    private static final String[] LOOT = {INVENTARIO_POCION, INVENTARIO_ANILLO_DEFENSA, INVENTARIO_ANILLO_ATAQUE};
    private static final int[] LOOT_PROBABILIDADES = {50, 75, 100}; 

    // Como vamos a usar ahora funciones, he movido las variables
    // que vayamos a usar en más de una aquí para hacerlas accesibles.
    // Antes podíamos permitirnos el lugo de crearlas e instanciarlas en main()
    // pero si queremos poder acceder a ellas desde todas las funciones,
    // la solución más sencilla es dejarlas aquí como estáticas
    private static ArrayList<String> inventario;
    private static String nombreHeroe;
    private static int vidaHeroe;
    private static int pocionesHeroe;
    private static int contadorEnemigos;

    private static String nombreEnemigo;
    private static int tipoEnemigo;
    private static int vidaEnemigo;
    private static int danioEnemigo;

    private static boolean seHaRetirado;

    // Por qué usar una variable de la clase Random en todas las partes
    // en lugar de instanciar variable de ámbito local cada vez?
    private static Random aleatorio;
    // Vamos también a usar una variable para guardar el color de los siguientes
    // mensajes que queramos imprimir
    // podríamos pasarlo como parámetro, pero la verdad,
    // y de hecho lo hice así al principio, pero se hacía muy repetitivo e ineficiente
    private static String colorMensajes;

    // El Scanner también lo vamos a mover aquí y le cambio el nombre para
    // no perderle la pista, que "sc" para una variable temporal va, pero así ya no...
    private static Scanner scanner;

    //Ésta es nueva para controlar si el jugador quiere reiniciar partida
    private static boolean continuarJugando;

    public static void main(String[] args) {
        // main ahora lo único que hace es instanciar algunas variables
        // y llamar a iniciarPartida. Limpio limpio!
        aleatorio = new Random();
        scanner = new Scanner(System.in);
        seHaRetirado=false;
        continuarJugando=true;
        while(continuarJugando){
            iniciarPartida();
        }
        // Aquí llegamos si el jugador ya no quiere seguir jugando. 
        // Cerramos el scanner y listo!
        scanner.close();
    }
    private static void iniciarPartida(){
        //inicializamos el color de los mensajes a informativo, por defecto.
        colorMensajes=COLOR_INFORMATIVO;
        inventario= new ArrayList<>();
        vidaHeroe = VIDA_MAXIMA;
        for (String objeto : INVENTARIO_INICIAL) {
            inventario.add(objeto);
        }
        pocionesHeroe = 2;
        contadorEnemigos = 0;      
        
        // Al tener una función para imprimir la doble línea que usamos para crear "bloques",
        // otra para la cabecera en sí y otra para mensajes en sí,
        // se queda todo mucho más limpio
        imprimirLinea();
        imprimirCabecera("       BIENVENIDO AL REINO DE JAVA THE HUT       ");
        imprimirLinea();
        imprimirMensaje("Introduce el nombre de tu héroe: ");
        nombreHeroe = scanner.nextLine();
        imprimirCabecera("¡COMIENZA LA AVENTURA!");

        //Aquí realizamos el GameLoop
        do {
           gameLoop(); 
        }while (vidaHeroe > 0 && !seHaRetirado);
        
        /* ----------MENSAJES DE FIN DE PARTIDA---------- */
        if(vidaHeroe<=0){
            colorMensajes=COLOR_ENEMIGO;
            imprimirCabecera(nombreHeroe+" ha sido derrotado! ");
        }else{
            colorMensajes= COLOR_JUGADOR;
            imprimirCabecera(nombreHeroe+" ha escapado con vida!");
        }
        colorMensajes = COLOR_INFORMATIVO;
        imprimirMensaje("Enemigos derrotados:"+contadorEnemigos);
        imprimirCabecera("GAME OVER"); 
        
        /* ----------OPCIONES DE REINICIO---------- */
        // Aquí metemos la lógica nueva para reiniciar partida
        colorMensajes=COLOR_OPCIONES;
        imprimirLinea();
        imprimirCabecera("¿Quieres empezar de nuevo?");
        imprimirMensaje(">1. Sí");
        imprimirMensaje(">2. No");
        continuarJugando=(validarOpcion(2)==1);
        scanner.nextLine(); // Limpiamos el buffer del scanner para que no nos de problemas al leer el nombre del héroe en la siguiente partida
        if(!continuarJugando) imprimirCabecera("¡HASTA OTRA!");
    }

    private static void gameLoop() {
        colorMensajes=COLOR_INFORMATIVO;
        imprimirFicha();
        /* ----------Selección del siguiente enemigo.---------- */
        tipoEnemigo = aleatorio.nextInt(2) + 1;
        if (tipoEnemigo == ENEMIGO_TRASGO) {
            
            nombreEnemigo = NOMBRE_TRASGO;
            danioEnemigo = DANIO_TRASGO;
            vidaEnemigo = VIDA_TRASGO;
        } else {
            nombreEnemigo = NOMBRE_TROLL;
            danioEnemigo = DANIO_TROLL;
            vidaEnemigo = VIDA_TROLL;
        }
        /* ----------COMBAT LOOP---------- */
        imprimirCabecera("NUEVO ENCUENTRO");
        imprimirMensaje("\n¡Un " + nombreEnemigo + " salvaje aparece!");
        while (vidaHeroe > 0 && vidaEnemigo > 0) {
            combatLoop();
        }
        if (vidaHeroe>0) {
            colorMensajes=COLOR_OPCIONES;
            int numValidas=2;
            imprimirMensaje("Qué quieres hacer?");
            imprimirMensaje(">1. Retirarte");
            imprimirMensaje(">2. Continuar sin usar poción");
            if (pocionesHeroe>0) {
                imprimirMensaje(">3. Continuar usando una poción");
                numValidas=3;
            }
                // Aquí antes teníamos una lista de cosas que hacer:
            // comprobar la opción del usuario, validarla, etc
            // al sacarlo en la función validarOpcion y usar un  try/catch simplificamos mucho
            int opcionSeleccionada = validarOpcion(numValidas);
            seHaRetirado= (opcionSeleccionada==1); 
            if (opcionSeleccionada==3)  beberPocion();
        }  
    }
    private static void combatLoop() {
        int DanoTemporal = DANIO_ESPADA;
        int modificadorAtaque = 1;
        for (String objeto : inventario) {
            if (objeto.equals(INVENTARIO_ANILLO_ATAQUE)) {
                modificadorAtaque *= INCREMENTO_ANILLO_ATAQUE;
            }
        }
        DanoTemporal = (int) (DanoTemporal *  modificadorAtaque);
        System.out.println(COLOR_JUGADOR + "\n¡" + nombreHeroe + " ataca al " + nombreEnemigo + " con la espada y le hace " + DanoTemporal + " de daño!");
        vidaEnemigo -= DanoTemporal;
        //Comprobamos si el enemigo ha muerto, para mostrar un 
        // mensaje de victoria y que suelte un objeto.
        colorMensajes = COLOR_JUGADOR;
        if (vidaEnemigo <= 0) {
            contadorEnemigos++;
            imprimirMensaje(nombreHeroe + " ha derrotado al " + nombreEnemigo + "! El enemigo suelta un objeto.");
            int lootRandom = aleatorio.nextInt(100) + 1;
            for (int i = 0; i < LOOT_PROBABILIDADES.length; i++) {
                if (lootRandom <= LOOT_PROBABILIDADES[i]) {
                    String objetoLoot = LOOT[i];
                    inventario.add(objetoLoot);
                    imprimirMensaje("¡Has conseguido " + objetoLoot + "! Se añade a tu inventario.");
                    if(objetoLoot==INVENTARIO_POCION) pocionesHeroe++;
                    break;
                }
            }
        }else {
            // Si no ha muerto, mostramos su vida restante y es el turno del enemigo.
            colorMensajes=COLOR_ENEMIGO;
            imprimirMensaje("Vida del " + nombreEnemigo + ": " + vidaEnemigo + " HP");
            double modificadorDefensa = 1 + DEFENSA_ESCUDO;
            for (String objeto : inventario) {
                if (objeto.equals(INVENTARIO_ANILLO_DEFENSA)) {
                    modificadorDefensa *= INCREMENTO_ANILLO_DEFENSA;
                }
            }
            DanoTemporal = (int) (danioEnemigo / modificadorDefensa);
            vidaHeroe= Math.max(0, vidaHeroe-DanoTemporal);
            imprimirMensaje(nombreEnemigo + " ataca a " + nombreHeroe + " y le hace " + DanoTemporal + " de daño!");
            imprimirMensaje("Vida de " + nombreHeroe + ": " + vidaHeroe + " HP");
        }

        // Aquí metemos la espera de un segundo que teníamos
        // pero al poder ya usar try/catch, ya no necesitamos
        // hacer que las funciones que llamen a esto hagan un throws InterruptedException
        try {
            Thread.sleep(TIEMPO_ESPERA);
        } catch (InterruptedException e) {
            // Aquí no hace falta hacer nada
        }
    }

    private static void imprimirLinea(){
        System.out.println(colorMensajes + "=================================================");
    }
    private static void imprimirMensaje(String mensaje){
        System.out.println(colorMensajes + mensaje);
    }
    private static void imprimirCabecera(String mensaje){
        System.out.println(colorMensajes + "\n+|=========> " + mensaje + " <=========|+");
    }
    private static void imprimirFicha(){
        // Fijaos qué limpita queda ahora la impresión de la ficha del héroe
        // Además, otra ventaja de sacar todo esto con funciones
        // es que si quisiéramos cambiar el estilo de los mensajes, 
        // como lo tenemos centralizado, lo cambiamos en estas funciones
        //  y se cambia en todo el juego
        imprimirCabecera("FICHA DEL HÉROE");
        imprimirMensaje("Nombre: " + nombreHeroe);
        imprimirMensaje("Salud: " + vidaHeroe);
        imprimirMensaje("Inventario: ");
        for (String objeto : inventario) {
            imprimirMensaje("- " + objeto);
        }
    }

    // Valida opciones numéricas enteras empezando por 1 
    // y terminando por el parámetro que recibe: numValidas
    private static int validarOpcion(int numValidas){
        int opcion=0;
        try {
            opcion = scanner.nextInt();
            if( (opcion > numValidas) || (opcion<1) ){
                imprimirMensaje("Opción inválida. Elije un número entre 1 y "+numValidas);
                return validarOpcion(numValidas);
            }
            return opcion;
        } catch (InputMismatchException e) {
            imprimirMensaje("Opción inválida. Elije un número entre 1 y "+numValidas);
            scanner.next();
            return validarOpcion(numValidas);
        } 
    }

    private static void beberPocion(){
        inventario.remove(INVENTARIO_POCION);
        pocionesHeroe--;
        vidaHeroe= Math.min(VIDA_MAXIMA, vidaHeroe+CURACION_POCION);
    }
}

/*
 * Ejercicios propuestos:
 * Refactorizarlo todo para usar las clases GameConfig, Heroe y Enemigo (al menos)
*/