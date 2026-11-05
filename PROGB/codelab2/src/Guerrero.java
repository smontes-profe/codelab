public class Guerrero extends Personaje {

    private static final int VIDA_MAXIMA = 100;
    private static final int DANIO = 20;
    private static final String CLASE_STRING="Guerrero";

    public Guerrero(String nombre) {
        //do some stuff
        super(nombre,CLASE_STRING, VIDA_MAXIMA, DANIO);
    }

    //con la instrucción @Override sobreescribimos el método recibirDanio
    // ya que gracias al escudo, el guerrero reduce el daño recibido.
    @Override
    public void recibirDanio(int cantidad) {
        int danioReducido = reducirDanio(cantidad);
        if (cantidad > 0) {
            //mensajito para dar ambientación
            // %n añade un salto de línea
            System.out.printf(" El escudo reduce el daño de %d a %d.%n", cantidad, danioReducido);
        }
        // aquí, una vez aplicada la reducción de daño, llamamos al método original del padre. 
        // como debe ser, ahorrñandonos duplicidad y facilitando modificación de código.
        super.recibirDanio(danioReducido);
    }

    private int reducirDanio(int cantidad) {
        //Básicamente: 
        // -Si es 0, reduce 0. Si no:
        // lo reduce a la mitad. 
        // Como son números enteros, la división descarta los decimales. 
        // Sumar 1 antes de dividir hace que los daños impares se redondeen hacia arriba: 
        // 25 se convierte en 13; 24, en 12. Si el daño es 1, queda en 1.
        return cantidad > 0 ? (cantidad + 1) / 2 : cantidad;
    }
}
