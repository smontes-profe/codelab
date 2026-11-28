import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class VentanaJuego extends JFrame {

    private static final Color COLOR_INFORMACION = new Color(245, 245, 245);
    private static final Color COLOR_JUGADOR = new Color(155, 225, 255);
    private static final Color COLOR_ENEMIGO = new Color(255, 202, 155);

    // El controlador conserva los datos de la partida; la ventana los muestra.
    private final ControladorJuego controlador;
    private final Ranking ranking = new Ranking();

    // CardLayout permite cambiar entre el formulario y la pantalla de partida.
    private final CardLayout distribucionPantallas = new CardLayout();
    private final JPanel pantallas = new JPanel(distribucionPantallas);

    // Campos donde el jugador escribe los nombres.
    private final JTextField nombreJugador = new JTextField();
    private final JTextField nombrePersonaje = new JTextField();
    private final JTextArea estadoRanking = new JTextArea();
    private final CardLayout vistasRanking = new CardLayout();
    private final JPanel contenidoRanking = new JPanel(vistasRanking);
    private final DefaultTableModel datosRanking = new DefaultTableModel(
            new String[] {"#", "Jugador", "Personaje", "Derrotados"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaRanking = new JTable(datosRanking);
    private int consultaRankingActual;
    // values() obtiene todas las opciones del enum para el desplegable.
    private final JComboBox<Profesion> profesion = new JComboBox<>(Profesion.values());
    // Componentes de la ficha que actualizaremos cuando cambie el personaje.
    private final JTextArea jugadorFicha = new JTextArea();
    private final JTextPane tituloPersonaje = new JTextPane();
    private final JLabel vidaPersonaje = new JLabel();
    private final JLabel danioPersonaje = new JLabel();
    private final JTextArea inventario = new JTextArea();
    private final JTextPane registroEncuentro = new JTextPane();
    private final JTextPane registroCombate = new JTextPane();
    private String nombreEnemigoActual;
    private final JButton prepararEncuentro = new JButton("Preparar encuentro");
    private final JLabel etiquetaObjeto = new JLabel("Objeto:");
    private final JComboBox<String> objetosEncuentro = new JComboBox<>();
    private final JButton confirmarObjeto = new JButton("Confirmar");
    private final JButton continuarAventura = new JButton("Continuar aventura");
    private final JButton finalizarAventura = new JButton("Finalizar aventura");
    private final JButton nuevaPartida = new JButton("Nueva partida");
    private final Timer turnos = new Timer(GameConfig.TIEMPO_ESPERA, evento -> avanzarCombate());

    public VentanaJuego() {
        super("Leyendas del código");
        controlador = new ControladorJuego(new RegistroJuego(
                mensaje -> escribirMensaje(registroCombate, mensaje + "\n", COLOR_JUGADOR),
                mensaje -> escribirMensaje(registroCombate, mensaje + "\n", COLOR_ENEMIGO),
                mensaje -> escribirMensaje(registroCombate, mensaje + "\n", COLOR_INFORMACION)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pantallas.add(crearPantallaInicio(), "inicio");
        pantallas.add(crearPantallaPartida(), "partida");
        setContentPane(pantallas);
        setMinimumSize(new Dimension(760, 480));
        setSize(900, 600);
        setLocationRelativeTo(null);
        cargarRanking();
    }

    private JPanel crearPantallaInicio() {
        Color fondo = new Color(28, 35, 46);
        Color colorFormulario = new Color(67, 45, 93);
        Color colorRanking = new Color(39, 50, 64);
        Color colorTexto = new Color(245, 245, 245);

        JPanel inicio = new JPanel(new BorderLayout(15, 15));
        inicio.setBackground(fondo);
        inicio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("LEYENDAS DEL CÓDIGO", JLabel.CENTER);
        titulo.setForeground(colorTexto);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 32f));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        inicio.add(titulo, BorderLayout.NORTH);

        // El formulario y el ranking ocupan bloques independientes, como en la partida.
        JPanel contenido = new JPanel(new BorderLayout(15, 0));
        contenido.setBackground(fondo);
        JPanel formulario = new JPanel(new BorderLayout(0, 15));
        formulario.setBackground(colorFormulario);
        formulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        formulario.setPreferredSize(new Dimension(320, 0));

        JLabel tituloFormulario = new JLabel("CREA TU HÉROE");
        tituloFormulario.setForeground(colorTexto);
        tituloFormulario.setFont(tituloFormulario.getFont().deriveFont(Font.BOLD, 18f));
        formulario.add(tituloFormulario, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridLayout(6, 1, 0, 6));
        datos.setBackground(colorFormulario);
        JLabel etiquetaJugador = new JLabel("Nombre del jugador");
        JLabel etiquetaPersonaje = new JLabel("Nombre del personaje");
        JLabel etiquetaProfesion = new JLabel("Profesión");
        for (JLabel etiqueta : new JLabel[] {etiquetaJugador, etiquetaPersonaje, etiquetaProfesion}) {
            etiqueta.setForeground(colorTexto);
            etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD, 15f));
        }
        nombreJugador.setFont(nombreJugador.getFont().deriveFont(15f));
        nombrePersonaje.setFont(nombrePersonaje.getFont().deriveFont(15f));
        profesion.setFont(profesion.getFont().deriveFont(15f));
        datos.add(etiquetaJugador);
        datos.add(nombreJugador);
        datos.add(etiquetaPersonaje);
        datos.add(nombrePersonaje);
        datos.add(etiquetaProfesion);
        datos.add(profesion);

        JButton crearPersonaje = new JButton("¡Comenzar aventura!");
        crearPersonaje.setFont(crearPersonaje.getFont().deriveFont(Font.BOLD, 16f));
        crearPersonaje.setBackground(new Color(32, 75, 110));
        crearPersonaje.setForeground(colorTexto);
        crearPersonaje.setFocusPainted(false);
        // Al pulsar el botón, comienza la partida y se muestra la ficha.
        crearPersonaje.addActionListener(evento -> iniciarPartida());
        JPanel contenidoFormulario = new JPanel(new BorderLayout(0, 20));
        contenidoFormulario.setBackground(colorFormulario);
        contenidoFormulario.add(datos, BorderLayout.NORTH);
        contenidoFormulario.add(crearPersonaje, BorderLayout.CENTER);
        JPanel espacioFormulario = new JPanel(new BorderLayout());
        espacioFormulario.setBackground(colorFormulario);
        espacioFormulario.add(contenidoFormulario, BorderLayout.NORTH);
        formulario.add(espacioFormulario, BorderLayout.CENTER);
        contenido.add(formulario, BorderLayout.WEST);

        JPanel panelRanking = new JPanel(new BorderLayout());
        panelRanking.setBackground(colorRanking);
        JLabel tituloRanking = new JLabel("RANKING · TOP 10");
        tituloRanking.setForeground(colorTexto);
        tituloRanking.setFont(tituloRanking.getFont().deriveFont(Font.BOLD, 18f));
        tituloRanking.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        panelRanking.add(tituloRanking, BorderLayout.NORTH);
        estadoRanking.setEditable(false);
        estadoRanking.setLineWrap(true);
        estadoRanking.setWrapStyleWord(true);
        estadoRanking.setBackground(colorRanking);
        estadoRanking.setForeground(colorTexto);
        estadoRanking.setFont(estadoRanking.getFont().deriveFont(15f));
        estadoRanking.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        // Una fila por resultado mantiene los diez puestos visibles.
        tablaRanking.setBackground(colorRanking);
        tablaRanking.setForeground(colorTexto);
        tablaRanking.setFont(tablaRanking.getFont().deriveFont(14f));
        tablaRanking.setRowHeight(24);
        tablaRanking.setFocusable(false);
        tablaRanking.setRowSelectionAllowed(false);
        tablaRanking.setShowGrid(false);
        tablaRanking.setFillsViewportHeight(true);
        tablaRanking.getTableHeader().setReorderingAllowed(false);
        tablaRanking.getTableHeader().setResizingAllowed(false);
        tablaRanking.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaRanking.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaRanking.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaRanking.getColumnModel().getColumn(3).setPreferredWidth(85);
        tablaRanking.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color[] coloresPodio = {
                    new Color(255, 215, 110), new Color(215, 225, 235), new Color(240, 178, 125)
            };

            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean seleccionado,
                    boolean conFoco, int fila, int columna) {
                super.getTableCellRendererComponent(tabla, valor, seleccionado, conFoco, fila, columna);
                setForeground(fila < 3 ? coloresPodio[fila] : COLOR_INFORMACION);
                setBackground(tabla.getBackground());
                setFont(tabla.getFont().deriveFont(fila < 3 ? Font.BOLD : Font.PLAIN));
                setHorizontalAlignment(columna == 0 || columna == 3 ? JLabel.CENTER : JLabel.LEFT);
                putClientProperty("html.disable", Boolean.TRUE);
                setToolTipText(valor == null ? null : valor.toString());
                return this;
            }
        });
        JScrollPane tablaConCabecera = new JScrollPane(tablaRanking,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tablaConCabecera.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        tablaConCabecera.getViewport().setBackground(colorRanking);
        contenidoRanking.add(estadoRanking, "estado");
        contenidoRanking.add(tablaConCabecera, "tabla");
        panelRanking.add(contenidoRanking, BorderLayout.CENTER);
        contenido.add(panelRanking, BorderLayout.CENTER);
        inicio.add(contenido, BorderLayout.CENTER);
        return inicio;
    }

    private JPanel crearPantallaPartida() {
        JPanel partida = new JPanel(new BorderLayout(10, 10));
        partida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("LEYENDAS DEL CÓDIGO", JLabel.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 32f));
        partida.add(titulo, BorderLayout.NORTH);

        // La ficha permanece a la izquierda; el registro crece con la ventana.
        partida.add(crearPanelPersonaje(), BorderLayout.WEST);

        // Los acontecimientos del encuentro y los turnos tienen registros separados.
        JPanel mensajes = new JPanel(new GridLayout(2, 1, 0, 10));
        mensajes.add(crearPanelMensajes("ENCUENTRO", registroEncuentro, new Color(32, 75, 110)));
        mensajes.add(crearPanelMensajes("COMBATE", registroCombate, new Color(92, 48, 56)));

        prepararEncuentro.setEnabled(false);
        prepararEncuentro.addActionListener(evento -> prepararEncuentro());
        Color colorOpciones = new Color(39, 50, 64);
        JPanel opciones = new JPanel(new BorderLayout());
        opciones.setBackground(colorOpciones);
        JLabel tituloOpciones = new JLabel("OPCIONES");
        tituloOpciones.setForeground(new Color(245, 245, 245));
        tituloOpciones.setFont(tituloOpciones.getFont().deriveFont(Font.BOLD, 16f));
        tituloOpciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        opciones.add(tituloOpciones, BorderLayout.NORTH);
        JPanel botones = new JPanel();
        botones.setBackground(colorOpciones);
        etiquetaObjeto.setForeground(new Color(245, 245, 245));
        etiquetaObjeto.setFont(etiquetaObjeto.getFont().deriveFont(Font.BOLD, 15f));
        botones.add(prepararEncuentro);
        botones.add(etiquetaObjeto);
        botones.add(objetosEncuentro);
        botones.add(confirmarObjeto);
        botones.add(continuarAventura);
        botones.add(finalizarAventura);
        botones.add(nuevaPartida);
        opciones.add(botones, BorderLayout.CENTER);
        etiquetaObjeto.setVisible(false);
        objetosEncuentro.setVisible(false);
        confirmarObjeto.setVisible(false);
        continuarAventura.setVisible(false);
        finalizarAventura.setVisible(false);
        nuevaPartida.setVisible(false);
        confirmarObjeto.addActionListener(evento -> elegirObjeto());
        continuarAventura.addActionListener(evento -> continuarAventura());
        finalizarAventura.addActionListener(evento -> finalizarAventura());
        nuevaPartida.addActionListener(evento -> volverAlInicio());

        JPanel aventura = new JPanel(new BorderLayout(0, 10));
        aventura.add(mensajes, BorderLayout.CENTER);
        aventura.add(opciones, BorderLayout.SOUTH);
        partida.add(aventura, BorderLayout.CENTER);
        return partida;
    }

    private JPanel crearPanelMensajes(String titulo, JTextPane mensajes, Color fondo) {
        Color colorTexto = new Color(245, 245, 245);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);

        JLabel encabezado = new JLabel(titulo);
        encabezado.setForeground(colorTexto);
        encabezado.setFont(encabezado.getFont().deriveFont(Font.BOLD, 16f));
        encabezado.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panel.add(encabezado, BorderLayout.NORTH);

        mensajes.setEditable(false);
        mensajes.setBackground(fondo);
        mensajes.setForeground(colorTexto);
        mensajes.setFont(mensajes.getFont().deriveFont(15f));
        mensajes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        JScrollPane desplazamiento = new JScrollPane(mensajes);
        desplazamiento.setBorder(null);
        panel.add(desplazamiento, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPersonaje() {
        Color colorJugador = new Color(32, 75, 110);
        Color colorPersonaje = new Color(67, 45, 93);
        Color colorInventario = new Color(39, 50, 64);
        Color colorTexto = new Color(245, 245, 245);

        JPanel lateral = new JPanel(new BorderLayout(0, 10));
        lateral.setPreferredSize(new Dimension(300, 0));

        // Cada bloque tiene su propio color y conserva el texto legible.
        jugadorFicha.setEditable(false);
        jugadorFicha.setLineWrap(true);
        jugadorFicha.setWrapStyleWord(true);
        jugadorFicha.setBackground(colorJugador);
        jugadorFicha.setForeground(colorTexto);
        jugadorFicha.setFont(jugadorFicha.getFont().deriveFont(Font.BOLD, 16f));
        jugadorFicha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane panelJugador = new JScrollPane(jugadorFicha);
        panelJugador.setBorder(null);
        panelJugador.setPreferredSize(new Dimension(0, 65));

        tituloPersonaje.setEditable(false);
        tituloPersonaje.setBackground(colorPersonaje);
        tituloPersonaje.setForeground(colorTexto);
        tituloPersonaje.setFont(tituloPersonaje.getFont().deriveFont(Font.BOLD, 21f));
        tituloPersonaje.setBorder(BorderFactory.createEmptyBorder(10, 8, 5, 8));
        JScrollPane panelTitulo = new JScrollPane(tituloPersonaje);
        panelTitulo.setBorder(null);
        panelTitulo.setPreferredSize(new Dimension(0, 90));

        vidaPersonaje.setForeground(colorTexto);
        danioPersonaje.setForeground(colorTexto);
        vidaPersonaje.setFont(vidaPersonaje.getFont().deriveFont(Font.BOLD, 15f));
        danioPersonaje.setFont(danioPersonaje.getFont().deriveFont(Font.BOLD, 15f));
        JPanel estadisticas = new JPanel(new GridLayout(2, 1, 0, 6));
        estadisticas.setBackground(colorPersonaje);
        estadisticas.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        estadisticas.add(vidaPersonaje);
        estadisticas.add(danioPersonaje);

        JPanel panelPersonaje = new JPanel(new BorderLayout());
        panelPersonaje.add(panelTitulo, BorderLayout.CENTER);
        panelPersonaje.add(estadisticas, BorderLayout.SOUTH);

        JPanel cabecera = new JPanel(new BorderLayout(0, 10));
        cabecera.add(panelJugador, BorderLayout.NORTH);
        cabecera.add(panelPersonaje, BorderLayout.CENTER);
        lateral.add(cabecera, BorderLayout.NORTH);

        JLabel tituloInventario = new JLabel("INVENTARIO");
        tituloInventario.setForeground(colorTexto);
        tituloInventario.setFont(tituloInventario.getFont().deriveFont(Font.BOLD, 16f));
        tituloInventario.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        inventario.setEditable(false);
        inventario.setLineWrap(true);
        inventario.setWrapStyleWord(true);
        inventario.setBackground(colorInventario);
        inventario.setForeground(colorTexto);
        inventario.setFont(inventario.getFont().deriveFont(15f));
        inventario.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JPanel panelInventario = new JPanel(new BorderLayout());
        panelInventario.setBackground(colorInventario);
        panelInventario.add(tituloInventario, BorderLayout.NORTH);
        JScrollPane listaObjetos = new JScrollPane(inventario);
        listaObjetos.setBorder(null);
        panelInventario.add(listaObjetos, BorderLayout.CENTER);
        lateral.add(panelInventario, BorderLayout.CENTER);
        return lateral;
    }

    private void iniciarPartida() {
        if (nombreJugador.getText().trim().isEmpty() || nombrePersonaje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce el nombre del jugador y del personaje.");
            return;
        }
        int indice = profesion.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Elige una profesión.");
            return;
        }
        // Pasamos los datos de la ventana al controlador para iniciar la partida.
        Profesion profesionElegida = profesion.getItemAt(indice);
        controlador.iniciarPartida(nombreJugador.getText(), nombrePersonaje.getText(), profesionElegida);
        nombreEnemigoActual = null;
        mostrarPersonaje();
        registroEncuentro.setText("La aventura está a punto de comenzar.\n");
        registroCombate.setText("");
        prepararEncuentro.setEnabled(true);
        // Al comenzar, mostramos la partida en una ventana más grande.
        distribucionPantallas.show(pantallas, "partida");
        setMinimumSize(new Dimension(760, 480));
        setSize(1000, 650);
        setLocationRelativeTo(null);
    }

    private void mostrarPersonaje() {
        Personaje personaje = controlador.getPersonaje();

        jugadorFicha.setText("Jugador: " + controlador.getJugador());
        tituloPersonaje.setText(personaje.getNombre() + " el "
                + personaje.getProfesion().toLowerCase(Locale.ROOT));
        // Centramos el texto sin interpretar el nombre como HTML.
        SimpleAttributeSet centrado = new SimpleAttributeSet();
        StyleConstants.setAlignment(centrado, StyleConstants.ALIGN_CENTER);
        tituloPersonaje.getStyledDocument().setParagraphAttributes(
                0, tituloPersonaje.getDocument().getLength(), centrado, false);
        SimpleAttributeSet nombreDestacado = new SimpleAttributeSet();
        StyleConstants.setBold(nombreDestacado, true);
        StyleConstants.setItalic(nombreDestacado, true);
        tituloPersonaje.getStyledDocument().setCharacterAttributes(
                0, personaje.getNombre().length(), nombreDestacado, false);
        vidaPersonaje.setText("Vida: " + personaje.getVida() + " / " + personaje.getVidaMaxima());
        danioPersonaje.setText("Daño base: " + personaje.getDanio());

        // La mochila se muestra en su propio panel.
        StringBuilder texto = new StringBuilder();
        if (personaje.getCantidadObjetos() == 0) {
            texto.append("(vacío)\n");
        } else {
            for (int i = 0; i < personaje.getCantidadObjetos(); i++) {
                texto.append("- ").append(personaje.getObjetoMochila(i).getNombre()).append("\n");
            }
        }
        inventario.setText(texto.toString());
    }

    private void prepararEncuentro() {
        Encuentro encuentro = controlador.prepararEncuentro();
        String nombreEnemigo = encuentro.getEnemigo().getNombre();
        nombreEnemigoActual = nombreEnemigo;
        escribirMensaje(registroEncuentro, "\nENFRENTAMIENTO "
                + (controlador.getEnfrentamientosSuperados() + 1) + "\n", COLOR_INFORMACION);
        if (encuentro.isEnemigoALejos()) {
            escribirMensaje(registroEncuentro, "\n¡Un " + nombreEnemigo + " aparece a lo lejos!\n",
                    COLOR_INFORMACION);
        } else {
            escribirMensaje(registroEncuentro, "\n¡Un " + nombreEnemigo + " aparece frente a "
                    + controlador.getPersonaje().getNombre() + "!\n", COLOR_INFORMACION);
        }
        prepararEncuentro.setEnabled(false);
        // La primera opción no usa nada; las demás siguen el orden de la mochila.
        objetosEncuentro.removeAllItems();
        objetosEncuentro.addItem("No usar nada");
        Personaje personaje = controlador.getPersonaje();
        for (int i = 0; i < personaje.getCantidadObjetos(); i++) {
            objetosEncuentro.addItem(personaje.getObjetoMochila(i).getNombre());
        }
        escribirMensaje(registroEncuentro, "¿Quieres usar un objeto antes del combate?\n", COLOR_INFORMACION);
        etiquetaObjeto.setVisible(true);
        objetosEncuentro.setVisible(true);
        confirmarObjeto.setVisible(true);
    }

    private void elegirObjeto() {
        int indice = objetosEncuentro.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Elige una opción.");
            return;
        }
        String resultado = controlador.elegirObjeto(indice - 1);
        escribirMensaje(registroEncuentro, resultado + "\n", COLOR_INFORMACION);
        mostrarPersonaje();
        etiquetaObjeto.setVisible(false);
        objetosEncuentro.setVisible(false);
        confirmarObjeto.setVisible(false);
        escribirMensaje(registroCombate, "¡Comienza el combate!\n", COLOR_INFORMACION);
        // Timer espera entre turnos sin bloquear la ventana de Swing.
        turnos.setInitialDelay(0);
        turnos.start();
    }

    private void avanzarCombate() {
        boolean continua = controlador.avanzarCombate();
        mostrarPersonaje();
        if (!continua) {
            turnos.stop();
            if (controlador.getPersonaje().estaVivo()) {
                Objeto botin = controlador.getUltimoBotin();
                escribirMensaje(registroEncuentro, "El " + nombreEnemigoActual + " suelta "
                        + botin.getNombre() + ". Lo guardas en la mochila.\n", COLOR_INFORMACION);
                escribirMensaje(registroEncuentro, "¿Quieres continuar o finalizar la aventura?\n",
                        COLOR_INFORMACION);
                continuarAventura.setVisible(true);
                finalizarAventura.setVisible(true);
            } else {
                nuevaPartida.setVisible(true);
            }
        }
    }

    private void continuarAventura() {
        controlador.continuarAventura();
        continuarAventura.setVisible(false);
        finalizarAventura.setVisible(false);
        prepararEncuentro();
    }

    private void finalizarAventura() {
        controlador.finalizarAventura();
        continuarAventura.setVisible(false);
        finalizarAventura.setVisible(false);
        escribirMensaje(registroEncuentro, controlador.getPersonaje().getNombre()
                + " finaliza la aventura con vida tras superar "
                + controlador.getEnfrentamientosSuperados() + " enfrentamiento(s).\n", COLOR_INFORMACION);
        guardarResultado();
    }

    private void volverAlInicio() {
        nuevaPartida.setVisible(false);
        distribucionPantallas.show(pantallas, "inicio");
        setMinimumSize(new Dimension(760, 480));
        setSize(900, 600);
        setLocationRelativeTo(null);
        cargarRanking();
    }

    // SwingWorker realiza las consultas fuera del hilo que dibuja la ventana.
    private void cargarRanking() {
        int numeroConsulta = ++consultaRankingActual;
        estadoRanking.setText("Consultando el ranking...");
        vistasRanking.show(contenidoRanking, "estado");
        SwingWorker<ArrayList<EntradaRanking>, Void> consulta = new SwingWorker<ArrayList<EntradaRanking>, Void>() {
            @Override
            protected ArrayList<EntradaRanking> doInBackground() throws SQLException {
                return ranking.consultarRanking();
            }

            @Override
            protected void done() {
                if (numeroConsulta != consultaRankingActual) {
                    return;
                }
                try {
                    ArrayList<EntradaRanking> entradas = get();
                    if (entradas.isEmpty()) {
                        estadoRanking.setText("Todavía no hay resultados guardados.");
                        return;
                    }
                    mostrarRanking(entradas);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    estadoRanking.setText("La consulta del ranking se ha interrumpido.");
                } catch (ExecutionException e) {
                    estadoRanking.setText("No se ha podido consultar el ranking: " + e.getCause().getMessage());
                }
            }
        };
        consulta.execute();
    }

    private void mostrarRanking(ArrayList<EntradaRanking> entradas) {
        datosRanking.setRowCount(0);
        for (int i = 0; i < entradas.size(); i++) {
            EntradaRanking entrada = entradas.get(i);
            datosRanking.addRow(new Object[] {i + 1, entrada.getJugador(), entrada.getPersonaje(),
                    entrada.getEnemigosDerrotados()});
        }
        vistasRanking.show(contenidoRanking, "tabla");
    }

    private void guardarResultado() {
        String jugador = controlador.getJugador();
        String personaje = controlador.getPersonaje().getNombre();
        int enfrentamientos = controlador.getEnfrentamientosSuperados();
        escribirMensaje(registroEncuentro, "Guardando el resultado en el ranking...\n", COLOR_INFORMACION);

        SwingWorker<Void, Void> guardado = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws SQLException {
                ranking.guardarRecord(jugador, personaje, enfrentamientos);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    escribirMensaje(registroEncuentro, "¡Resultado guardado en el ranking!\n", COLOR_INFORMACION);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    escribirMensaje(registroEncuentro, "El guardado del resultado se ha interrumpido.\n",
                            COLOR_INFORMACION);
                } catch (ExecutionException e) {
                    escribirMensaje(registroEncuentro, "No se ha podido guardar el resultado: "
                            + e.getCause().getMessage() + "\n", COLOR_INFORMACION);
                } finally {
                    nuevaPartida.setVisible(true);
                }
            }
        };
        guardado.execute();
    }

    // JTextPane permite combinar colores y estilos en un mismo registro.
    private void escribirMensaje(JTextPane panel, String mensaje, Color color) {
        StyledDocument documento = panel.getStyledDocument();
        SimpleAttributeSet estilo = new SimpleAttributeSet();
        StyleConstants.setForeground(estilo, color);
        int inicio = documento.getLength();
        try {
            documento.insertString(inicio, mensaje, estilo);
        } catch (BadLocationException e) {
            throw new IllegalStateException("No se ha podido añadir el mensaje al registro.", e);
        }

        Personaje personaje = controlador.getPersonaje();
        if (personaje != null) {
            resaltarNombre(documento, mensaje, inicio, personaje.getNombre());
        }
        resaltarNombre(documento, mensaje, inicio, nombreEnemigoActual);
        panel.setCaretPosition(documento.getLength());
    }

    private void resaltarNombre(StyledDocument documento, String mensaje, int inicio, String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return;
        }
        SimpleAttributeSet destacado = new SimpleAttributeSet();
        StyleConstants.setBold(destacado, true);
        StyleConstants.setItalic(destacado, true);
        int posicion = mensaje.indexOf(nombre);
        while (posicion >= 0) {
            int fin = posicion + nombre.length();
            // Evitamos destacar el nombre si solo forma parte de otra palabra.
            boolean inicioValido = posicion == 0 || !esParteDePalabra(mensaje.charAt(posicion - 1))
                    || !esParteDePalabra(nombre.charAt(0));
            boolean finValido = fin == mensaje.length() || !esParteDePalabra(mensaje.charAt(fin))
                    || !esParteDePalabra(nombre.charAt(nombre.length() - 1));
            if (inicioValido && finValido) {
                documento.setCharacterAttributes(inicio + posicion, nombre.length(), destacado, false);
            }
            posicion = mensaje.indexOf(nombre, fin);
        }
    }

    private boolean esParteDePalabra(char caracter) {
        return Character.isLetterOrDigit(caracter) || caracter == '_';
    }

    public static void main(String[] args) {
        // Swing crea y actualiza sus ventanas en su propio hilo de eventos.
        SwingUtilities.invokeLater(() -> new VentanaJuego().setVisible(true));
    }
}
