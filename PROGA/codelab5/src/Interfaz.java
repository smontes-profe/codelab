import java.util.InputMismatchException;
import java.util.Scanner;

public class Interfaz {
    private Scanner scanner;
    private String colorMensajes;
    private Heroe personaje;
    private Enemigo enemigoActual;

    private static Interfaz instancia;
    public static Interfaz getInstancia(){
        if(instancia==null){
            instancia = new Interfaz();
        }
        return instancia;
    }

    private Interfaz(){
        scanner=new Scanner(System.in);
        colorMensajes=GameConfig.COLOR_INFORMATIVO;
    }
    public void salir(){
        scanner.close();
    }
    public void imprimirLinea(){
        System.out.println(colorMensajes + "=================================================");
    }
    public void imprimirMensaje(String mensaje){
        System.out.println(colorMensajes + mensaje);
    }
    public void imprimirCabecera(String mensaje){
        System.out.println(colorMensajes + "\n+|=========> " + mensaje + " <=========|+");
    }
    public void imprimirFicha(){
        imprimirCabecera("FICHA DEL HÉROE");
        imprimirMensaje("Nombre: " + personaje.getNombre());
        imprimirMensaje("Salud: " + personaje.getVida());
        imprimirMensaje("Inventario: ");
        for (String objeto : personaje.getInventario()) {
            imprimirMensaje("- " + objeto);
        }
    }

    public Heroe inicializarHeroe(){
        colorMensajes=GameConfig.COLOR_INFORMATIVO;
        imprimirLinea();
        imprimirCabecera("       BIENVENIDO AL REINO DE JAVA THE HUT       ");
        imprimirLinea();
        imprimirMensaje("Introduce el nombre de tu héroe: ");
        personaje =new Heroe(scanner.nextLine());
        imprimirCabecera("¡COMIENZA LA AVENTURA!");
        return personaje;
    }
    public boolean mostrarGameOver(){
        if(!personaje.estaVivo()){
            colorMensajes=GameConfig.COLOR_ENEMIGO;
            imprimirCabecera(personaje.getNombre()+" ha sido derrotado! ");
        }else{
            colorMensajes= GameConfig.COLOR_JUGADOR;
            imprimirCabecera(personaje.getNombre()+" ha escapado con vida!");
        }
        colorMensajes = GameConfig.COLOR_INFORMATIVO;
        imprimirMensaje("Enemigos derrotados:"+personaje.enemigosDerrotados);
        imprimirCabecera("GAME OVER"); 
        //Opciones de reinicio
        colorMensajes=GameConfig.COLOR_OPCIONES;
        imprimirLinea();
        imprimirCabecera("¿Quieres empezar de nuevo?");
        imprimirMensaje(">1. Sí");
        imprimirMensaje(">2. No");
        boolean continuarJugando=(validarOpcion(2)==1);
        scanner.nextLine();
        if(!continuarJugando) imprimirCabecera("¡HASTA OTRA!");
        return continuarJugando;
    }
    private int validarOpcion(int numValidas){
        int opcion=0;
        try {
            opcion = scanner.nextInt();
            if( (opcion > numValidas) || (opcion<1) ){
                imprimirMensaje("Opción inválida. Elije un número entre 1 y "+numValidas);
                return validarOpcion(numValidas);
            }
            return opcion;
        } catch (InputMismatchException e) {
            imprimirMensaje("Opción inválida. Elije un número entre 1 y "+numValidas);
            scanner.next();
            return validarOpcion(numValidas);
        } 
    }
    public void iniciarNuevoEncuentro(Enemigo nuevoEnemigo){
        enemigoActual=nuevoEnemigo;
        imprimirCabecera("NUEVO ENCUENTRO");
        imprimirMensaje("\n¡Un " + enemigoActual.getNombre() + " salvaje aparece!");
    }
    public int mostrarOpcionesEncuentro(){
        colorMensajes=GameConfig.COLOR_OPCIONES;
        int numValidas=2;
        imprimirMensaje("Qué quieres hacer?");
        imprimirMensaje(">1. Retirarte");
        imprimirMensaje(">2. Continuar sin usar poción");
        if (personaje.tienePociones()) {
            imprimirMensaje(">3. Continuar usando una poción");
            numValidas=3;
        }
        return validarOpcion(numValidas);
    }
    public void mostrarDanioAlEnemigo(int danio){
        colorMensajes=GameConfig.COLOR_JUGADOR;
        imprimirMensaje(colorMensajes + "\n¡" + personaje.getNombre() + " ataca al " + enemigoActual.getNombre()
         + " con la espada y le hace " + danio + " de daño!");
    }
    public void mostrarDanioAlHeroe(int danioTemporal){
        colorMensajes=GameConfig.COLOR_ENEMIGO;
        imprimirMensaje("Vida del " + enemigoActual.getNombre() + ": " + enemigoActual.getVida() + " HP");
        imprimirMensaje(enemigoActual.getNombre() + " ataca a " + personaje.getNombre() 
        + " y le hace " + danioTemporal + " de daño!");
        imprimirMensaje("Vida de " + personaje.getNombre() + ": " + personaje.getVida() + " HP");
    }
    public void mostrarLoot(String objetoLoot){
        imprimirMensaje(personaje.getNombre() + " ha derrotado al " + enemigoActual.getNombre() 
        + "! El enemigo suelta un objeto.");
        imprimirMensaje("¡Has conseguido " + objetoLoot + "! Se añade a tu inventario.");
    }
    public void finalizarCombatLoop(){
        try {
            Thread.sleep(GameConfig.TIEMPO_ESPERA);
        } catch (InterruptedException e) {
            // Aquí no hace falta hacer nada
        }
    }
}
