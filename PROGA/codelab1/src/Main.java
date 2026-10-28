
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
        scanner.close();

        // TAREA 1: Además de pedir el nombre del personaje,pedir al usuario
        // dificultad (fácil, normal, difícil) y mostrarla en la ficha del héroe.
        // Además, con dificultad fácil, el héroe empieza con 150 de vida,
        // con dificultad normal 100 y con dificultad difícil 50.

        // String nombre = "John Snow";
        int vida = 100;
        int pociones = 3;

        // TAREA 2: Hacer una ficha del héroe más "atractiva"
        System.out.println("+|===> FICHA DEL HÉROE <===!+");
        System.out.println("┌─────────────────────────────┐");
        System.out.println("│ Nombre: " + nombre);
        System.out.println("│ Salud: " + vida);
        System.out.println("│ Inventario: " + pociones);
        System.out.println("└─────────────────────────────┘");
        System.out.println("*******************************");

        // ********************* BLOQUE 2: Operadores y Lógica - Asiganción
        // ******************************
        System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");

        // TAREA 3: Que el daño que hace el trasgo sea aleatorio entre 10 y 30. (Pista:
        // Math.random())
        vida -= 85; // El héroe queda con 15 de vida
        System.out.println("Recibes 85 de daño. Vida restante: " + vida);

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