public class Guerrero extends Personaje {

    private static final int VIDA_MAXIMA = 100;
    private static final int DANIO = 20;
    private static final String CLASE_STRING="Guerrero";

    public Guerrero(String nombre) {
        super(nombre,CLASE_STRING, VIDA_MAXIMA, DANIO);
    }

    @Override
    public void recibirDanio(int cantidad) {
        int danioReducido = reducirDanio(cantidad);
        if (cantidad > 0) {
            System.out.printf(" El escudo reduce el daño de %d a %d.%n", cantidad, danioReducido);
        }
        super.recibirDanio(danioReducido);
    }

    private int reducirDanio(int cantidad) {
        return cantidad > 0 ? (cantidad + 1) / 2 : cantidad;
    }
}
