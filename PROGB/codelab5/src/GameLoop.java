import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class GameLoop {

    private final Scanner scanner;
    private final Random random;
    private final Ranking ranking = new Ranking();
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
     */
    public void run() {
        boolean jugarDeNuevo;
        do {
            jugarDeNuevo = jugarPartida();
        } while (jugarDeNuevo);
    }

    private boolean jugarPartida() {
        System.out.println();
        ColorTerminal.INFORMACION.imprimirLinea("╔══════════════════════════════════════╗");
        ColorTerminal.INFORMACION.imprimirLinea("║             LEYENDAS DEL CÓDIGO      ║ ");
        ColorTerminal.INFORMACION.imprimirLinea("╚══════════════════════════════════════╝");

        try {
            ranking.mostrarRanking();
        } catch (SQLException e) {
            ColorTerminal.ERROR.imprimirLinea(" No se ha podido consultar el ranking: " + e.getMessage());
        }

        ColorTerminal.OPCION.imprimir(" Introduce tu nombre de jugador: ");
        String jugador = scanner.nextLine();
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

        int enfrentamientosSuperados = 0;
        boolean continuarAventura = true;
        while (miPersonaje.estaVivo() && continuarAventura) {
            enfrentamientosSuperados++;

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
            elegirObjetoParaEncuentro(enemigoActual);

            if (enemigoALejos) {
                if (!miPersonaje.atacarADistancia(enemigoActual)) {
                    ColorTerminal.JUGADOR.imprimirLinea(" El guerrero espera: ¡solo puede luchar cuerpo a cuerpo!");
                }
                if (enemigoActual.estaVivo() && miPersonaje.estaVivo()) {
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

        try {
            ranking.guardarRecord(jugador, miPersonaje.getNombre(), enfrentamientosSuperados);
            ColorTerminal.INFORMACION.imprimirLinea(" ¡Record guardado!");
        } catch (SQLException e) {
            ColorTerminal.ERROR.imprimirLinea(" No se ha podido guardar el record: " + e.getMessage());
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
        for (int i = 0; i < miPersonaje.getCantidadObjetos(); i++) {
            ColorTerminal.OPCION.imprimirLinea("  " + (i + 2) + ". "
                    + miPersonaje.getObjetoMochila(i).getNombre() + ".");
        }
        int numeroMaximo = miPersonaje.getCantidadObjetos() + 1;
        String respuesta = scanner.nextLine();
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
        try {
            int opcion = Integer.parseInt(respuesta);
            return opcion >= 1 && opcion <= numeroMaximo;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Objeto obtenerLoot() {
        if (GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length != GameConfig.LOOT_POSIBLE.length) {
            throw new IllegalStateException("Cada posibilidad de loot debe tener una probabilidad asociada.");
        }

        int tirada = random.nextInt(100) + 1;
        for (int i = 0; i < GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length; i++) {
            if (tirada <= GameConfig.PROBABILIDADES_LOOT_ACUMULADAS[i]) {
                return GameConfig.LOOT_POSIBLE[i];
            }
        }

        throw new IllegalStateException("Las probabilidades de loot no cubren todos los resultados.");
    }

    private boolean decidirContinuacion(int enfrentamientosSuperados) {
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

    private void pausaEntreTurnos() {
        try {
            Thread.sleep(GameConfig.TIEMPO_ESPERA);
        } catch (InterruptedException e) {
        }
        
    }
}
