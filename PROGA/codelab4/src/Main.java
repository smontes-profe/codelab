

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
// ***************** BLOQUE 1: Variables y Tipos **********************
        Scanner sc = new Scanner(System.in);

        //**** ORGANIZAMOS EL CÓDIGO****
        int vida = 100;
        int pociones = 3;
        boolean estaVivo = true;

         /* *********************Si quiere aumentar la dificultad ponga una vida hasta 50 y elija una sola pócima **********************/


        System.out.println("==========================================");
        System.out.println("   ⚔️ BIENVENIDO AL REINO DE JAVA ⚔️   ");
        System.out.println("==========================================");

        System.out.print("\n➤ Introduce el nombre de tu héroe: ");
        String nombre = sc.nextLine();

        //*****METEMOS Try/Catch ******
        // El objetivo es validar la entrada de datos//

        try{
            System.out.println("\n¿Cómo prefieres iniciar tu gesta?");
            System.out.println("1. Modo Estándar (100 Vida / 3 Pociones)");
            System.out.println("2. Modo Difícil (Elegir vida hasta 50 / 1 Poción)");
            System.out.print("Selecciona una opción (1 o 2): ");

            int opcion = sc.nextInt();

            if (opcion == 2) {

                System.out.print("➤ Elige tu vida inicial (máximo 50): ");
                vida = sc.nextInt();
                pociones = 1;
                System.out.println("⚠️ Has elegido el camino del guerrero. ¡Suerte!");
            } else {

            System.out.println("✅ Partida cargada con valores estándar.");
            }
        } catch (InputMismatchException e) {
            // Este bloque se ejecuta SOLO si hubo un error de tipo de dato

            System.out.println("\n❌ ERROR: ¡Has conjurado un hechizo erróneo! (No introdujiste un número).");
            System.out.println("⚠️ Como castigo, empiezas con 10 de vida por no saber leer.");
            vida = 10;
            pociones = 0;
            sc.nextLine(); // Limpiamos el buffer del scanner para evitar bucles infinitos
        }


        // --- 2. FICHA DEL HÉROE "CHULA" ---
        System.out.println("       📜 FICHA DE PERSONAJE 📜       ");
        System.out.println(nombre);
        System.out.println(vida);
        System.out.println("pociones " + pociones);
        System.out.println("      ¡La aventura comienza ahora!      \n");

        // ********************* BLOQUE 2: Operadores y Lógica - Asiganción ******************************
        while (vida>=0 && estaVivo) {


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


            // ***************** BLOQUE 3: Bucles (La Batalla Continúa) **********************
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
            //****** EJEMPLO DE ARRAY *****//
                String miestring="";
                String []ejeplito = new String[2];

            String[] miarray = {"PEPE", "MARIA", "MANUEL", "ANA"};

            for (int i=0; i<miarray.length;i++){
                System.out.print(miarray[i]);
            }


            // Bloque 5 ************Agregamos objetos a mi mochila**********
            // Declaración del inventario
            // ****************** EJEMPLO DE ARRAYLIST ************
            ArrayList<String> inventario = new ArrayList<>();


            // Añadimos objetos iniciales
            inventario.add("Mapa arrugado");
            inventario.add("Daga de hierro");

            // ***** OTRO EJEMPLO DE CÓMO UTILIZAR EL TRY/CATCH
            // *** OTRO TIPO DE ERROR ArithmeticException 10/0
            try {
                System.out.println("\nIntentando acceder al objeto secreto en el índice 5 de tu mochila...");
                // Esto provocará un IndexOutOfBoundsException porque el array solo tiene 2 elementos
                System.out.println(inventario.get(5));
            } catch (IndexOutOfBoundsException error) {
                System.out.println("🚫 INFO: Intentaste buscar un objeto que no existe en tu mochila.");
            }

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


            // MENSAJE DE TEXTO DE UN COMPAÑERO EN EL CAMPUS
            // Definimos el texto y la velocidad
            String mensajeFinal = "¡Has sido derrotado! ☠️";
            int velocidad = 200;


            for (int i = 0; i < mensajeFinal.length(); i++) {
                System.out.print(mensajeFinal.charAt(i));

                // OTRO TRY/CATCH
                try {
                    Thread.sleep(velocidad);
                } catch (InterruptedException e) {
                    // ESTA EXCEPCIÓN en los hilos es necesario porque
                    // El sistema necesita saber qué hacer si, mientras el programa espera esos milisegundos
                    // para imprimir la siguiente letra, algo decide detener el proceso de golpe.
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(); // Salto de línea final
        }

    }

}