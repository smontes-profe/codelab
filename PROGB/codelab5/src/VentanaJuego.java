import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class VentanaJuego extends JFrame {

    // El controlador conserva los datos de la partida; la ventana los muestra.
    private final ControladorJuego controlador = new ControladorJuego();

    // CardLayout permite cambiar entre el formulario y la pantalla de partida.
    private final CardLayout distribucionPantallas = new CardLayout();
    private final JPanel pantallas = new JPanel(distribucionPantallas);

    // Campos donde el jugador escribe los nombres.
    private final JTextField nombreJugador = new JTextField();
    private final JTextField nombrePersonaje = new JTextField();
    // values() obtiene todas las opciones del enum para el desplegable.
    private final JComboBox<Profesion> profesion = new JComboBox<>(Profesion.values());
    // Componentes de la ficha que actualizaremos cuando cambie el personaje.
    private final JTextArea jugadorFicha = new JTextArea();
    private final JTextPane tituloPersonaje = new JTextPane();
    private final JLabel vidaPersonaje = new JLabel();
    private final JLabel danioPersonaje = new JLabel();
    private final JTextArea inventario = new JTextArea();
    private final JTextArea registro = new JTextArea(10, 30);
    private final JButton prepararEncuentro = new JButton("Preparar encuentro");

    public VentanaJuego() {
        super("Leyendas del código");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pantallas.add(crearPantallaInicio(), "inicio");
        pantallas.add(crearPantallaPartida(), "partida");
        setContentPane(pantallas);
        setSize(480, 240);
        setLocationRelativeTo(null);
    }

    private JPanel crearPantallaInicio() {
        JPanel inicio = new JPanel(new BorderLayout(10, 10));
        inicio.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // El panel coloca los campos y sus etiquetas en tres filas y dos columnas.
        JPanel datos = new JPanel(new GridLayout(3, 2, 5, 5));
        datos.add(new JLabel("Nombre del jugador:"));
        datos.add(nombreJugador);
        datos.add(new JLabel("Nombre del personaje:"));
        datos.add(nombrePersonaje);
        datos.add(new JLabel("Profesión:"));
        datos.add(profesion);

        JButton crearPersonaje = new JButton("Iniciar partida");
        // Al pulsar el botón, comienza la partida y se muestra la ficha.
        crearPersonaje.addActionListener(evento -> iniciarPartida());
        inicio.add(datos, BorderLayout.CENTER);
        inicio.add(crearPersonaje, BorderLayout.SOUTH);
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

        registro.setEditable(false);
        registro.setLineWrap(true);
        registro.setWrapStyleWord(true);
        JScrollPane panelRegistro = new JScrollPane(registro);
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Aventura"));

        prepararEncuentro.setEnabled(false);
        prepararEncuentro.addActionListener(evento -> prepararEncuentro());
        JPanel opciones = new JPanel();
        opciones.setBorder(BorderFactory.createTitledBorder("Opciones"));
        opciones.add(prepararEncuentro);

        JPanel aventura = new JPanel(new BorderLayout(0, 10));
        aventura.add(panelRegistro, BorderLayout.CENTER);
        aventura.add(opciones, BorderLayout.SOUTH);
        partida.add(aventura, BorderLayout.CENTER);
        return partida;
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
        mostrarPersonaje();
        registro.setText("La aventura está a punto de comenzar.\n");
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
        if (encuentro.isEnemigoALejos()) {
            registro.append("\n¡Un " + nombreEnemigo + " aparece a lo lejos!\n");
        } else {
            registro.append("\n¡Un " + nombreEnemigo + " aparece frente a "
                    + controlador.getPersonaje().getNombre() + "!\n");
        }
        registro.append("El combate estará disponible en el siguiente paso.\n");
        prepararEncuentro.setEnabled(false);
    }

    public static void main(String[] args) {
        // Swing crea y actualiza sus ventanas en su propio hilo de eventos.
        SwingUtilities.invokeLater(() -> new VentanaJuego().setVisible(true));
    }
}
