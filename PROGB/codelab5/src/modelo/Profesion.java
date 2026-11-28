package modelo;

/** Cada profesión reúne su nombre visible y la forma de crear su personaje. */
public enum Profesion {
    GUERRERO("Guerrero") {
        /** {@inheritDoc} */
        @Override
        public Personaje crearPersonaje(String nombre, RegistroJuego registro) {
            return new Guerrero(nombre, registro);
        }
    },
    MAGO("Mago") {
        /** {@inheritDoc} */
        @Override
        public Personaje crearPersonaje(String nombre, RegistroJuego registro) {
            return new Mago(nombre, registro);
        }
    };

    private final String nombre;

    Profesion(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Crea un personaje de esta profesión.
     *
     * @param nombre nombre del personaje
     * @param registro destinatario de sus mensajes
     * @return personaje creado
     */
    public abstract Personaje crearPersonaje(String nombre, RegistroJuego registro);

    /** @return nombre de la profesión que se muestra en la ventana */
    @Override
    public String toString() {
        return nombre;
    }
}
