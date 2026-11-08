
/*
 * Codelab 3 - Arrays y ArrayLists
 * Fecha: 11-11-2026
 * Autor: Sergio Montes
 * En el codelab de hoy centraremos la atención en el uso de arrays y ArrayLists,
 * que ya habéis visto a nivel teórico, pondremos en práctica connuestro juego de rol.
 * Qué haremos en esta sesión:
 *  * Vamos a complicar bastante la cosa, así que vamos a eliminar la elección de dificultad.
 *  * Ahora los enemigos tendrán puntos de vida y el héroe podrá atacarlos.
 *  * Tendremos dos bucles en la aventura: 
 *  * * - Un bucle principal que representa la aventura, donde el héroe se enfrenta a enemigos.
 *  * * - Un bucle secundario que representa el combate con un enemigo, donde
 *  *   el héroe y el enemigo se atacan hasta que uno de los dos pierde toda su vida.
 *  * (Para simplifcar, automáticamente el héroe atacará primero y luego el enemigo, con una esperade un segundo entre turnos
 *  y así sucesivamente).
 * 
 *  * Vamos a añadir un sistema de inventario (mochila) para el héroe, que le permitirá recoger objetos durante la aventura.
 *  * Para ello, vamos a crear un ArrayList<String> que representará el inventario.
 *  * El héroe empezará con los siguientes objetos:
 *  * * - 2 x pociones de curación: restauran toda la vida.
 *  * * - 1 x espada. La espada hace 25 de daño.
 *  * * - 1 x escudo. Reduce el daño recibido en 10 puntos.
 *  * (Aquí la espada y el escudo son objetos que no se pueden perder, pero las pociones sí. La mostraremos como elementos 
 *  del inventario, pero a nivel de "stats", tanto ataque como defensa serán constantes, por simplicidad).
 * 
 *  * Cada vez que el héroe derrota a un enemigo, éste soltará un objeto al azar en base a un porcentaje de probabilidad: 
 *  * * - (50%) Poción de curación: restaura toda la vida.
 *  * * - (25%) Anillo de defensa: aumenta la efectividad del escudo en un 50% (acumulativo).
 *  * * - (20%) Anillo de ataque: aumenta la efectividad de la espada en un 50% (acumulativo).
 *  * * - (5%) Amuleto de resurrección: si el héroe muere, revive con 100 de vida, pero se pierde todo el inventario.
 *  * Ésta lista de elementos, y los elementos iniciales, los manejaremos con un ArrayList.
 *  * Con esto deberíamos apreciar la diferencia principal entre un array (tamaño fijo) y un ArrayList (tamaño dinámico).
 *  * Una vez que el héroe derrota a un enemigo, informamos del objeto encuontrado, que pasa a su inventario,
 *  y mostramos la ficha del héroe, incluyendo ahora el inventario. 
 * 
 *  * Y ahora sí le daremos opciones al jugador:
 *  * * - Beber una poción de curación (si tiene alguna) y continuar la aventura.
 *  * * - Continuar la aventura sin beber poción.
 *  * * - Completar la aventura y salir vivo de la mazmorra.
 * 
 *  * Al final de la aventura mostraremos un recuento de los enemigos derrotados y si el héroe sobrevivió.
 */


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;

public class Main {

    // Importante añadir "throws InterruptedException" en el main si queremos usar Thread.sleep
    // ya que arroja una excepción controlada de interrupción
    // Como no habéis visto aun try catch, lo resolvemos así por ahora.
    public static void main(String[] args) throws InterruptedException {

        //El tiempo de espera en milisegundos entre cada ronda de ataques
        final int TIEMPO_ESPERA = 1000;
        final int VIDA_MAXIMA = 100;
        final int CURACION_POCION = 40;
        final int DANIO_ESPADA = 25;
        final int DEFENSA_ESCUDO = 10;
        //como estamos complicando mucho la cosa, vamos a definir el daño de los enemigos como constantes, 
        // para que sea más fácil de modificar, y eliminamos las costantes para la dificultad.
        final int DANIO_TRASGO = 25;
        final int DANIO_TROLL = 50;
        final int VIDA_TRASGO = 50;
        final int VIDA_TROLL = 100;
        final int ENEMIGO_TRASGO = 1;
        // Pongo aquí las constantes de los objetos del inventario, para que sea más fácil de modificar y mantener el código.
        final String INVENTARIO_POCION = "Poción de curación";
        final String INVENTARIO_ESPADA = "Espada";
        final String INVENTARIO_ESCUDO = "Escudo";
        final String INVENTARIO_ANILLO_DEFENSA = "Anillo de defensa";
        final String INVENTARIO_ANILLO_ATAQUE = "Anillo de ataque";
        final String INVENTARIO_AMULETO = "Amuleto de resurrección";
        //Aquí definimos los arrays de inventario y loot, que nos servirán para manejar los objetos del juego.
        final String[] inventarioInicial = {INVENTARIO_POCION,INVENTARIO_POCION, INVENTARIO_ESPADA, INVENTARIO_ESCUDO};
        final String[] loot = {INVENTARIO_POCION, INVENTARIO_ANILLO_DEFENSA, INVENTARIO_ANILLO_ATAQUE, INVENTARIO_AMULETO};
        // Probabilidades de loot en porcentaje. Podría metarlas como constantes, pero para simplificar, las dejo aquí.
        // En lugar de guardar las probabilidades en sí, guardo los valores acumulados, 
        // para que sea más fácil de calcular el loot al derrotar a un enemigo.
        final int[] lootProbabilidades = {50, 75, 95, 100}; 

        //He movido aquí la vida del héroe, para que sea más fácil de modificar y mantener el código.
        int vidaHeroe = VIDA_MAXIMA;
        // Declaración del inventario
        ArrayList<String> inventario = new ArrayList<>();
        // Inicializamos el inventario con los objetos iniciales
        for (String objeto : inventarioInicial) {
            inventario.add(objeto);
        }
        //Otra manera de hacerlo hubiera esta:
        // ArrayList<String> inventario = new ArrayList<>(Arrays.asList(inventarioInicial));
        // Pero  no sé si lo habíais visto en clase, así que lo hago con un bucle for sencillo.
        // Y otra forma también hubiera sido recorrer el array con un bucle for i:
        // for(int i = 0; i < inventarioInicial.length; i++) {
        //     inventario.add(inventarioInicial[i]);
        // }

        // Como no podemos usar pociones, me pareció lo más sencillo usar una variable para controlar
        // el número de pociones que tiene el héroe, en lugar de contar cuántas hay en el inventario.
        int pocionesHeroe = 2;
        int contadorEnemigos = 0;
   
        //he movido aquí también la declaración de las variables del enemigo, para que sea más fácil de modificar y mantener el código.
        int enemigo;
        String nombreEnemigo;
        int danoEnemigo;
        int vidaEnemigo;

        Scanner sc = new Scanner(System.in);
        Random aleatorio = new Random();
        System.out.println("=================================================");
        System.out.println("       BIENVENIDO AL REINO DE JAVA THE HUT       ");
        System.out.println("=================================================");
        System.out.print("Introduce el nombre de tu héroe: ");
        String nombreHeroe = sc.nextLine();

        // La dificultad cambia el número de pociones; la vida inicial es la misma.


        // Este contador aumenta cuando el héroe sobrevive al ataque del enemigo.
        
        boolean seHaRetirado = false;

        //Aquí imprimía la ficha, pero lo he movido al final del bucle, para que se vea la ficha actualizada después de cada turno.
        //Ahora es una aventura, no una batalla!
        System.out.println("\n+|=========> ¡COMIENZA LA AVENTURA! <=========|+");

        /* ----------GAME LOOP---------- */
        /* El bucle principal de la aventura. Se repite hasta que el héroe se retira o muere. 
         * He cambiado el bucle while por un do-while, para que al menos se ejecute una vez.
         */
        do {

            /* ----------FICHA DEL HÉROE---------- */
            System.out.println("\n=========== FICHA DEL HÉROE ===========");
            System.out.println("Nombre: " + nombreHeroe);
            System.out.println("Salud: " + vidaHeroe + " HP");
            System.out.println("Inventario: ");
            for (String objeto : inventario) {
                System.out.println("- " + objeto);
            }

            /* ----------Selección del seiguiente enemigo.---------- */
            enemigo = aleatorio.nextInt(2) + 1;
            if (enemigo == ENEMIGO_TRASGO) {
                nombreEnemigo = "Trasgo";
                danoEnemigo = DANIO_TRASGO;
                vidaEnemigo = VIDA_TRASGO;
            } else {
                nombreEnemigo = "Troll";
                danoEnemigo = DANIO_TROLL;
                vidaEnemigo = VIDA_TROLL;
            }
            System.out.println("\n ========== NUEVO ENCUENTRO ==========");
            System.out.println("\n¡Un " + nombreEnemigo + " salvaje aparece!");

            /* ----------COMBAT LOOP---------- */
            // El bucle de combate se repite hasta que el héroe o el enemigo pierden toda su vida.
            //Ataca el héroe primero, luego el enemigo, y así sucesivamente. Se espera un segundo entre turnos.
            while (vidaHeroe > 0 && vidaEnemigo > 0) {
                /* ----------ATAQUE DEL HÉROE---------- */
                //voy a crear esta variable temporal para almacenar el daño que hace el enemigo o el héroe, 
                // para poder mostrarlo en el mensaje de ataque.
                int DanoTemporal;
                //calculo posibles modificadores de daño por objetos del inventario, 
                // comprobando si el héroe tiene algún anillo de ataque.
                // Por cada anillo de ataque, el daño aumenta un 50% acumulativo.
                int modificadorAtaque = 1;
                for (String objeto : inventario) {
                    if (objeto.equals(INVENTARIO_ANILLO_ATAQUE)) {
                        modificadorAtaque *= 0.5;
                    }
                }
                //redondeo usando el casting (int) y muestro el daño que hace el héroe al enemigo.
                DanoTemporal = (int) (DANIO_ESPADA * modificadorAtaque);
                System.out.println("\n¡" + nombreHeroe + " ataca al " + nombreEnemigo + " con la espada y le hace " + DanoTemporal + " de daño!");
                vidaEnemigo -= DanoTemporal;
                //Comprobamos si el enemigo ha muerto, para mostrar un mensaje de victoria y que suelte un objeto.
                if (vidaEnemigo < 0) {
                    contadorEnemigos++;
                    System.out.println("\n¡" + nombreHeroe + " ha derrotado al " + nombreEnemigo + "! El enemigo suelta un objeto.");
                    //El enemigo suelta un objeto al azar, en base a las probabilidades definidas en el array lootProbabilidades.
                    int lootRandom = aleatorio.nextInt(100) + 1;
                    //Recorremos el array de probabilidades acumuladas para determinar qué objeto suelta el enemigo.
                    for (int i = 0; i < lootProbabilidades.length; i++) {
                        if (lootRandom <= lootProbabilidades[i]) {
                            String objetoLoot = loot[i];
                            inventario.add(objetoLoot);
                            System.out.println("¡Has conseguido " + objetoLoot + "! Se añade a tu inventario.");
                            //Controlamos si el objeto es una poción para incrementar el contador de pociones
                            if(objetoLoot==INVENTARIO_POCION) pocionesHeroe++;
                            //una vez eoncontrado el objeto, salimos del bucle for, para no añadir más objetos.
                            break;
                        }
                    }
                }else {
                    // Si no ha muerto, mostramos su vida restante y es el turno del enemigo.
                    System.out.println("Vida del " + nombreEnemigo + ": " + vidaEnemigo + " HP");
                    /* ----------ATAQUE DEL ENEMIGO---------- */
                    //calculo posibles modificadores de defensa por objetos del inventario,
                    // comprobando si el héroe tiene algún anillo de defensa.
                    // Por cada anillo de defensa, la efectividad del escudo aumenta un 50% acumulativo.
                    int modificadorDefensa = DEFENSA_ESCUDO;
                    for (String objeto : inventario) {
                        if (objeto.equals(INVENTARIO_ANILLO_DEFENSA)) {
                            modificadorDefensa *= 0.5;
                        }
                    }
                    DanoTemporal = (int) (danoEnemigo - modificadorDefensa);
                    System.out.println("\n¡El " + nombreEnemigo + " ataca a " + nombreHeroe + " y le hace " + DanoTemporal + " de daño!");
                    System.out.println("Vida de " + nombreHeroe + ": " + vidaHeroe + " HP");
                }

                // En este punto, tanto el héroe como el enemigo han completado sus ataques.
                // Dejamos que pase un segundo e iteramos el bucle.
                Thread.sleep(TIEMPO_ESPERA);
            }
            
            // En este punto se ha roto por fin el bucle de rondas de combate. 
            // Como dentro controlamos si ele enemigo muere, quiere decir que en este punto, 
            // el héroe ha derrotado al enemigo y hemos mostrado el mensaje de victoria y le hemos dado la recompensa, 
            // o el héroe ha muerto. COmprobamos si está vivo, para darle las opciones al jugador
            /* ----------OPCIONES---------- */
            if (vidaHeroe>0) {
                // Si sigue vivo, planteamos las opciones
                System.out.println("Qué quieres hacer?");
                System.out.println(">1. Retirarte");
                System.out.println(">2. Continuar sin usar poción");
                // Si aun tiene pociones, añadimos la tercera opción de usar y continuar
                if (pocionesHeroe>0) System.out.println(">3. Continuar usando una poción");
                //recogemos la opciçon del usuario
                String opcionSeleccionada = sc.nextLine();
                while (opcionSeleccionada!= "1" && opcionSeleccionada!= "2" && opcionSeleccionada!= "3"){
                    System.out.println("Opción inválida! Inténtalo de nuevo");
                    opcionSeleccionada = sc.nextLine();
                }
                //marcamos seHaRetirado en función del valor elegido
                seHaRetirado= (opcionSeleccionada=="1"); 
                //si se quiere retirar (opción 2) no hay que hacer nada, salimos del bucle y punto
                //pero si la opción es 3, bebemos antes la poción
                if (opcionSeleccionada=="3") {
                    // primero buscamos una poción en el arrayList de inventario y la eliminamos
                    // y reducimos el contador de pociones disponibles
                    inventario.remove(INVENTARIO_POCION);
                    pocionesHeroe--;
                    //y luego curamos al personaje
                    vidaHeroe+= CURACION_POCION;
                }
            }   
        }while (vidaHeroe > 0 && !seHaRetirado);
        
        /* ----------MENSAJES DE FIN DE PARTIDA---------- */
        //Mostramos mensajes de fin de partida
        //TODO: Dejar bonito
        if(vidaHeroe<=0){
            System.out.println(nombreHeroe+" ha sido derrotado!");
        }else{
            System.out.println(nombreHeroe+" ha escapado con vida!");
        }
        //TODO: Dejar bonito
        System.out.println("Enemigos derrotados:"+contadorEnemigos);
        System.out.println("---------- GAME OVER --------------");
        // Se cierra el Scanner porque ya no se necesitan más datos del teclado.
        sc.close();
    }
}
