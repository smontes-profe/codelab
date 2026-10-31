public abstract class Enemigo {

    private final String nombre;
    private final int danio;
    private int vida;

    //En esta clase hemos tocado poco
    // pero es un cambio fundamental
    // hemos prescindido de usar enum para usar herencia
    // ahora mismo puede parecer un cambio simplemente de opción
    // pero en el futuro nos puede dar mucho juego si quermeos que un Troll haga cosas distintas de un Trasgo

    protected Enemigo(String nombre, int vida, int danio) {
        this.nombre = nombre;
        this.vida = vida;
        this.danio = danio;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDanio(int cantidad) {
        if (cantidad > 0) {
            vida = Math.max(0, vida - cantidad);
        }
    }

    public void hacerDanio(Personaje objetivo) {
        objetivo.recibirDanio(danio);
        System.out.printf("👾 ¡El %s ataca a %s y le hace %d de daño!%n",
                nombre, objetivo.getNombre(), danio);
    }
}
