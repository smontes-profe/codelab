public class Main {
    public static void main(String[] args) {
        Personaje personaje = new Personaje("Luna", 80, 2);

        System.out.println("=== PREPARANDO LA EXPEDICIÓN ===");
        personaje.mostrarFicha();

        System.out.println("Un trasgo sorprende a Luna.");
        personaje.recibirDanio(25);
        personaje.mostrarFicha();

        if (personaje.usarPocion()) {
            System.out.println("Luna usa una poción.");
        } else {
            System.out.println("No quedan pociones.");
        }
        personaje.mostrarFicha();
    }
}

class Personaje {
    private static final int VIDA_MAXIMA = 100;

    private final String nombre;
    private int vida;
    private int pociones;

    public Personaje(String nombre, int vida, int pociones) {
        this.nombre = nombre;
        this.vida = vida;
        this.pociones = pociones;
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
        if (pociones == 0 || vida == VIDA_MAXIMA) {
            return false;
        }

        pociones--;
        vida = Math.min(VIDA_MAXIMA, vida + 30);
        return true;
    }
}
