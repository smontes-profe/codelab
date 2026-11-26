import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Interfaz {
    private JLabel mensajeInicio;

    private static Interfaz instancia;
    private JFrame ventana;
    private JLabel nombreHeroe;
    private JLabel vidaHeroe;
    private JLabel danioHeroe;
    private JTextArea inventarioHeroe;
    private JLabel mensajeEncuentro;
    private JLabel nombreEnemigo;
    private JLabel vidaEnemigo;
    private JLabel danioEnemigo;
    private JLabel contadorTrasgos;
    private JLabel contadorTrolls;
    private JButton botonAtacar;
    private JButton botonPocion;
    private JTextArea resultadoTurno;
    private CardLayout vistaAcciones;
    private JPanel panelAcciones;

    public static Interfaz getInstancia() {
        if (instancia == null) {
            instancia = new Interfaz();
        }
        return instancia;
    }

    private Interfaz() {
    }

    public void mostrarInicio(GameEngine motor) {
        if (ventana == null) {
            ventana = new JFrame("Leyendas del código");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        ventana.setSize(500, 300);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Leyendas del código", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel etiquetaNombre = new JLabel("Nombre del héroe:");
        JTextField campoNombre = new JTextField();
        JButton botonJugar = new JButton("¡Comenzar aventura!");
        mensajeInicio = new JLabel("", SwingConstants.CENTER);

        formulario.add(etiquetaNombre);
        formulario.add(campoNombre);
        formulario.add(botonJugar);
        formulario.add(mensajeInicio);

        panel.add(formulario, BorderLayout.CENTER);
        ventana.setContentPane(panel);

        botonJugar.addActionListener(evento -> {
            String nombre = campoNombre.getText().trim();
            if (nombre.isEmpty()) {
                mensajeInicio.setText("Escribe un nombre para tu héroe.");
                return;
            }
            // La interfaz comunica al motor la decisión del usuario.
            motor.comenzarPartida(nombre);
        });
        ventana.revalidate();
        ventana.repaint();
        ventana.setVisible(true);
    }

    public void mostrarVistaPrincipal(Heroe heroe, GameEngine motor) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Leyendas del código", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Zona izquierda: ficha arriba e inventario debajo.
        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 15));
        panelIzquierdo.setPreferredSize(new Dimension(250, 0));

        JPanel ficha = new JPanel(new GridLayout(3, 1, 0, 8));
        ficha.setBorder(BorderFactory.createTitledBorder("Ficha del héroe"));

        nombreHeroe = new JLabel();
        vidaHeroe = new JLabel();
        danioHeroe = new JLabel();

        ficha.add(nombreHeroe);
        ficha.add(vidaHeroe);
        ficha.add(danioHeroe);

        panelIzquierdo.add(ficha, BorderLayout.NORTH);

        inventarioHeroe = new JTextArea();
        inventarioHeroe.setEditable(false);

        JScrollPane panelInventario = new JScrollPane(inventarioHeroe);
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario"));

        panelIzquierdo.add(panelInventario, BorderLayout.CENTER);
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);

        JPanel panelEncuentro = new JPanel(new BorderLayout());
        panelEncuentro.setBorder(
                BorderFactory.createTitledBorder("Encuentro actual"));

        mensajeEncuentro = new JLabel("", SwingConstants.CENTER);
        nombreEnemigo = new JLabel();
        vidaEnemigo = new JLabel();
        danioEnemigo = new JLabel();

        JPanel fichaEnemigo = new JPanel(new GridLayout(3, 1, 0, 8));
        fichaEnemigo.setBorder(BorderFactory.createTitledBorder("Ficha del enemigo"));
        fichaEnemigo.add(nombreEnemigo);
        fichaEnemigo.add(vidaEnemigo);
        fichaEnemigo.add(danioEnemigo);

        JPanel datosEncuentro = new JPanel(new BorderLayout(0, 10));
        datosEncuentro.add(mensajeEncuentro, BorderLayout.NORTH);
        datosEncuentro.add(fichaEnemigo, BorderLayout.CENTER);
        panelEncuentro.add(datosEncuentro, BorderLayout.NORTH);

        resultadoTurno = new JTextArea();
        resultadoTurno.setEditable(false);
        resultadoTurno.setLineWrap(true);
        resultadoTurno.setWrapStyleWord(true);
        resultadoTurno.setBorder(BorderFactory.createTitledBorder("Último turno"));
        panelEncuentro.add(new JScrollPane(resultadoTurno), BorderLayout.CENTER);

        botonAtacar = new JButton("Atacar");
        botonAtacar.addActionListener(evento -> motor.atacar());

        JButton botonContinuar = new JButton("Continuar");
        botonContinuar.addActionListener(evento -> motor.continuarAventura(false));
        botonPocion = new JButton("Usar poción y continuar");
        botonPocion.addActionListener(evento -> motor.continuarAventura(true));
        JButton botonRetirarse = new JButton("Retirarse");
        botonRetirarse.addActionListener(evento -> motor.retirarse());

        JPanel opcionesEncuentro = new JPanel();
        opcionesEncuentro.add(botonContinuar);
        opcionesEncuentro.add(botonPocion);
        opcionesEncuentro.add(botonRetirarse);

        vistaAcciones = new CardLayout();
        panelAcciones = new JPanel(vistaAcciones);
        panelAcciones.add(botonAtacar, "combate");
        panelAcciones.add(opcionesEncuentro, "opciones");
        JButton botonReiniciar = new JButton("Nueva aventura");
        botonReiniciar.addActionListener(evento -> motor.reiniciar());
        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(evento -> ventana.dispose());

        JPanel opcionesFinales = new JPanel();
        opcionesFinales.add(botonReiniciar);
        opcionesFinales.add(botonSalir);
        panelAcciones.add(opcionesFinales, "terminado");
        panelEncuentro.add(panelAcciones, BorderLayout.SOUTH);

        JPanel panelContadores = new JPanel(new GridLayout(1, 2, 10, 0));
        panelContadores.setBorder(BorderFactory.createTitledBorder("Encuentros superados"));
        contadorTrasgos = new JLabel("", SwingConstants.CENTER);
        contadorTrolls = new JLabel("", SwingConstants.CENTER);
        panelContadores.add(contadorTrasgos);
        panelContadores.add(contadorTrolls);

        JPanel panelDerecho = new JPanel(new BorderLayout(0, 15));
        panelDerecho.add(panelContadores, BorderLayout.NORTH);
        panelDerecho.add(panelEncuentro, BorderLayout.CENTER);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        actualizarFicha(heroe);

        ventana.setContentPane(panelPrincipal);
        ventana.setSize(850, 500);
        ventana.setLocationRelativeTo(null);
        ventana.revalidate();
        ventana.repaint();
    }

    public void actualizarFicha(Heroe heroe) {
        nombreHeroe.setText("Nombre: " + heroe.getNombre());
        vidaHeroe.setText("Vida: " + heroe.getVida());
        danioHeroe.setText("Daño: " + heroe.getDanio());

        String[] objetos = {
                GameConfig.INVENTARIO_ESPADA,
                GameConfig.INVENTARIO_ESCUDO,
                GameConfig.INVENTARIO_POCION,
                GameConfig.INVENTARIO_ANILLO_ATAQUE,
                GameConfig.INVENTARIO_ANILLO_DEFENSA
        };

        String textoInventario = "";

        for (String objetoBuscado : objetos) {
            int cantidad = 0;

            for (String objeto : heroe.getInventario()) {
                if (objeto.equals(objetoBuscado)) {
                    cantidad++;
                }
            }

            textoInventario += objetoBuscado + ": " + cantidad + "\n";
        }

        inventarioHeroe.setText(textoInventario);
    }
    public void mostrarEnemigo(Enemigo enemigo) {
        mensajeEncuentro.setText("¡Aparece un nuevo enemigo!");
        actualizarEnemigo(enemigo);
        resultadoTurno.setText("");
        botonAtacar.setEnabled(true);
        vistaAcciones.show(panelAcciones, "combate");
    }

    /**
     * Actualiza los datos visibles del enemigo después de un ataque.
     * @param enemigo enemigo del encuentro actual
     */
    public void actualizarEnemigo(Enemigo enemigo) {
        nombreEnemigo.setText("Nombre: " + enemigo.getNombre());
        vidaEnemigo.setText("Vida: " + enemigo.getVida());
        danioEnemigo.setText("Daño: " + enemigo.getDanio());
    }

    /**
     * Muestra los daños del último turno y bloquea el ataque si el combate ha terminado.
     * @param heroe héroe que participa en el combate
     * @param enemigo enemigo que recibe el ataque
     * @param danioHeroe daño causado por el héroe
     * @param danioEnemigo daño recibido por el héroe, si hay contraataque
     */
    public void mostrarResultadoTurno(Heroe heroe, Enemigo enemigo,
            int danioHeroe, int danioEnemigo) {
        String resultado = heroe.getNombre() + " ataca a " + enemigo.getNombre()
                + " y causa " + danioHeroe + " de daño.\n";

        if (enemigo.estaVivo()) {
            resultado += enemigo.getNombre() + " contraataca y causa "
                    + danioEnemigo + " de daño.\n";
        } else {
            resultado += "¡" + enemigo.getNombre() + " ha sido derrotado!";
        }
        if (!heroe.estaVivo()) {
            resultado += "¡" + heroe.getNombre() + " ha sido derrotado!";
        }

        mensajeEncuentro.setText("Resultado del último ataque");
        resultadoTurno.setText(resultado);
        botonAtacar.setEnabled(heroe.estaVivo() && enemigo.estaVivo());
    }

    /**
     * Actualiza el número de enemigos derrotados de cada tipo.
     * @param trasgos número de trasgos derrotados en la partida
     * @param trolls número de trolls derrotados en la partida
     */
    public void actualizarContadores(int trasgos, int trolls) {
        contadorTrasgos.setText("Trasgos: " + trasgos);
        contadorTrolls.setText("Trolls: " + trolls);
    }

    /**
     * Añade al resultado del combate el objeto obtenido como recompensa.
     * @param objeto nombre del objeto añadido al inventario
     */
    public void mostrarRecompensa(String objeto) {
        resultadoTurno.append("\n¡Has conseguido " + objeto + "!");
    }

    /**
     * Muestra las decisiones disponibles tras ganar el encuentro.
     * @param tienePociones si el héroe puede usar una poción
     */
    public void mostrarOpcionesFinEncuentro(boolean tienePociones) {
        botonPocion.setEnabled(tienePociones);
        vistaAcciones.show(panelAcciones, "opciones");
    }

    /**
     * Muestra el resultado de la partida y permite reiniciar o salir.
     * @param heroe héroe que termina la aventura
     * @param retirada si el héroe se retiró con vida
     */
    public void mostrarFinPartida(Heroe heroe, boolean retirada) {
        if (retirada) {
            mensajeEncuentro.setText("¡Aventura terminada!");
            resultadoTurno.append("\n" + heroe.getNombre() + " se ha retirado con vida.");
        } else {
            mensajeEncuentro.setText("GAME OVER");
        }
        resultadoTurno.append("\nEnemigos derrotados: " + heroe.enemigosDerrotados);
        vistaAcciones.show(panelAcciones, "terminado");
    }

}
