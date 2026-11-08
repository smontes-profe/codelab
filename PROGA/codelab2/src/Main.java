/*
 * Codelab 2 (Ejercicios resueltos) - Juego de rol por turnos
 * Autor: Sergio Montes
 * Modificaciones realizadas:
 * Añadí validación básica para las opciones y los datos numéricos.
 *  * Implementé encuentros aleatorios con trasgos y trolls, cada uno con su rango de daño.
 *  * Apliqué las dificultades elegidas: 3 pociones en fácil y 2 en difícil.
 * * Añadí el contador de enemigos derrotados y el resumen final.
 * * Evité que la vida superara el máximo o que una poción reviviera al héroe.
 * * Sustituí las notas de la sesión por comentarios explicativos sobre el código.
 */

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Estas constantes reúnen los valores fijos del juego y evitan repetir números sueltos.
        final int VIDA_MAXIMA = 100;
        final int POCIONES_FACIL = 3;
        final int POCIONES_DIFICIL = 2;
        final int CURACION_POCION = 40;
        final int DANIO_MIN_TRASGO = 20;
        final int DANIO_MAX_TRASGO = 35;
        final int DANIO_MIN_TROLL = 40;
        final int DANIO_MAX_TROLL = 60;
        final int DIFICULTAD_FACIL = 1;
        final int DIFICULTAD_DIFICIL = 2;
        final int ENEMIGO_TRASGO = 1;
        final int ACCION_LUCHAR = 1;
        final int ACCION_ESQUIVAR = 2;
        final int ACCION_RETIRARSE = 3;

        Scanner sc = new Scanner(System.in);
        Random aleatorio = new Random();

        System.out.println("=================================================");
        System.out.println("   ⚔️ BIENVENIDO AL REINO DE JAVA THE HUT ⚔️   ");
        System.out.println("=================================================");
        System.out.print("Introduce el nombre de tu héroe: ");
        String nombre = sc.nextLine();

        // Se repite la pregunta hasta recibir un número que corresponda a una dificultad.
        int dificultad = 0;
        while (dificultad != DIFICULTAD_FACIL && dificultad != DIFICULTAD_DIFICIL) {
            System.out.println("\nElige la dificultad:");
            System.out.println(DIFICULTAD_FACIL + ". Fácil (3 pociones)");
            System.out.println(DIFICULTAD_DIFICIL + ". Difícil (2 pociones)");
            System.out.print("Selecciona una opción: ");

            if (sc.hasNextInt()) {
                dificultad = sc.nextInt();
                if (dificultad != DIFICULTAD_FACIL && dificultad != DIFICULTAD_DIFICIL) {
                    System.out.println("Opción no válida. Escribe 1 o 2.");
                }
            } else {
                // Si se escribe texto, lo leemos para poder volver a pedir la opción.
                sc.next();
                System.out.println("Debes escribir un número. Inténtalo de nuevo.");
            }
        }

        // La dificultad cambia el número de pociones; la vida inicial es la misma.
        int vidaHeroe = VIDA_MAXIMA;
        int pocionesHeroe;
        if (dificultad == DIFICULTAD_DIFICIL) {
            pocionesHeroe = POCIONES_DIFICIL;
            System.out.println("Has elegido el camino difícil. ¡Suerte!");
        } else {
            pocionesHeroe = POCIONES_FACIL;
            System.out.println("Has elegido el camino fácil. ¡Buena suerte!");
        }

        // Este contador aumenta cuando el héroe sobrevive al ataque del enemigo.
        int enemigosDerrotados = 0;
        boolean seHaRetirado = false;

        System.out.println("\n+|=========> FICHA DEL HÉROE <=========|+");
        System.out.println("Nombre: " + nombre);
        if (dificultad == DIFICULTAD_DIFICIL) {
            System.out.println("Dificultad: Difícil");
        } else {
            System.out.println("Dificultad: Fácil");
        }
        System.out.println("Salud: " + vidaHeroe + " HP");
        System.out.println("Pociones: " + pocionesHeroe);
        System.out.println("\n+|=========> ¡COMIENZA LA BATALLA! <=========|+");

        // La batalla continúa mientras el héroe tenga vida y no se haya retirado.
        while (vidaHeroe > 0 && !seHaRetirado) {
            System.out.println("\n¿Qué quieres hacer?");
            System.out.println(ACCION_LUCHAR + ". Luchar");
            System.out.println(ACCION_ESQUIVAR + ". Esquivar este turno");
            System.out.println(ACCION_RETIRARSE + ". Retirarte");
            System.out.print("Elige una opción: ");

            // También se comprueba que la acción sea una de las tres opciones del menú.
            int accion = 0;
            while (accion < ACCION_LUCHAR || accion > ACCION_RETIRARSE) {
                if (sc.hasNextInt()) {
                    accion = sc.nextInt();
                    if (accion < ACCION_LUCHAR || accion > ACCION_RETIRARSE) {
                        System.out.println("Opción no válida. Escribe 1, 2 o 3.");
                    }
                } else {
                    sc.next();
                    System.out.println("Debes escribir un número. Inténtalo de nuevo.");
                }
            }

            if (accion == ACCION_ESQUIVAR) {
                System.out.println("Esquivas el combate y pasas al siguiente turno.");
                continue;
            }

            if (accion == ACCION_RETIRARSE) {
                System.out.println("Te retiras de la batalla.");
                seHaRetirado = true;
                continue;
            }

            // Se elige al azar uno de los dos enemigos y se calcula su daño dentro de su rango.
            int enemigo = aleatorio.nextInt(2) + 1;
            String nombreEnemigo;
            int danoEnemigo;
            if (enemigo == ENEMIGO_TRASGO) {
                nombreEnemigo = "Trasgo";
                danoEnemigo = aleatorio.nextInt(DANIO_MAX_TRASGO - DANIO_MIN_TRASGO + 1)
                        + DANIO_MIN_TRASGO;
            } else {
                nombreEnemigo = "Troll";
                danoEnemigo = aleatorio.nextInt(DANIO_MAX_TROLL - DANIO_MIN_TROLL + 1)
                        + DANIO_MIN_TROLL;
            }

            System.out.println("\n¡Un " + nombreEnemigo + " te ataca!");
            vidaHeroe -= danoEnemigo;
            if (vidaHeroe < 0) {
                vidaHeroe = 0;
            }
            System.out.println("Recibes " + danoEnemigo + " de daño. Vida actual: " + vidaHeroe);

            // Si el golpe acaba con el héroe, no puede usar una poción ni derrotar al enemigo.
            if (vidaHeroe <= 0) {
                System.out.println("Has caído en combate. ¡Fin de la aventura!");
                break;
            }

            // Sobrevivir al ataque significa que el héroe ha derrotado a ese enemigo.
            enemigosDerrotados++;
            System.out.println("¡Has derrotado al enemigo!");

            // La poción se usa automáticamente y la vida nunca supera su valor máximo.
            if (pocionesHeroe > 0) {
                vidaHeroe += CURACION_POCION;
                if (vidaHeroe > VIDA_MAXIMA) {
                    vidaHeroe = VIDA_MAXIMA;
                }
                pocionesHeroe--;
                System.out.println("Usas una poción. Pociones restantes: " + pocionesHeroe);
            } else {
                System.out.println("No te quedan pociones.");
            }

            System.out.println("Vida actual: " + vidaHeroe);
            System.out.println("-----------------------------------------------");
        }

        // Al terminar la batalla se informa del resultado y del total de enemigos derrotados.
        if (seHaRetirado) {
            System.out.println("\n" + nombre + " se retira de la aventura.");
        }
        System.out.println("Enemigos derrotados: " + enemigosDerrotados);
        System.out.println("Vida final: " + vidaHeroe + " HP");
        System.out.println("Pociones restantes: " + pocionesHeroe);

        // Se cierra el Scanner porque ya no se necesitan más datos del teclado.
        sc.close();
    }
}
