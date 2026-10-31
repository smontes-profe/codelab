public class Personaje {

    private static final int VIDA_MAXIMA = 100;
    private static final int POCIONES_INICIO = 2;
    private static final int CURACION_POCION = 30;

    private final String nombre;
    private int vida;
    private int pociones;

    public int getVida() { return vida; }
    public int getPociones() { return pociones; }

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.vida = VIDA_MAXIMA;
        this.pociones = POCIONES_INICIO;
    }

    public String getNombre() {
        return nombre;
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

    public boolean estaVivo(){
        return vida>0;
    }
}
