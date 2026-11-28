package controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import modelo.Botin;
import modelo.Combate;
import modelo.Encuentro;
import modelo.EntradaRanking;
import modelo.Objeto;
import modelo.Personaje;
import modelo.Profesion;
import modelo.Ranking;
import modelo.RegistroJuego;

/** Coordina el estado de la partida y conecta la vista con el modelo. */
public class ControladorJuego {

    // Estos campos guardan el estado entre pulsaciones y turnos del temporizador.
    private final Random random = new Random();
    private final RegistroJuego registro;
    private final Ranking ranking = new Ranking();
    private String jugador;
    private Personaje personaje;
    private Encuentro encuentro;
    private Combate combate;
    private boolean objetoElegido;
    private boolean turnoDistanciaPendiente;
    private boolean combateTerminado;
    private boolean partidaFinalizada;
    private int enfrentamientosSuperados;
    private Objeto ultimoBotin;

    /**
     * Crea el controlador con el destino de los mensajes de combate.
     *
     * @param registro destinatario de los mensajes del juego
     */
    public ControladorJuego(RegistroJuego registro) {
        this.registro = registro;
    }

    /**
     * Crea el personaje y reinicia el estado de la partida.
     *
     * @param jugador nombre del jugador
     * @param nombrePersonaje nombre del personaje
     * @param profesion profesión elegida
     */
    public void iniciarPartida(String jugador, String nombrePersonaje, Profesion profesion) {
        this.jugador = jugador;
        this.personaje = profesion.crearPersonaje(nombrePersonaje, registro);
        this.encuentro = null;
        this.combate = null;
        this.objetoElegido = false;
        this.turnoDistanciaPendiente = false;
        this.combateTerminado = false;
        this.partidaFinalizada = false;
        this.enfrentamientosSuperados = 0;
        this.ultimoBotin = null;
    }

    /** @return nombre del jugador, o {@code null} antes de iniciar la partida */
    public String getJugador() {
        return jugador;
    }

    /** @return personaje de la partida, o {@code null} antes de iniciarla */
    public Personaje getPersonaje() {
        return personaje;
    }

    /** @return número de encuentros ganados en la partida actual */
    public int getEnfrentamientosSuperados() {
        return enfrentamientosSuperados;
    }

    /** @return botín de la última victoria, o {@code null} si todavía no lo hay */
    public Objeto getUltimoBotin() {
        return ultimoBotin;
    }

    /**
     * Consulta el ranking. La vista debe hacerlo en segundo plano para no bloquear Swing.
     *
     * @return hasta diez resultados ordenados por enemigos derrotados
     * @throws SQLException si falla la consulta
     */
    public ArrayList<EntradaRanking> consultarRanking() throws SQLException {
        return ranking.consultarRanking();
    }

    /**
     * Guarda en el ranking los datos de una partida.
     *
     * @param jugador nombre del jugador
     * @param personaje nombre del personaje
     * @param enfrentamientos número de encuentros superados
     * @throws SQLException si falla el guardado
     */
    public void guardarResultado(String jugador, String personaje, int enfrentamientos) throws SQLException {
        ranking.guardarRecord(jugador, personaje, enfrentamientos);
    }

    /**
     * Prepara y conserva un encuentro para la partida actual.
     *
     * @return encuentro creado
     * @throws IllegalStateException si no hay partida activa o ya existe un encuentro
     */
    public Encuentro prepararEncuentro() {
        if (personaje == null || partidaFinalizada) {
            throw new IllegalStateException("Primero hay que crear un personaje.");
        }
        if (encuentro != null) {
            throw new IllegalStateException("Ya hay un encuentro en curso.");
        }
        encuentro = new Encuentro(random, registro);
        combate = new Combate(personaje, encuentro.getEnemigo(), registro);
        turnoDistanciaPendiente = encuentro.isEnemigoALejos();
        return encuentro;
    }

    /**
     * Confirma la elección de un objeto antes del combate. Aunque el objeto
     * elegido no se pueda consumir, la elección para este encuentro queda hecha.
     *
     * @param indice posición en la mochila, o -1 para no usar ningún objeto
     * @return mensaje que explica el resultado de la elección
     * @throws IllegalStateException si no se puede elegir en este momento
     * @throws IllegalArgumentException si el índice no corresponde a una opción
     */
    public String elegirObjeto(int indice) {
        if (encuentro == null || objetoElegido) {
            throw new IllegalStateException("No se puede elegir un objeto en este momento.");
        }
        if (indice < -1 || indice >= personaje.getCantidadObjetos()) {
            throw new IllegalArgumentException("La posición del objeto no es válida.");
        }
        String mensaje = indice == -1 ? "No usas ningún objeto." : personaje.usarObjeto(indice);
        objetoElegido = true;
        return mensaje;
    }

    /**
     * Ejecuta un turno del combate y procesa su resultado si termina.
     *
     * @return {@code true} si quedan turnos por jugar; {@code false} si ha terminado
     * @throws IllegalStateException si el combate no está listo o ya ha terminado
     */
    public boolean avanzarCombate() {
        if (combate == null || !objetoElegido || combateTerminado) {
            throw new IllegalStateException("No se puede avanzar el combate en este momento.");
        }

        if (turnoDistanciaPendiente) {
            combate.turnoADistancia();
            turnoDistanciaPendiente = false;
            if (!combate.haTerminado()) {
                registro.informacion(" El enemigo se acerca... ¡ahora está cuerpo a cuerpo!");
            }
        } else {
            combate.turnoCuerpoACuerpo();
        }

        if (combate.haTerminado()) {
            combateTerminado = true;
            if (!personaje.estaVivo()) {
                personaje.finalizarEfectosDeEncuentro();
                partidaFinalizada = true;
                registro.informacion("Fin de la aventura. Has superado "
                        + enfrentamientosSuperados + " enfrentamiento(s).");
            } else {
                registro.jugador(" ¡Has derrotado al " + encuentro.getEnemigo().getNombre() + "!");
                // El botín se guarda antes de terminar los efectos del encuentro.
                ultimoBotin = Botin.obtenerLoot(random);
                personaje.agregarObjeto(ultimoBotin);
                enfrentamientosSuperados++;
                personaje.finalizarEfectosDeEncuentro();
            }
            return false;
        }
        return true;
    }

    /**
     * Prepara la partida para un nuevo encuentro tras una victoria.
     *
     * @throws IllegalStateException si no se acaba de ganar un encuentro
     */
    public void continuarAventura() {
        comprobarVictoria();
        // Dejamos listo el controlador para preparar el siguiente encuentro.
        encuentro = null;
        combate = null;
        objetoElegido = false;
        turnoDistanciaPendiente = false;
        combateTerminado = false;
        ultimoBotin = null;
    }

    /**
     * Finaliza la partida con vida tras una victoria.
     *
     * @throws IllegalStateException si no se acaba de ganar un encuentro
     */
    public void finalizarAventura() {
        comprobarVictoria();
        partidaFinalizada = true;
    }

    private void comprobarVictoria() {
        if (partidaFinalizada || !combateTerminado || personaje == null || !personaje.estaVivo()) {
            throw new IllegalStateException("Solo se puede decidir después de ganar un encuentro.");
        }
    }
}
