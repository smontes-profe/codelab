public class Main {
    public static void main(String[] args) {
        Personaje[] expedicion = {
                new Guerrero("Bruna", 100, 18),
                new Mago("Izan", 75, 12)
        };

        char[][] mapa = {
                {'S', '.', '#', '.', 'T'},
                {'.', '.', '#', '.', '.'},
                {'#', '.', '.', '.', 'E'}
        };

        System.out.println("=== EQUIPO DE LA EXPEDICIÓN ===");
        for (int i = 0; i < expedicion.length; i++) {
            System.out.print((i + 1) + ". ");
            expedicion[i].mostrarFicha();
            System.out.println("Ataque: " + expedicion[i].atacar());
        }

        System.out.println("\n=== MAPA DEL LABERINTO ===");
        System.out.println("S: inicio | E: salida | #: muro | T: tesoro | .: pasillo");
        mostrarMapa(mapa);

        int[] tesoro = buscarCasilla(mapa, 'T');
        if (tesoro != null) {
            System.out.println("Se ha localizado el tesoro en la fila "
                    + tesoro[0] + ", columna " + tesoro[1] + ".");
        } else {
            System.out.println("No se ha encontrado ningún tesoro.");
        }
    }

    private static void mostrarMapa(char[][] mapa) {
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int columna = 0; columna < mapa[fila].length; columna++) {
                System.out.print(mapa[fila][columna] + " ");
            }
            System.out.println();
        }
    }

    private static int[] buscarCasilla(char[][] mapa, char objetivo) {
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int columna = 0; columna < mapa[fila].length; columna++) {
                if (mapa[fila][columna] == objetivo) {
                    return new int[]{fila, columna};
                }
            }
        }
        return null;
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

    public int atacar() {
        return 8;
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

    @Override
    public int atacar() {
        return 20;
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

    @Override
    public int atacar() {
        return 14;
    }
}
