

//Para nuestra aventura, vamos a querer tener un personaje.
//Características: vida, pociones y nombre.
//No puede tener menos de 0 de vida, ni más de 100 de vida.
//Las pociones curan 30 puntos de vida.
//El personaje tendrá dos acciones basicas: atacar y defenderse.
//Al atacar, recibiremos un daño aleatorio entre 0 y 20.
//Al defendernos, recibiremos un daño aleatorio entre 0 y 10.

//Para implementar todo esto, lo primero que tenemos que hacer es 
//Crear la clase Personaje, con sus atributos y métodos. 

import java.util.Scanner;

class Personaje {

    // Quién me sabe decir qué difernecia hay entre declarar VIDA_MAXIMA
    // como final, y además static
    // qué hace cada cosa???
    private static final int VIDA_MAXIMA = 100;
    private static final int POCIONES_INICIO = 2;
    private static final int CURACION_POCION = 30;

    // debería poner "nombre" todo en Mayúsculas aunque no sea static?
    // es "obligatorio"?
    private final String nombre;
    private int vida;
    private int pociones;

    public String getNombre(){
        return nombre;
    }

    //éstos parámetros nombre, vida, pociones... 
    //se pasan por valor o por referencia?
    //sabéis la diferencia?

    public Personaje(String nombre){
        // Qué hace el parámetro THIS?
        // puedo no usar "this" en este contexto?
        this.nombre = nombre;
        this.vida = VIDA_MAXIMA;
        this.pociones = POCIONES_INICIO;
    }

    // por qué es public? no podría ser private??
    // este tipo de función cuando pertenece a una clase se llama...?
    /**
     * Imprime la ficha del personaje usando System out print
     */
    public void mostrarFicha() {
        System.out.println("Personaje: " + nombre);
        System.out.println("Vida: " + vida);
        System.out.println("Pociones: " + pociones);
        System.out.println();
        // Debería poner aquí "return"? Por qué?¿
    }

    // Los usuarios del castellano tenemos un problema con la "Ñ"
    // o la transoformamos en "n" o buscamos una alternativa
    // muchos programadores hispanohablantes prefieren escribir su código en inglés
    // para evitar estos problemas y también por otras razones
    /**
     * Resta de vida al personaje el daño recibido
     * @param cantidad daño que hay que restar de la vida del personaje
     */
    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    // Qué creéis que pasa si vida llega a 0?
    public boolean usarPocion() {
        // es buena práctica meter un "return" dentro de un "if"
        // No es el caso, pero si tuviera que encadenar varios if/else
        // qué es mejor práctica? encadenarlos o lanzar returns con cada if?
        if (pociones == 0 || vida == VIDA_MAXIMA) return false;

        pociones--;
        // qué estoy haciendo aquí?
        vida = Math.min(VIDA_MAXIMA, vida + CURACION_POCION);
        // qué pasa si uso 3 pociones seguidas y tengo 10 de vida?
        return true;
    }
}

// Ahora que ya tengo mi clase Personaje creada
// va a ser más fácil trabajar mi clase main
// de momento vamos a dejar el juego muy snecillo:
// que cree un personaje en función de los parámetros que le pasemos
// y luego vamos a imprimir por pantalla su ficha.
// Al final, vamos a simular un par de acciones para ir entrando en materia.
// Y por ultimo, vamos a mostrar la ficha del personaje para ver si ha cambiado.
public class Main {
    public static void main(String[] args) {
        // cómo se llama el método que crea un objeto?
        // Sabéis qué son "magic numbers" o "números locos"??
        // Qué sería lo más correcto si "Luna", 80 y 2 son constantes que voy a usar
        // en varios sitios??
        
        Personaje miPersonaje = new Personaje("Luna");

        System.out.println("=== PREPARANDO LA EXPEDICIÓN ===");
        miPersonaje.mostrarFicha();

        System.out.println("Un trasgo sorprende a " + miPersonaje.getNombre());
        // aquí lo mismo: sería interesante definir el daño que hace el trasgo
        // en lugar de poner 25 directamente en el código?
        miPersonaje.recibirDanio(25);
        miPersonaje.mostrarFicha();

        // este método usarPocion devuelve un Boolean
        // pero además también hace "cosas" antes de devolverlo
        // es correcto? es buena práctica?


        if (miPersonaje.usarPocion()) {
            System.out.println(miPersonaje.getNombre()+" usa una poción.");
        } else {
            System.out.println("No quedan pociones.");
        }
        miPersonaje.mostrarFicha();
        // Cómo veis esta forma de trabajar? Os parece mejor que
        // tener un montón de variables sueltas?
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
