// La forma más elegante de crear el tipo de enemigo probablemente sea hacer un enum.
// Enum es una clase en sí con todas las de la ley, por lo que podemos definir atributos
// y métodos propios de cada tipo.
public enum TipoEnemigo {
    // Defino las constantes de mi enum. Cada una de ellas es una instancia de TipoEnemigo
    TROLL(50),
    TRASGO(25);

    // Atributos que tendrá cada tipo de enemigo
    private final int danio;

    // Constructor de cada tipo de enemigo
    TipoEnemigo(int danio) {
        this.danio = danio;
    }

    public int getDanio() {
        return danio;
    }
}
