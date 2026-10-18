import java.util.Scanner;

public class EjercicioVideojuego {

  public static void main(String[] args) {
    int vida = 100;
    int pociones = 3;
    boolean estarVivo = true;
    final int DANOTRASGO = 85;
    final int CURACION = 35;
    Scanner scanner = new Scanner(System.in);
    System.out.println("¿Cuál es el nombre del personaje?");
    String nombre = scanner.nextLine();
    // TAREA 1: Además de pedir el nombre del personaje,pedir al usuario
    // dificultad (fácil, normal, difícil) y mostrarla en la ficha del héroe.
    // Además, con dificultad fácil, el héroe empieza con 150 de vida,
    // con dificultad normal 100 y con dificultad difícil 50
    System.out.println("Elige la dificultad: \n1-Fácil \n2-Normal \n3-Difícil");
    int dificultad = scanner.nextInt(); // usuario escribe 1, 2 o 3 y se asigna la dificultad con los case de dentro del
    // switch
    scanner.close();
    String tipoDificultad; // creo una variable única para las 3 frases de la dificultad
    // creo switch para separar los 3 niveles de dificultad en funcion de la
    // elección anterior
    switch (dificultad) { // uso la variable dificultad porqué es un int y es la que diferencia los
                          // distintos niveles de dificultad, tipoDificultad es el resultado de cada case,
                          // al igual que la variable vida
      case 1:
        tipoDificultad = "Fácil. No eres muy valiente, ¿verdad?";
        vida = 150;
        break;
      case 2:
        tipoDificultad = "Normal. Adéntrate en tu aventura";
        vida = 100;
        break;
      case 3:
        tipoDificultad = "Difícil. Te crees muy valiente, ¿eh?";
        vida = 50;
        break;
      default:
        tipoDificultad = "Dificultad no vàlida.";
        break;
    }
    System.out.println("+|===> FICHA DEL HÉROE <===!+");
    System.out.println("┌──────────────────────────────────────────────────┐");
    System.out.println("│ Nombre: " + nombre);
    System.out.println("| Dificultad: " + tipoDificultad);
    System.out.println("│ Salud: " + vida);
    System.out.println("│ Inventario: " + pociones);
    System.out.println("└──────────────────────────────────────────────────┘");
    System.out.println("*******************************");
    // ********************* BLOQUE 2: Operadores y Lógica - Asiganción
    // ******************************
    System.out.println("\n¡Un Trasgo te embosca y te asesta un golpe!");
    vida -= 85; // vida = vida-85
    System.out.println("Recibes " + DANOTRASGO + " de daño. Vida restante: " + vida);
    // ***************** BLOQUE 3: Condicionales IF (El Umbral)
    // *************************
    // TAREA 3: Que el daño que hace el trasgo sea aleatorio entre 10 y 30. (Pista:
    // Math.random())
    double danoTrasgo = 10 + Math.random() * 20;
    if (vida <= 0) {
      System.out.println("HAS MUERTO. Game Over.");
      estarVivo = false;
    } else {
      System.out.println("Aturdido, pero sigues en pie...");
      // **********************BLOQUE 3: El Desafío (Cura Crítica)
      // *************************
      // Objetivo: Si vida < 20 Y tiene pociones, curar automáticamente.
      System.out.println("\n--- Comprobando estado crítico ---");
      if ((vida < 20) && (pociones > 0)) {
        System.out.println("¡Alerta! Vida baja. Usando poción automáticamente...");
        vida += CURACION;
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
}
