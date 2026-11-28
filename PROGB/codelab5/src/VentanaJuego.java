import java.awt.BorderLayout;
import java.awt.GridLayout;
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

    // Campos donde el jugador escribe los nombres.
    private final JTextField nombreJugador = new JTextField();
    private final JTextField nombrePersonaje = new JTextField();
    // values() obtiene todas las opciones del enum para el desplegable.
    private final JComboBox<Profesion> profesion = new JComboBox<>(Profesion.values());
    // Área donde se mostrará la ficha.
    private final JTextArea ficha = new JTextArea(10, 30);

    public VentanaJuego() {
        super("Leyendas del código");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // El panel coloca los campos y sus etiquetas en tres filas y dos columnas.
        JPanel datos = new JPanel(new GridLayout(3, 2, 5, 5));
        datos.add(new JLabel("Nombre del jugador:"));
        datos.add(nombreJugador);
        datos.add(new JLabel("Nombre del personaje:"));
        datos.add(nombrePersonaje);
        datos.add(new JLabel("Profesión:"));
        datos.add(profesion);

        JButton crearPersonaje = new JButton("Crear personaje");
        // Al pulsar el botón, se ejecuta el método que crea y muestra el personaje.
        crearPersonaje.addActionListener(evento -> mostrarPersonaje());

        // La ficha se puede leer, pero no editar; el texto se ajusta al ancho.
        ficha.setEditable(false);
        ficha.setLineWrap(true);
        ficha.setWrapStyleWord(true);

        // Distribuimos el formulario arriba, la ficha en el centro y el botón abajo.
        add(datos, BorderLayout.NORTH);
        add(new JScrollPane(ficha), BorderLayout.CENTER);
        add(crearPersonaje, BorderLayout.SOUTH);
        // Ajustamos el tamaño de la ventana a su contenido y la centramos.
        pack();
        setLocationRelativeTo(null);
    }

    private void mostrarPersonaje() {
        String nombre = nombrePersonaje.getText();
        int indice = profesion.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Elige una profesión.");
            return;
        }
        // La profesión elegida sabe qué tipo de personaje debe crear.
        Profesion profesionElegida = profesion.getItemAt(indice);
        Personaje personaje = profesionElegida.crearPersonaje(nombre);

        // Construimos el texto de la ficha antes de mostrarlo en el área de texto.
        StringBuilder texto = new StringBuilder();
        texto.append("Jugador: ").append(nombreJugador.getText()).append("\n");
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

    public static void main(String[] args) {
        // Swing crea y actualiza sus ventanas en su propio hilo de eventos.
        SwingUtilities.invokeLater(() -> new VentanaJuego().setVisible(true));
    }
}
