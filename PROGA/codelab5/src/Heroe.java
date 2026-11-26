import java.util.ArrayList;

public class Heroe {
    private ArrayList<String> inventario;
    private String nombre;
    private int vida;
    public int enemigosDerrotados;

    /**
     * Consulta el nombre elegido para el héroe.
     * @return nombre del héroe
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Consulta los puntos de vida restantes.
     * @return vida actual del héroe
     */
    public int getVida() {
        return vida;
    }

    /**
     * Comprueba si el héroe tiene vida para seguir jugando.
     * @return {@code true} si la vida es mayor que cero
     */
    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Consulta los objetos que lleva el héroe.
     * @return lista modificable del inventario actual
     */
    public ArrayList<String> getInventario() {
        return inventario;
    }

    /**
     * Comprueba si queda alguna poción en el inventario.
     * @return {@code true} si hay al menos una poción
     */
    public boolean tienePociones() {
        return getPocionesDisponibles() > 0;
    }

    /**
     * Crea un héroe con la vida máxima y los objetos iniciales configurados.
     * @param nuevoNombre nombre que tendrá el héroe
     */
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

    /**
     * Calcula el daño de la espada aplicando todos los anillos de ataque.
     * @return daño actual del héroe, truncado a un número entero
     */
    public int getDanio() {
        double modificadorAtaque = 1;
        for (String objeto : inventario) {
            if (objeto.equals(GameConfig.INVENTARIO_ANILLO_ATAQUE)) {
                modificadorAtaque *= GameConfig.INCREMENTO_ANILLO_ATAQUE;
            }
        }
        return (int) (GameConfig.DANIO_ESPADA * modificadorAtaque);
    }

    /**
     * Ataca al enemigo con el daño actual del héroe.
     * @param enemigoActual enemigo que recibe el ataque
     * @return daño calculado para el ataque
     */
    public int hacerDanio(Enemigo enemigoActual) {
        int danio = getDanio();
        enemigoActual.recibirDanio(danio);
        return danio;
    }

    /**
     * Reduce la vida del héroe tras aplicar la defensa del escudo y los anillos.
     * La vida no baja de cero.
     * @param danioEnemigo daño del ataque antes de aplicar la defensa
     * @return daño calculado después de aplicar la defensa
     */
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

    /**
     * Consume una poción y recupera vida sin superar el máximo.
     * Si no hay pociones, no cambia el inventario ni la vida.
     */
    public void beberPocion() {
        if (tienePociones()) {
            inventario.remove(GameConfig.INVENTARIO_POCION);
            vida = Math.min(GameConfig.VIDA_MAXIMA, vida + GameConfig.CURACION_POCION);
        }
    }

    /**
     * Elige una recompensa según las probabilidades configuradas y la añade al inventario.
     * @return nombre del objeto obtenido, o una cadena vacía si ningún límite coincide
     */
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
