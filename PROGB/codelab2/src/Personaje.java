public abstract class Personaje {

    private static final int POCIONES_INICIO = 2;
    private static final int CURACION_POCION = 30;

    private final String nombre;
    private String profesion;
    private final int vidaMaxima;
    private final int danio;
    private int vida;
    private int pociones;

    public int getVida() { return vida; }
    public int getPociones() { return pociones; }
    public String getProfesion() { return profesion; }
    public String getNombre() { return nombre; }

    protected Personaje(String nombre, String miProfesion,  int vidaMaxima, int danio) {
        this.nombre = nombre;
        this.profesion = miProfesion;
        this.vidaMaxima = vidaMaxima;
        this.danio = danio;
        this.vida = vidaMaxima;
        this.pociones = POCIONES_INICIO;
    }

    public void mostrarFicha() {
        System.out.println("┌──────────────  FICHA DEL HÉROE ──────────────┐");
        System.out.println("   " + nombre + " — " + profesion);
        System.out.println("    Vida: " + vida + "/" + vidaMaxima);
        System.out.println("   Pociones: " + pociones);
        System.out.println("└────────────────────────────────────────────────┘");
        System.out.println();
    }
    
    public void atacar(Enemigo objetivo) {
        System.out.printf(" %s golpea a %s y le hace %d de daño.%n",
                nombre, objetivo.getNombre(), danio);
        objetivo.recibirDanio(danio);
    }

    public boolean atacarADistancia(Enemigo objetivo) {
        return false;
    }

    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    public boolean usarPocion() {
        if (pociones == 0 || vida == vidaMaxima)
            return false;
        pociones--;
        vida = Math.min(vidaMaxima, vida + CURACION_POCION);
        return true;
    }

    public boolean estaVivo() {
        return vida > 0;
    }
}
