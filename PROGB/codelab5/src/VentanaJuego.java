import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VentanaJuego extends JFrame {

    private final JTextField nombreJugador = new JTextField();
    private final JTextField nombrePersonaje = new JTextField();
    private final JComboBox<String> profesion = new JComboBox<>(new String[] {"Guerrero", "Mago"});
    private final JTextArea ficha = new JTextArea(10, 30);

    public VentanaJuego() {
        super("Leyendas del código");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel datos = new JPanel(new GridLayout(3, 2, 5, 5));
        datos.add(new JLabel("Nombre del jugador:"));
        datos.add(nombreJugador);
        datos.add(new JLabel("Nombre del personaje:"));
        datos.add(nombrePersonaje);
        datos.add(new JLabel("Profesión:"));
        datos.add(profesion);

        JButton crearPersonaje = new JButton("Crear personaje");
        crearPersonaje.addActionListener(evento -> mostrarPersonaje());

        ficha.setEditable(false);
        ficha.setLineWrap(true);
        ficha.setWrapStyleWord(true);

        add(datos, BorderLayout.NORTH);
        add(new JScrollPane(ficha), BorderLayout.CENTER);
        add(crearPersonaje, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private void mostrarPersonaje() {
        String nombre = nombrePersonaje.getText();
        Personaje personaje;
        if (profesion.getSelectedIndex() == 0) {
            personaje = new Guerrero(nombre);
        } else {
            personaje = new Mago(nombre);
        }

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
        SwingUtilities.invokeLater(() -> new VentanaJuego().setVisible(true));
    }
}
