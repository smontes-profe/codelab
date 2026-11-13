
// Podría dejar los posibles elementos de la mochila y loot en GameConfig,
// pero tenerlos en un enum facilita muchísimo las cosas
// Si quiero añadir un nuevo posible elemento, añado una más aquí, y luego monto la lógica 
// en GameLoop

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
