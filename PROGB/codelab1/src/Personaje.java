public class Personaje {

    private static final int VIDA_MAXIMA = 100;
    private static final int POCIONES_INICIO = 2;
    private static final int CURACION_POCION = 30;

    private final String nombre;
    private int vida;
    private int pociones;

    // he creado dos getters aquí para devolver la vida y las pociones restantes
    // ya que probablemente necesite acceder a ellas desde fuera
    public int getVida() { return vida; }
    public int getPociones() { return pociones; }
    public String getNombre() { return nombre; }
    public boolean estaVivo(){ return vida>0; }

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.vida = VIDA_MAXIMA;
        this.pociones = POCIONES_INICIO;
    }

    
    /**
     * Muestra por pantalla la ficha del personaje, con su nombre, vida y pociones restantes
     */
    public void mostrarFicha() {
        System.out.println("Personaje: " + nombre);
        System.out.println("Vida: " + vida);
        System.out.println("Pociones: " + pociones);
        System.out.println();
    }

    /**
     * Recibe daño y actualiza la vida del personaje, asegurándose de que no baje de 0
     * @param cantidad la cantidad de daño a recibir
     */
    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    /**
     * Usa una poción para curar al personaje. Si no hay pociones o la vida está al máximo, no hace nada.
     * @return true si se usó una poción y se curó al personaje, false en caso contrario
     */
    public boolean usarPocion() {
        if (pociones == 0 || vida == VIDA_MAXIMA)
            return false;
        pociones--;
        vida = Math.min(VIDA_MAXIMA, vida + CURACION_POCION);
        return true;
    }

    
}
