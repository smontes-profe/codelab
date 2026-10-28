/*
Código usado durante la sesión en vivo

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
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
=======
// ***************** BLOQUE 1: Variables y Tipos (El Despertar).Casting/Concatenación **********************
        //Scanner sc = new Scanner(System.in);

        String nombre = "John Snow";
>>>>>>> bc9e8a4dabdd80e9ede2d49939756d7e8b274c74
        int vida = 100;
        int pociones = 3;
        boolean estaVivo = true;

<<<<<<< HEAD
        // TAREA 2: Hacer una ficha del héroe más "atractiva"
        System.out.println("+|===> FICHA DEL HÉROE <===!+");
        System.out.println("┌─────────────────────────────┐");
        System.out.println("│ Nombre: " + nombre);
        System.out.println("│ Salud: " + vida);
        System.out.println("│ Inventario: " + pociones);
        System.out.println("└─────────────────────────────┘");
        System.out.println("*******************************");
=======
        System.out.println("=== FICHA DEL HÉROE ===");

        System.out.println("Nombre: " + nombre);
        System.out.println("Salud actual: " + vida + " HP");
        System.out.println("Salud: " + nombre + vida);
        System.out.println("Inventario: " + pociones + " pociones.");
        System.out.println("*********************");
>>>>>>> bc9e8a4dabdd80e9ede2d49939756d7e8b274c74

        // ********************* BLOQUE 2: Operadores y Lógica - Asiganción
        // ******************************
        System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");

<<<<<<< HEAD
        // TAREA 3: Que el daño que hace el trasgo sea aleatorio entre 10 y 30. (Pista:
        // Math.random())
=======
        //esta forma de asignar y restar a la vez (operadores de asignación compuesta) la conocéis?
>>>>>>> bc9e8a4dabdd80e9ede2d49939756d7e8b274c74
        vida -= 85; // El héroe queda con 15 de vida
        System.out.println("Recibes 85 de daño. Vida restante: " + vida);

        // ***************** BLOQUE 3: Condicionales IF (El Umbral)
        // *************************
        // ¿Se puede cambiar por un WHILE?
        if (vida <= 0) {
            System.out.println("HAS MUERTO. Game Over.");
            estaVivo = false;
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

*/

///Código con los ejercicios propuestos resueltos con buenas prácticas
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // *********** BLOQUE 1: Constantes y Variables (El Despertar)
        // **********************
        // Constantes para la vida inicial según dificultad
        // las buenas prácticas indican que las constantes se escriben en mayúsculas y
        // con guiones bajos
        // y se declaran al principio del método main o de la clase
        // después posibles variables que se van a usar en el programa
        // y funciones (creo que aun no las habéis visto, pero para que vayáis tomando
        // nota)
        // Alguien sabría decirme razones para ello?

        final int VIDA_FACIL = 150;
        final int VIDA_NORMAL = 100;
        final int VIDA_DIFICIL = 50;
        final int POCIONES_INICIALES = 3;
        final int DANIO_MIN = 10;
        final int DANIO_MAX = 30;
        final int UMBRAL_AUTOCURACION = 20; // umbral de vida para activar la cura automática
        final int VIDA_POCION = 50; // vida que recupera el héroe al usar una poción

        // también podría ser recomendable declarar aquí como constantes
        // todas las cadenas de texto que se van a mostrar al usuario, para poder
        // modificarlas fácilmente en un futuro, sin tener que buscar en todo el código
        // dónde se usan, aunque en programas más grandes, lo más recomendable es tener
        // un archivo de configuración con todas las constantes y parámetros del
        // programa

        // podría ser interesante declarar el scanner aquí y cerrarlo al final del main,
        // no?
        // aunque la verdad tampoco es necesario ponerse tan "quisquilloso" con esto,
        // pero es buena práctica
        Scanner scanner;

        // Variable para almacenar la vida del héroe según la dificultad elegida
        // Aunque yo estoy comentando aquí todo por mor del ejercicio,
        // es muccho mejor usar nombres que ni siquiera necesiten comentarios para
        // entender su función, no creéis?
        int vidaHeroe;
        // aunque igual tampoco hay que pasarse, como sería el siguiente ejemplo:
        // int pocionesQueTieneElHeroeActualmente;
        int pocionesHeroe;
        int danio; // Variable para almacenar el daño recibido por el héroe

        // ahora mismo tener las variables declaradas aquí arriba,
        // tan lejos de cuándo se usan algunas, no tiene muchosentido,
        // pero en programas más grandes sí que es buena práctica declarar
        // todas las variables al principio del método main o de la clase
        // además cuando veáis cómo funcionan las funciones, tendrá más sentido aún.

        // inicializamos el scanner y pedimos al usuario el nombre del personaje
        scanner = new Scanner(System.in);
        System.out.println("¿Cuál es el nombre del personaje?");
        String nombre = scanner.nextLine();

        // TAREA 1 (Resuelta): Además de pedir el nombre del personaje, pedir al usuario
        // dificultad (fácil, normal, difícil) y mostrarla en la ficha del héroe.
        // Además, con dificultad fácil, el héroe empieza con 150 de vida,
        // con dificultad normal 100 y con dificultad difícil 50.
        System.out.println("¿Qué dificultad eliges? (fácil, normal, difícil):");
        String dificultad = scanner.nextLine();
        // mejor cerrar el scanner al final del main o aquí que sabemos que ya no lo
        // vamos a usar más?
        scanner.close();

        // Ojo que esta es difícil: por qué no funcionaría el siguiente código?
        // if (dificultad == "fácil" || dificultad == "facil") {
        // y tengo que hacerlo con .equals() en lugar de == para comparar cadenas de
        // texto, por qué?
        if (dificultad.equals("fácil") || dificultad.equals("facil")) {
            vidaHeroe = VIDA_FACIL;
        } else if (dificultad.equals("difícil") || dificultad.equals("dificil")) {
            vidaHeroe = VIDA_DIFICIL;
        } else {
            // aquí hacemos un poquito de "trampa" y si el usuario introduce cualquier otra
            // cosa,
            // le damos la dificultad normal, por simplificar.
            // en el futuro veremos muchas mejres opciones para gestionar este tipo de
            // situaciones.
            vidaHeroe = VIDA_NORMAL;
            dificultad = "normal"; // Valor por defecto si introduce otra opción
        }
        pocionesHeroe = POCIONES_INICIALES;

        // TAREA 2 (Resuelta): Hacer una ficha del héroe más "atractiva" (mostrando
        // también la dificultad)
        System.out.println("\n+|=========> FICHA DEL HÉROE <=========|+");
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│ Nombre:      " + nombre);
        System.out.println("│ Dificultad:  " + dificultad);
        System.out.println("│ Salud:       " + vidaHeroe + " HP");
        System.out.println("│ Inventario:  " + pocionesHeroe + " poción(es)");
        System.out.println("└───────────────────────────────────────┘");
        System.out.println("*****************************************");

        // ********************* BLOQUE 2: Operadores y Lógica - Asiganción
        // ******************************
        System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");

        // TAREA 3 (Resuelta): Que el daño que hace el trasgo sea aleatorio entre 10 y
        // Generamos un entero aleatorio en el rango [10, 30] usando casting (int)
        danio = (int) (Math.random() * (DANIO_MAX - DANIO_MIN + 1)) + DANIO_MIN;
        // disminuir la vida del héroe según el daño recibido con una operación de
        // asignación compuesta
        vidaHeroe -= danio;
        System.out.println("Recibes " + danio + " de daño. Vida restante: " + vidaHeroe);

        // ***************** BLOQUE 3: Condicionales IF (El Umbral)
        // *************************
        // ¿Se puede cambiar por un WHILE?
        if (vidaHeroe <= 0) {
            System.out.println("HAS MUERTO. Game Over.");
        } else {
            System.out.println("Aturdido, pero sigues en pie...");
        }

        // **********************BLOQUE 3: El Desafío (Cura Crítica)
        // *************************
        // Objetivo: Si vida < 20 Y tiene pociones, curar automáticamente.

        System.out.println("\n--- Comprobando estado crítico ---");

        if (vidaHeroe < UMBRAL_AUTOCURACION && pocionesHeroe > 0) {
            System.out.println("¡Alerta! Vida baja. Usando poción automáticamente...");
            vidaHeroe = VIDA_POCION;
            pocionesHeroe--; // Gastamos una poción: pociones = pociones - 1
            System.out.println(">>> Curación exitosa. Nueva vida: " + vidaHeroe);
            System.out.println(">>> Pociones restantes: " + pocionesHeroe);
        } else if (vidaHeroe < UMBRAL_AUTOCURACION && pocionesHeroe == 0) {
            System.out.println("¡ESTÁS EN PELIGRO! No te quedan pociones.");
        } else {
            System.out.println("Estado estable. No es necesario gastar recursos.");
        }
        System.out.println("\n=== FIN DE LA SESIÓN 1 ===");
    }

}