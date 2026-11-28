package modelo;

// Cada opción reúne el nombre visible y la forma de crear su personaje.
public enum Profesion {
    GUERRERO("Guerrero") {
        @Override
        public Personaje crearPersonaje(String nombre, RegistroJuego registro) {
            return new Guerrero(nombre, registro);
        }
    },
    MAGO("Mago") {
        @Override
        public Personaje crearPersonaje(String nombre, RegistroJuego registro) {
            return new Mago(nombre, registro);
        }
    };

    private final String nombre;

    Profesion(String nombre) {
        this.nombre = nombre;
    }

    // Cada profesión debe indicar qué tipo de personaje crea.
    public abstract Personaje crearPersonaje(String nombre, RegistroJuego registro);

    // Swing usa este texto para mostrar la profesión.
    @Override
    public String toString() {
        return nombre;
    }
}
