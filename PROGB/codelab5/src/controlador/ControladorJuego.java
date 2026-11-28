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

public class ControladorJuego {

    // Guardamos los datos de la partida para usarlos en los próximos pasos.
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

    public ControladorJuego(RegistroJuego registro) {
        this.registro = registro;
    }

    // Al crear otro personaje, comienza una nueva partida.
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

    public String getJugador() {
        return jugador;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public int getEnfrentamientosSuperados() {
        return enfrentamientosSuperados;
    }

    public Objeto getUltimoBotin() {
        return ultimoBotin;
    }

    // La vista ejecuta estas operaciones en segundo plano para no bloquear Swing.
    public ArrayList<EntradaRanking> consultarRanking() throws SQLException {
        return ranking.consultarRanking();
    }

    public void guardarResultado(String jugador, String personaje, int enfrentamientos) throws SQLException {
        ranking.guardarRecord(jugador, personaje, enfrentamientos);
    }

    // Conservamos el encuentro para poder continuar con él más adelante.
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

    // El índice -1 representa la decisión de no usar ningún objeto.
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

    // Ejecutamos un turno cada vez, para que la ventana pueda actualizarse.
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
