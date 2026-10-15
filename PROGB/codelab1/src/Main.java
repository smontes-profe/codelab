import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        final int MAX_ENEMIGOS = 5;

        Personaje personaje = crearPersonaje();

        System.out.println("=== PREPARANDO LA EXPEDICIÓN ===");
        personaje.mostrarFicha();
º
        for (int i = 0; i < MAX_ENEMIGOS && !personaje.estaMuerto(); i++) {
            generarEncuentro(personaje);
        }

        finalizarExpidicion(personaje);
    }

    public static void finalizarExpidicion(Personaje personaje) {
        System.out.println("============FIN DE LA EXPEDICIÓN============");
        if (personaje.estaMuerto()) {
            System.out.println(personaje.getNombre() + " ha muerto en la expedición. :( ++RIP++ ");
        } else {
            System.out.println("¡¡¡¡" + personaje.getNombre() + " ha sobrevivido a la expedición!!! ): ¡ENHORABUENA!");
        }
    }

    public static Personaje crearPersonaje() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre de tu personaje: ");
        String nombre = sc.nextLine();
        sc.close();
        return new Personaje(nombre);
    }

    public static void generarEncuentro(Personaje personaje) {
        Enemigo enemigo = new Enemigo();
        System.out.println("+===> ¡¡¡NUEVO ENCUENTRO!!! <===+");
        System.out.println("Un " + enemigo.getTipo() + " sorprende a " + personaje.getNombre());
        enemigo.hacerDanio(personaje);
        personaje.usarPocion();
        personaje.mostrarFicha();
    }
}

class Personaje {

    private static final int VIDA_MAXIMA = 100;
    private static final int POCIONES_INICIALES = 3;

    private String nombre;
    private int vida;
    private int pociones;

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.vida = VIDA_MAXIMA;
        this.pociones = POCIONES_INICIALES;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaMuerto() {
        return vida <= 0;
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

    public void usarPocion() {
        if (pociones > 0 && vida != VIDA_MAXIMA) {
            pociones--;
            vida = Math.min(VIDA_MAXIMA, vida + 30);
            System.out.println(nombre + " usa una poción.");
            return;
        }
        System.out.println("Warning!!! No le quedan pociones a " + nombre);
    }
}

class Enemigo {
    public enum Tipo {
        TRASGO,
        TROLL
    }

    private final int DANIO_TROLL = 50;
    private final int DANIO_TRASGO = 25;

    private Tipo tipo;

    public Enemigo() {
        tipo = Tipo.values()[(int) (Math.random() * Tipo.values().length)];
    }

    public void hacerDanio(Personaje personaje) {
        int danio = tipo == Tipo.TROLL ? DANIO_TROLL : DANIO_TRASGO;
        personaje.recibirDanio(danio);
    }

    public String getTipo() {
        return tipo.name();
    }
}
