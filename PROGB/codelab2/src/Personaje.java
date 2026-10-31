public abstract class Personaje {

    private static final int POCIONES_INICIO = 2;
    private static final int CURACION_POCION = 30;

    private final String nombre;
    private String clase;
    private final int vidaMaxima;
    private final int danio;
    private int vida;
    private int pociones;

    public int getVida() { return vida; }
    public int getPociones() { return pociones; }
    public String getClase() { return clase; }
    public String getNombre() { return nombre; }

    protected Personaje(String nombre, String clase,  int vidaMaxima, int danio) {
        this.nombre = nombre;
        this.clase = clase;
        this.vidaMaxima = vidaMaxima;
        this.danio = danio;
        this.vida = vidaMaxima;
        this.pociones = POCIONES_INICIO;
    }

    public void mostrarFicha() {
        // Podríamos habernos ahorrado tener la propiedad "clase" usando esto:
        // System.out.println("Personaje: " + nombre + " (" + getClass().getSimpleName() + ")");
        // pero no estaba seguro de si lo habíais visto
        System.out.println("┌────────────── 🧙 FICHA DEL HÉROE ──────────────┐");
        System.out.println("  👤 " + nombre + " — " + clase);
        System.out.println("  ❤️  Vida: " + vida + "/" + vidaMaxima);
        System.out.println("  🧪 Pociones: " + pociones);
        System.out.println("└────────────────────────────────────────────────┘");
        System.out.println();
    }

    public void atacar(Enemigo objetivo) {
        //Ya hemos usado esta forma de emplear printf
        //cada "%loquesea" se reempleza por la variable que le corresponde por orden
        //%s para Strings, %n para Numbers
        System.out.printf("⚔️ %s golpea a %s y le hace %d de daño.%n",
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
