
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

        // ********************* BLOQUE 2: Operadores y Lógica - Asiganción ******************************
        System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");

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

        while (vida > 0 && estaVivo) {
            System.out.println("\nUn enemigo te ataca...");

            // Generamos daño aleatorio entre 10 y 30 para que dure la pelea

            int golpeEnemigo2 = new Random().nextInt(21) + 10;
            vida -= golpeEnemigo2;

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
            }

            [poke1, pok2, poke4]
            Thread.sleep(1000);

        }
    }

}