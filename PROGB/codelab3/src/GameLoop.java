import java.util.Random;
import java.util.Scanner;

public class GameLoop {

    private final Scanner scanner;
    private final Random random;
    private Personaje miPersonaje;

    /**
     * Crea el flujo del juego usando el generador aleatorio predeterminado.
     *
     * @param scanner lector de las decisiones del jugador
     */
    public GameLoop(Scanner scanner) {
        this(scanner, new Random());
    }

    GameLoop(Scanner scanner, Random random) {
        this.scanner = scanner;
        this.random = random;
    }

    /**
     * Ejecuta partidas hasta que el jugador decide dejar de jugar.
     *
     * @throws InterruptedException si se interrumpe una pausa entre turnos
     */
    public void run() throws InterruptedException {
        // Lo único reseñable es: por qué lanza una InterruptedException?
        // Lo veremos al final cuando veamos el uso de Thread.sleep
        boolean jugarDeNuevo;
        do {
            jugarDeNuevo = jugarPartida();
        } while (jugarDeNuevo);
    }

    private boolean jugarPartida() throws InterruptedException {
        System.out.println();
        // Aquí podéis ver qué fácil es usar la clase enum ColorTerminal
        // pasándole después del punto el tipo de mensaje y luego usando el método imprimirLinea
        ColorTerminal.INFORMACION.imprimirLinea("╔══════════════════════════════════════╗");
        ColorTerminal.INFORMACION.imprimirLinea("║             LEYENDAS DEL CÓDIGO      ║ ");
        ColorTerminal.INFORMACION.imprimirLinea("╚══════════════════════════════════════╝");
        ColorTerminal.OPCION.imprimir(" Introduce el nombre de tu personaje: ");
        String nombre = scanner.nextLine();

        System.out.println();
        ColorTerminal.OPCION.imprimirLinea("Elige tu profesión:");
        ColorTerminal.OPCION.imprimirLinea("  1  Guerrero ");
        ColorTerminal.OPCION.imprimirLinea("  2  Mago     ");
        String profesion = scanner.nextLine();
        while (!"1".equals(profesion) && !"2".equals(profesion)) {
            ColorTerminal.ERROR.imprimirLinea("XX Opción no válida. Escribe 1 para Guerrero o 2 para Mago.");
            profesion = scanner.nextLine();
        }
        miPersonaje = "1".equals(profesion) ? new Guerrero(nombre) : new Mago(nombre);

        System.out.println();
        ColorTerminal.INFORMACION.imprimirLinea("════════════   PREPARANDO LA EXPEDICIÓN   ════════════");
        miPersonaje.mostrarFicha();

        // Hemos quitado el límite de enfrentamientos, y en su lugar añadimos este contador 
        // para mostrar al final cuántos enemigos ha superado el jugador
        // antes de retirarse o morir
        int enfrentamientosSuperados = 0;
        boolean continuarAventura = true;
        while (miPersonaje.estaVivo() && continuarAventura) {
            enfrentamientosSuperados++;

            // Esta parte del bucle principal es casi idéntico a como lo teníamos
            // simplemente adapatado a la refactorización que hemos hecho
            System.out.println();
            ColorTerminal.INFORMACION.imprimirLinea("═══════════   ENFRENTAMIENTO "
                    + enfrentamientosSuperados + "  ═══════════");

            Enemigo enemigoActual = crearEnemigo();
            boolean enemigoALejos = random.nextBoolean();
            if (enemigoALejos) {
                ColorTerminal.ENEMIGO.imprimirLinea(" ¡Un " + enemigoActual.getNombre() + " aparece a lo lejos!");
            } else {
                ColorTerminal.ENEMIGO.imprimirLinea(" ¡Un " + enemigoActual.getNombre() + " aparece frente a "
                        + miPersonaje.getNombre() + "!");
            }
            // Aquí es donde introducimos la novedad: darle la opción al jugador
            // de usar un objeto del inventario
            elegirObjetoParaEncuentro(enemigoActual);

            if (enemigoALejos) {
                if (!miPersonaje.atacarADistancia(enemigoActual)) {
                    ColorTerminal.JUGADOR.imprimirLinea(" El guerrero espera: ¡solo puede luchar cuerpo a cuerpo!");
                }
                if (enemigoActual.estaVivo() && miPersonaje.estaVivo()) {
                    // El Trasgo puede atacar desde lejos; los demás enemigos no tienen esta habilidad.
                    enemigoActual.atacarADistancia(miPersonaje);
                }
                if (enemigoActual.estaVivo() && miPersonaje.estaVivo()) {
                    pausaEntreTurnos();
                    ColorTerminal.INFORMACION.imprimirLinea(" El enemigo se acerca... ¡ahora está cuerpo a cuerpo!");
                }
            }

            while (miPersonaje.estaVivo() && enemigoActual.estaVivo()) {
                miPersonaje.atacar(enemigoActual);
                if (enemigoActual.estaVivo()) {
                    enemigoActual.hacerDanio(miPersonaje);
                }
                if (enemigoActual.estaVivo()) {
                    // El Troll regenera vida al final de cada ronda de combate.
                    int vidaRecuperada = enemigoActual.regenerar();
                    if (vidaRecuperada > 0) {
                        ColorTerminal.ENEMIGO.imprimirLinea(" El " + enemigoActual.getNombre()
                                + " regenera " + vidaRecuperada + " puntos de vida.");
                    }
                }
                if (miPersonaje.estaVivo() && enemigoActual.estaVivo()) {
                    pausaEntreTurnos();
                }
            }

            if (!miPersonaje.estaVivo()) {
                miPersonaje.finalizarEfectosDeEncuentro();
                System.out.println();
                ColorTerminal.ENEMIGO.imprimirLinea(" ═══════════════ FIN DE LA AVENTURA ═══════════════ ☠️");
                ColorTerminal.INFORMACION.imprimirLinea("Has sobrevivido a "
                        + (enfrentamientosSuperados - 1) + " enfrentamiento(s).");
                return finDePartida();
            }

            ColorTerminal.JUGADOR.imprimirLinea(" ¡Has derrotado al " + enemigoActual.getNombre() + "!");
            Objeto loot = obtenerLoot();
            miPersonaje.agregarObjeto(loot);
            ColorTerminal.INFORMACION.imprimirLinea(" El " + enemigoActual.getNombre() + " suelta "
                    + loot.getNombre() + ". Lo guardas en la mochila.");
            miPersonaje.finalizarEfectosDeEncuentro();
            miPersonaje.mostrarFicha();
            ColorTerminal.INFORMACION.imprimirLinea("═══════════════  FIN DEL ENFRENTAMIENTO  ═══════════════");
            continuarAventura = decidirContinuacion(enfrentamientosSuperados);
        }

        return finDePartida();
    }

    private Enemigo crearEnemigo() {
        return random.nextBoolean() ? new Troll() : new Trasgo();
    }

    private void elegirObjetoParaEncuentro(Enemigo enemigo) {
        ColorTerminal.OPCION.imprimirLinea(" ¿Quieres usar un objeto del inventario para enfrentarte al "
                + enemigo.getNombre() + "?");
        ColorTerminal.OPCION.imprimirLinea("  1. No usar nada.");
        //Recorremos el ArrayList y mostramos las opciones en consola
        for (int i = 0; i < miPersonaje.getCantidadObjetos(); i++) {
            ColorTerminal.OPCION.imprimirLinea("  " + (i + 2) + ". "
                    + miPersonaje.getObjetoMochila(i).getNombre() + ".");
        }
        // Otra forma de recorrerlo, sería hacer un "for each" en lugar de un "for i":
        // int cont=1;
        // for(Objeto item : miPersonaje.mochila){
        //    cont++;
        //    ColorTerminal.OPCION.imprimirLinea("  " + cont+". " + item.getNombre() + ".");
        // } 
        // Pero: mochila es private, por motivos de encapsulamiento
        // y para qué usar un cont si ya al hacer un for i, tengo el contedor i?

        // de nuevo, si mchila no fuera private de Personaje, 
        // podría haber hecho simplemente miPersonaje.mochila.size
        // pero de nuevo, por motivos de encapsulamiento, es mejor 
        // simplemente tener un getter para eso
        int numeroMaximo = miPersonaje.getCantidadObjetos() + 1;
        String respuesta = scanner.nextLine();
        // En vez de ensuciar esta parte comprobando si la opción es válida, 
        // delego eso a otro método privado y queda todo mejor
        // así la puedo usar desde otros puntos si lo necesitara en el futuro.
        while (!esOpcionValida(respuesta, numeroMaximo)) {
            ColorTerminal.ERROR.imprimirLinea("XX Opción no válida. Elige un número de la lista.");
            respuesta = scanner.nextLine();
        }

        int opcion = Integer.parseInt(respuesta);
        if (opcion > 1) {
            miPersonaje.usarObjeto(opcion - 2);
        }
    }

    private boolean esOpcionValida(String respuesta, int numeroMaximo) {
        // Si la opción elegida es un número entero 
        // y está dentro del límite marcado por numeroMaximo, devuelve true
        // Si no, deveulve false
        // Y lo hacemos con un try/catch dejándolo todo más fácil
        try {
            int opcion = Integer.parseInt(respuesta);
            return opcion >= 1 && opcion <= numeroMaximo;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Objeto obtenerLoot() {
        // Esta parte es en realidad más sencilla de lo que parece
        // el nombre del Array de las probabilidades "engorda" bastante esto,
        // pero ya sabéis que es siempre mejor pecar de abusar al pooner nombres largos
        // por mor de que quede claro para qué sirven
        
        // Esta parte es simplemente un control interno por si al tocar las GameConfig
        // modificamos el array de loot (por ejemplo, introducimos un nuevo elemento)
        // pero se nos olvida asignarle una probabilidad
        // En ese caso lanzaría una excepción manual
        if (GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length != GameConfig.LOOT_POSIBLE.length) {
            throw new IllegalStateException("Cada posibilidad de loot debe tener una probabilidad asociada.");
        }

        // Esta parte es sencilla, en realidad:
        // Sacamos un aleatorio entre 1 y 100 
        int tirada = random.nextInt(100) + 1;
        // Recorremos el array buscando dónde ha tocado
        for (int i = 0; i < GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length; i++) {
            //Si el número aleatorio es menor que la probabilidad que comparamos, 
            // bingo! Ése es el loot que devuelve el enemio:
            // hacemos return para devolver el objeto y además así no seguir iterando el for
            if (tirada <= GameConfig.PROBABILIDADES_LOOT_ACUMULADAS[i]) {
                return GameConfig.LOOT_POSIBLE[i];
            }
        }

        // Si hemos llegado hasta aquí es que hemos recorrido el array entero 
        // Y ninguna probabilidad acumulada era mayor que el número aleatorio
        // Es decir: la hemos "cagado". O bien el límite del aleatorio lo hemos puesto mal
        // o las probabilidades acumuladas no estaban bien.
        // Así que lanzamos un excepción para avisar de que hay que corregir el error.
        throw new IllegalStateException("Las probabilidades de loot no cubren todos los resultados.");
    }

    private boolean decidirContinuacion(int enfrentamientosSuperados) {
        // Aquñí damos las opciones al jugador al final del game loop
        // Hemos simplificado lo que teníamos y lo hemos sacado aquí por limpieza, 
        // pero por lo demás, nada nuevo.
        ColorTerminal.OPCION.imprimirLinea(" ¿Qué quieres hacer?");
        ColorTerminal.OPCION.imprimirLinea("  1  Finalizar la aventura (el héroe sobrevive).");
        ColorTerminal.OPCION.imprimirLinea("  2  Continuar la aventura.");
        String respuesta = scanner.nextLine();
        while (!"1".equals(respuesta) && !"2".equals(respuesta)) {
            ColorTerminal.ERROR.imprimirLinea("XX Opción no válida. Escribe 1 o 2.");
            respuesta = scanner.nextLine();
        }
        if ("1".equals(respuesta)) {
            ColorTerminal.JUGADOR.imprimirLinea(" ¡" + miPersonaje.getNombre()
                    + " finaliza la aventura con vida tras superar " + enfrentamientosSuperados
                    + " enfrentamiento(s)!");
            return false;
        }
        return true;
    }

    private boolean finDePartida() {
        // Hemos añadido estas nuevas opciones cuando decides terminar la aventura
        // Por si quieres empezar de nuevo de cero creando un nuevo personaje
        // Nada nuevo aquí, todo controlado.

        System.out.println();
        ColorTerminal.OPCION.imprimirLinea(" ¿Quieres volver a intentarlo?");
        ColorTerminal.OPCION.imprimirLinea("  1  ¡A por otra aventura!");
        ColorTerminal.OPCION.imprimirLinea("  2  Por hoy ya he tenido bastante.");
        String resultado = scanner.nextLine();
        while (!"1".equals(resultado) && !"2".equals(resultado)) {
            ColorTerminal.ERROR.imprimirLinea("XX Opción no válida. Escribe 1 o 2.");
            resultado = scanner.nextLine();
        }
        if ("2".equals(resultado)) {
            ColorTerminal.INFORMACION.imprimirLinea("- ¡Gracias por jugar! Hasta la próxima.");
        }
        return "1".equals(resultado);
    }

    private void pausaEntreTurnos() throws InterruptedException {
        // Aquí usamos Thread.sleep para hacer esperar un segundo (1000 milisegundos)
        // Importante señalar que el uso de Thread.sleep puede lanzar una exepción si se interrumpe
        // por eso hay que controlarla
        Thread.sleep(1000);
    }
}
