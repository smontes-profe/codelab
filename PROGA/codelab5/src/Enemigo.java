public class Enemigo {
    private String nombre;
    private int tipo;
    private int vida;
    private int danio;

    public int getVida(){ return vida;}
    public boolean estaVivo(){ return vida>0;}
    public int getDanio(){ return danio;}
    public String getNombre(){ return nombre;}

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
    public void recibirDanio(int danioTemporal){
        vida=Math.max(0,vida-danioTemporal);
    }
}
