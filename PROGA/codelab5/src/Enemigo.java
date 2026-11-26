public class Enemigo {
    private String nombre;
    private int tipo;
    private int vida;
    private int danio;

    /**
     * Consulta los puntos de vida restantes.
     * @return vida actual del enemigo
     */
    public int getVida(){ return vida;}

    /**
     * Comprueba si el enemigo puede seguir combatiendo.
     * @return {@code true} si su vida es mayor que cero
     */
    public boolean estaVivo(){ return vida>0;}

    /**
     * Consulta el daño que causa el enemigo antes de aplicar defensas.
     * @return daño base del enemigo
     */
    public int getDanio(){ return danio;}

    /**
     * Consulta el nombre del tipo de enemigo.
     * @return nombre del enemigo
     */
    public String getNombre(){ return nombre;}

    /** Crea aleatoriamente un trasgo o un troll con sus características iniciales. */
    public Enemigo(){
        tipo = GameConfig.aleatorio.nextInt(2) + 1;
        if (tipo == GameConfig.ENEMIGO_TRASGO) {   
            nombre = GameConfig.NOMBRE_TRASGO;
            danio = GameConfig.DANIO_TRASGO;
            vida = GameConfig.VIDA_TRASGO;
        } else {
            nombre = GameConfig.NOMBRE_TROLL;
            danio = GameConfig.DANIO_TROLL;
            vida = GameConfig.VIDA_TROLL;
        }
    }
    /**
     * Reduce la vida del enemigo sin que baje de cero.
     * @param danioTemporal daño que recibe
     */
    public void recibirDanio(int danioTemporal){
        vida=Math.max(0,vida-danioTemporal);
    }
}
