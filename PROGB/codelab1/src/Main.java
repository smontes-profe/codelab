//Para nuestra aventura, vamos a querer tener un personaje.
//Características: vida, pociones y nombre.
//No puede tener menos de 0 de vida, ni más de 100 de vida.
//Las pociones curan 30 puntos de vida.
//El personaje tendrá dos acciones basicas: atacar y defenderse.
//Al atacar, recibiremos un daño aleatorio entre 0 y 20.
//Al defendernos, recibiremos un daño aleatorio entre 0 y 10.

//Para implementar todo esto, lo primero que tenemos que hacer es 
//Crear la clase Personaje, con sus atributos y métodos. 

class Personaje {

    private static final int VIDA_MAXIMA = 100;
    private static final int POCIONES_INICIO = 2;
    private static final int CURACION_POCION = 30;

    private final String nombre;
    private int vida;
    private int pociones;

    public String getNombre() {
        return nombre;
    }

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.vida = VIDA_MAXIMA;
        this.pociones = POCIONES_INICIO;
    }

    public void mostrarFicha() {
        System.out.println("Personaje: " + nombre);
        System.out.println("Vida: " + vida);
        System.out.println("Pociones: " + pociones);
        System.out.println();
    }

    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    public boolean usarPocion() {
        if (pociones == 0 || vida == VIDA_MAXIMA)
            return false;
        pociones--;
        vida = Math.min(VIDA_MAXIMA, vida + CURACION_POCION);
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        Personaje miPersonaje = new Personaje("Luna");
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
