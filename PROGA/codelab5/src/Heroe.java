import java.util.ArrayList;

public class Heroe {
    private ArrayList<String> inventario;
    private String nombre;
    private int vida;
    public int enemigosDerrotados;

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public ArrayList<String> getInventario() {
        return inventario;
    }

    public boolean tienePociones() {
        return getPocionesDisponibles() > 0;
    }

    public Heroe(String nuevoNombre) {
        nombre = nuevoNombre;
        vida = GameConfig.VIDA_MAXIMA;
        inventario = new ArrayList<>();
        for (String objeto : GameConfig.INVENTARIO_INICIAL) {
            inventario.add(objeto);
        }
        enemigosDerrotados = 0;
    }

    private int getPocionesDisponibles() {
        int contPociones = 0;
        for (String objeto : inventario) {
            if (objeto.equals(GameConfig.INVENTARIO_POCION))
                contPociones++;
        }
        return contPociones;
    }

    public int getDanio() {
        double modificadorAtaque = 1;
        for (String objeto : inventario) {
            if (objeto.equals(GameConfig.INVENTARIO_ANILLO_ATAQUE)) {
                modificadorAtaque *= GameConfig.INCREMENTO_ANILLO_ATAQUE;
            }
        }
        return (int) (GameConfig.DANIO_ESPADA * modificadorAtaque);
    }

    public int hacerDanio(Enemigo enemigoActual) {
        int danio = getDanio();
        enemigoActual.recibirDanio(danio);
        return danio;
    }

    public int recibirDanio(int danioEnemigo) {
        double modificadorDefensa = 1 + GameConfig.DEFENSA_ESCUDO;
        for (String objeto : inventario) {
            if (objeto.equals(GameConfig.INVENTARIO_ANILLO_DEFENSA)) {
                modificadorDefensa *= GameConfig.INCREMENTO_ANILLO_DEFENSA;
            }
        }
        int DanoTemporal = (int) (danioEnemigo / modificadorDefensa);
        vida = Math.max(0, vida - DanoTemporal);
        return DanoTemporal;
    }

    public void beberPocion() {
        if (tienePociones()) {
            inventario.remove(GameConfig.INVENTARIO_POCION);
            vida = Math.min(GameConfig.VIDA_MAXIMA, vida + GameConfig.CURACION_POCION);
        }
    }

    public String getNuevoLoot() {
        String objetoLoot = "";
        int lootRandom = GameConfig.aleatorio.nextInt(100) + 1;
        for (int i = 0; i < GameConfig.LOOT_PROBABILIDADES.length; i++) {
            if (lootRandom <= GameConfig.LOOT_PROBABILIDADES[i]) {
                objetoLoot = GameConfig.LOOT[i];
                inventario.add(objetoLoot);
                break;
            }
        }
        return objetoLoot;
    }
}
