
/*
 * Codelab 3 - Arrays y ArrayLists
 * Fecha: 11-11-2026
 * Autor: Sergio Montes
 * En el codelab de hoy centraremos la atención en el uso de arrays y ArrayLists,
 * que ya habéis visto a nivel teórico, pondremos en práctica connuestro juego de rol.
 * Qué haremos en esta sesión:
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

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
// ***************** BLOQUE 1: Variables y Tipos **********************
        Scanner sc = new Scanner(System.in);

        /*****************1 DEBERES ¿Cómo quiero que sea la interactuación con mi usuario? O por defecto
         o se lo pido por consola **************************
         /* *********************Si quiere aumentar la dificultad ponga una vida hasta 50 y elija una sola pócima **********************/


        System.out.println("==========================================");
        System.out.println("   ⚔️ BIENVENIDO AL REINO DE JAVA ⚔️   ");
        System.out.println("==========================================");

        System.out.print("\n➤ Introduce el nombre de tu héroe: ");
        String nombre = sc.nextLine();

        System.out.println("\n¿Cómo prefieres iniciar tu gesta?");
        System.out.println("1. Modo Estándar (100 Vida / 3 Pociones)");
        System.out.println("2. Modo Difícil (Elegir vida hasta 50 / 1 Poción)");
        System.out.print("Selecciona una opción (1 o 2): ");

        int opcion = sc.nextInt();
        int vida;
        int pociones;

        /*switch (opcion) {
            case 1: System.out.println("La opcion facil");
            case 2: ......
        }*/

        if (opcion == 2) {

            System.out.print("➤ Elige tu vida inicial (máximo 50): ");
            vida = sc.nextInt();
            pociones = 1;
            System.out.println("⚠️ Has elegido el camino del guerrero. ¡Suerte!");
        } else {
            // Valores por defecto
            vida = 100;
            pociones = 3;
            System.out.println("✅ Partida cargada con valores estándar.");
        }
        boolean estaVivo=true;

        // --- 2. FICHA DEL HÉROE "CHULA" ---
        System.out.println("       📜 FICHA DE PERSONAJE 📜       ");
        System.out.println(nombre);
        System.out.println(vida);
        System.out.println("pociones " + pociones);
        System.out.println("      ¡La aventura comienza ahora!      \n");


        while (vida>=0 && estaVivo) {
            // ********************* BLOQUE 2: Operadores y Lógica - Asiganción ******************************
            //System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");

            //**************************************3 DEBER Random --> random en java (entre 50=min y un 85=max?)

        /*int golpeEnemigo = new Random().nextInt(36) + 50;

        vida = vida - golpeEnemigo;

        System.out.println("Recibes " + golpeEnemigo +" de daño. Vida restante: " + vida);

        if (vida <= 0) {
            System.out.println("¡" + nombre + " ha caído en combate!");
            estaVivo = false;
        }

    */


            // **********************BLOQUE 3: El Desafío (Cura Crítica) *************************
            // Objetivo: Si vida < 20 Y tiene pociones, curar automáticamente.

            System.out.println("\n--- Comprobando estado crítico ---");

            if (vida < 20 && pociones > 0) {
                System.out.println("¡Alerta! Vida baja. Usando poción automáticamente...");
                vida = 50;
                pociones--; // Gastamos una poción: pociones = pociones - 1

                System.out.println(">>> Curación exitosa. Nueva vida: " + vida);
                System.out.println(">>> Pociones restantes: " + pociones);

            } else if (vida < 20 && pociones == 0) {
                System.out.println("¡ESTÁS EN PELIGRO! No te quedan pociones.");
            } else {
                System.out.println("Estado estable. No es necesario gastar recursos.");
            }


            // ***************** BLOQUE 4: Bucles (La Batalla Continúa) **********************
            System.out.println("\n--- ¡COMIENZA LA BATALLA REAL! ---");


                System.out.println("\nUn enemigo te ataca...");

                // Generamos daño aleatorio entre 10 y 30 para que dure la pelea

                int golpeEnemigo2 = new Random().nextInt(21) + 10;
                vida -= golpeEnemigo2;
                //vida = vida - golpeEnemigo2;

                System.out.println("Recibes " + golpeEnemigo2 + " de daño. Vida actual: " + vida);

                // Lógica de curación automática (tu Bloque 3 dentro del bucle)
                if (vida < 25 && pociones > 0) {
                    System.out.println("¡Emergencia! Usando poción...");
                    vida += 40; // Curamos 40 de vida
                    pociones--;
                    System.out.println("Pociones restantes: " + pociones + " | Vida: " + vida);
                }

                // Comprobamos si el héroe ha caído
                if (vida <= 0) {
                    System.out.println("¡" + nombre + " ha caído en combate!");
                    estaVivo = false;
                    break;
                }


                Thread.sleep(1000);

                String miestring="";
                String []ejeplito = new String[2];

            String[] miarray = {"PEPE", "MARIA", "MANUEL", "ANA"};

            for (int i=0; i<miarray.length;i++){
                System.out.print(miarray[i]);
            }


            // Bloque 5 ************Agregamos objetos a mi mochila**********
            // Declaración del inventario
            ArrayList<String> inventario = new ArrayList<>();



            // Añadimos objetos iniciales
            inventario.add("Mapa arrugado");
            inventario.add("Daga de hierro");

            System.out.println("\n✨ ¡El enemigo ha soltado un 'Amuleto de Java'! ¿Deseas recogerlo?");
            System.out.println("1. Sí | 2. No");
            int decision = sc.nextInt();

            switch (decision) {
                case 1:
                    inventario.add("Amuleto de Java");
                    System.out.println("📦 Has guardado el Amuleto en tu mochila.");
                    break;
                case 2:
                    System.out.println("Has dejado pasar la oportunidad");
            }

            System.out.println("\n=== 🎒 TU MOCHILA ===");
            for (int i = 0; i < inventario.size(); i++) {
                System.out.println((i + 1) + ". " + inventario.get(i));
            }

            // en busqueda del cofre misterioso *************BLOQUE 6*******

            System.out.println("🎁 ¡Has encontrado un cofre misterioso en un rincón oscuro!");
            System.out.println("1. Abrirlo con cuidado");
            System.out.println("2. Ignorarlo y seguir adelante");
            System.out.print("¿Qué decides?: ");

            int eleccionCofre = sc.nextInt();

            switch (eleccionCofre) {
                case 1:
                    System.out.println("Abres la tapa lentamente... 🥁");
                    Thread.sleep(1500); // Pausa dramática

                    // Generamos un destino del 1 al 3
                    int destino = new Random().nextInt(3) + 1;

                    switch (destino) {
                        case 1:
                            System.out.println("💰 ¡TESORO! Encuentras una 'Espada de Código Limpio'.");
                            inventario.add("Espada de Código Limpio");
                            for (int i = 0; i < inventario.size(); i++) {
                                System.out.println((i + 1) + ". " + inventario.get(i));
                            }
                            break;

                        case 2:
                            System.out.println("👾 ¡ES UN MÍMICO! El cofre tiene dientes y te muerde.");
                            int susto = 15;
                            vida -= susto;
                            System.out.println("Pierdes " + susto + " de vida. Vida actual: " + vida);
                            break;

                        case 3:
                            System.out.println("💨 El cofre estaba lleno de gas somnífero. No encuentras nada.");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("Prudente... prefieres no arriesgar tu pellejo hoy.");
                    break;

                default:
                    System.out.println("Te quedas mirando el cofre tanto tiempo que un murciélago te golpea.");
                    vida -= 5;
                    break;
            }
        }

    }

}