public class Main {
    public static void main(String[] args) {
        Guerrero guerrero = new Guerrero("Bruna", 100, 18);
        Mago mago = new Mago("Izan", 75, 12);

        System.out.println("=== LA EXPEDICIÓN RECLUTA AVENTUREROS ===");
        guerrero.mostrarFicha();
        guerrero.mostrarArmadura();

        mago.mostrarFicha();
        mago.meditar();

        System.out.println("Ambos personajes heredan nombre, vida y mostrarFicha() de Personaje.");
    }
}

class Personaje {
    private final String nombre;
    private int vida;

    public Personaje(String nombre, int vida) {
        this.nombre = nombre;
        this.vida = vida;
    }

    public String getNombre() {
        return nombre;
    }

    public void mostrarFicha() {
        System.out.println(nombre + " | Vida: " + vida);
    }

    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }
}

class Guerrero extends Personaje {
    private final int puntosArmadura;

    public Guerrero(String nombre, int vida, int puntosArmadura) {
        super(nombre, vida);
        this.puntosArmadura = puntosArmadura;
    }

    public void mostrarArmadura() {
        System.out.println(getNombre() + " tiene " + puntosArmadura + " puntos de armadura.");
    }
}

class Mago extends Personaje {
    private int mana;

    public Mago(String nombre, int vida, int mana) {
        super(nombre, vida);
        this.mana = mana;
    }

    public void meditar() {
        mana += 10;
        System.out.println(getNombre() + " medita y recupera mana. Mana: " + mana);
    }
}
