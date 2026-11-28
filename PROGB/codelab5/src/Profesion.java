// Cada opción reúne el nombre visible y la forma de crear su personaje.
public enum Profesion {
    GUERRERO("Guerrero") {
        @Override
        public Personaje crearPersonaje(String nombre) {
            return new Guerrero(nombre);
        }
    },
    MAGO("Mago") {
        @Override
        public Personaje crearPersonaje(String nombre) {
            return new Mago(nombre);
        }
    };

    private final String nombre;

    Profesion(String nombre) {
        this.nombre = nombre;
    }

    // Cada profesión debe indicar qué tipo de personaje crea.
    public abstract Personaje crearPersonaje(String nombre);

    // Swing y la terminal usarán este texto para mostrar la profesión.
    @Override
    public String toString() {
        return nombre;
    }
}
