package modelo;

/** Objetos que pueden guardarse en la mochila del personaje. */
public enum Objeto {
    POCION_CURACION("Poción de curación"),
    POCION_ATAQUE("Poción de ataque"),
    POCION_DEFENSA("Poción de defensa");

    private final String nombre;

    Objeto(String nombre) {
        this.nombre = nombre;
    }

    /** @return el nombre legible del objeto */
    public String getNombre() {
        return nombre;
    }
}
