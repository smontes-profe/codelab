// Para nuestra aventura, vamos a tener un personaje.
// Características: vida, pociones y nombre.
// No puede tener menos de 0 de vida, ni más de 100 de vida.
// Las pociones curan 30 puntos de vida.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Creación del personaje usando Scanner
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el nombre de tu personaje: ");
        // podríamos crear una constante o una variable para guardar el nombre
        // pero en este caso da igual ya que solo se va a usar una vez
        Personaje miPersonaje = new Personaje(sc.nextLine());
        System.out.println("=== PREPARANDO LA EXPEDICIÓN ===");
        miPersonaje.mostrarFicha();
        System.out.println("Un trasgo sorprende a " + miPersonaje.getNombre());
        miPersonaje.recibirDanio(25);
        miPersonaje.mostrarFicha();
        if (miPersonaje.usarPocion()) {
            System.out.println(miPersonaje.getNombre() + " usa una poción.");
        } else {
            System.out.println("No quedan pociones.");
        }
        miPersonaje.mostrarFicha();

        sc.close();
    }
}

// DEBERES PARA LA PRÓXIMA SESIÓN:
// 1. Pedir al usuario que introduzca un nombre para su personaje.
// 2. Crear una clase "Enemigo" que tenga:
// -Tipo: puede ser trasgo o troll
// -Método constructor:
// -al generer un nuevo enemigo determina al azar el tipo
// -Método "hacerDanio" que reciba como parámetro un objeto de tipo Personaje.
// -Dicho método hará un daño determinado al Personaje que recibe como parámetro
// -Los trolls hacen 50 de daño, los trasgos, 25.
// 3. Modifica el código para que, en lugar de enfrentarse a un trasgo "por
// decreto"
// como tenemos ahora, que el jugador tenga 4 enfrentamientos con enemigos,
// usando la clase Enemigo que acabamos de diseñar.
// -Por cada enfrentamiento, llamaremos al método hacerDanio de cada Enemigo,
// pasando como parámetro el personaje del jugador.
// después de cada enfrentamiento, intentaremos que el personaje
// use una poción, y mostramos la ficha actualizada.
// Intentad usar buenas prácticas, si véis que necesitáis
// algún método más para ello, no dudes en crearlo, aunque yo no lo pida
// explcítamente.
// Si tenéis dudas, preguntad ahora o por el foro.
