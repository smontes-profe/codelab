import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.SwingUtilities;

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
    // La ficha y el registro ocupan paneles separados durante la partida.
    private final JTextArea ficha = new JTextArea(10, 30);
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
        ficha.setEditable(false);
        ficha.setLineWrap(true);
        ficha.setWrapStyleWord(true);
        JScrollPane panelFicha = new JScrollPane(ficha);
        panelFicha.setBorder(BorderFactory.createTitledBorder("Ficha e inventario"));
        panelFicha.setPreferredSize(new Dimension(250, 0));
        partida.add(panelFicha, BorderLayout.WEST);

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

        // Construimos el texto de la ficha antes de mostrarlo en el área de texto.
        StringBuilder texto = new StringBuilder();
        texto.append("Jugador: ").append(controlador.getJugador()).append("\n");
        texto.append("Personaje: ").append(personaje.getNombre()).append("\n");
        texto.append("Profesión: ").append(personaje.getProfesion()).append("\n");
        texto.append("Vida: ").append(personaje.getVida()).append("\n");
        texto.append("Mochila:\n");
        if (personaje.getCantidadObjetos() == 0) {
            texto.append("  (vacía)\n");
        } else {
            for (int i = 0; i < personaje.getCantidadObjetos(); i++) {
                texto.append("  - ").append(personaje.getObjetoMochila(i).getNombre()).append("\n");
            }
        }
        ficha.setText(texto.toString());
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
