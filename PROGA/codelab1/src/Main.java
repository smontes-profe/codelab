
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ***************** BLOQUE 1: Variables y Tipos (El
        // Despertar).Casting/Concatenación **********************
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Cuál es el nombre del personaje?");
        String nombre = scanner.nextLine();

        // TAREA 1 (Resuelta): Además de pedir el nombre del personaje, pedir al usuario
        // dificultad (fácil, normal, difícil) y mostrarla en la ficha del héroe.
        // Además, con dificultad fácil, el héroe empieza con 150 de vida,
        // con dificultad normal 100 y con dificultad difícil 50.
        System.out.println("¿Qué dificultad eliges? (fácil, normal, difícil):");
        String dificultad = scanner.nextLine();
        scanner.close();

        // Constantes para la vida inicial según dificultad
        final int VIDA_FACIL = 150;
        final int VIDA_NORMAL = 100;
        final int VIDA_DIFICIL = 50;

        int vida;
        if (dificultad.equalsIgnoreCase("fácil") || dificultad.equalsIgnoreCase("facil")) {
            vida = VIDA_FACIL;
        } else if (dificultad.equalsIgnoreCase("difícil") || dificultad.equalsIgnoreCase("dificil")) {
            vida = VIDA_DIFICIL;
        } else {
            vida = VIDA_NORMAL;
            dificultad = "normal"; // Valor por defecto si introduce otra opción
        }

        // String nombre = "John Snow";
        int pociones = 3;

        // TAREA 2 (Resuelta): Hacer una ficha del héroe más "atractiva" (mostrando
        // también la dificultad)
        System.out.println("\n+|=========> FICHA DEL HÉROE <=========|+");
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│ Nombre:      " + nombre);
        System.out.println("│ Dificultad:  " + dificultad);
        System.out.println("│ Salud:       " + vida + " HP");
        System.out.println("│ Inventario:  " + pociones + " poción(es)");
        System.out.println("└───────────────────────────────────────┘");
        System.out.println("*****************************************");

        // ********************* BLOQUE 2: Operadores y Lógica - Asiganción
        // ******************************
        System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");

        // TAREA 3 (Resuelta): Que el daño que hace el trasgo sea aleatorio entre 10 y
        // 30. (Pista: Math.random())
        final int DANIO_MIN = 10;
        final int DANIO_MAX = 30;
        // Generamos un entero aleatorio en el rango [10, 30] usando casting (int)
        int danio = (int) (Math.random() * (DANIO_MAX - DANIO_MIN + 1)) + DANIO_MIN;
        vida -= danio;
        System.out.println("Recibes " + danio + " de daño. Vida restante: " + vida);

        // ***************** BLOQUE 3: Condicionales IF (El Umbral)
        // *************************
        // ¿Se puede cambiar por un WHILE?
        if (vida <= 0) {
            System.out.println("HAS MUERTO. Game Over.");
        } else {
            System.out.println("Aturdido, pero sigues en pie...");
        }

        // **********************BLOQUE 3: El Desafío (Cura Crítica)
        // *************************
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

        System.out.println("\n=== FIN DE LA SESIÓN 1 ===");
    }

}