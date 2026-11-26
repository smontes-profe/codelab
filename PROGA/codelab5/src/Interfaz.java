import java.util.InputMismatchException;
import java.util.Scanner;

import java.awt.BorderLayout;
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
    private Scanner scanner;
    private String colorMensajes;
    private Heroe personaje;
    private Enemigo enemigoActual;

    private JLabel mensajeInicio;

    private static Interfaz instancia;
    private JFrame ventana;
    private JLabel nombreHeroe;
    private JLabel vidaHeroe;
    private JLabel danioHeroe;
    private JTextArea inventarioHeroe;

    public static Interfaz getInstancia() {
        if (instancia == null) {
            instancia = new Interfaz();
        }
        return instancia;
    }

    private Interfaz() {
        scanner = new Scanner(System.in);
        colorMensajes = GameConfig.COLOR_INFORMATIVO;
    }

    public void mostrarInicio(GameEngine motor) {
        ventana = new JFrame("Leyendas del código");
        ventana.setSize(500, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        ventana.setVisible(true);
    }

    public void mostrarVistaPrincipal(Heroe heroe) {
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

        // Reservamos esta zona para los encuentros de una siguiente sesión.
        JLabel zonaEncuentro = new JLabel(
                "Aquí aparecerán los encuentros", SwingConstants.CENTER);
        panelPrincipal.add(zonaEncuentro, BorderLayout.CENTER);

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

    /* OLD */

    public void mostrarHeroeCreado(Heroe heroe) {
        mensajeInicio.setText("¡" + heroe.getNombre()
                + " está preparado! Vida: " + heroe.getVida());
    }

    public void salir() {
        scanner.close();
    }

    public void imprimirLinea() {
        System.out.println(colorMensajes + "=================================================");
    }

    public void imprimirMensaje(String mensaje) {
        System.out.println(colorMensajes + mensaje);
    }

    public void imprimirCabecera(String mensaje) {
        System.out.println(colorMensajes + "\n+|=========> " + mensaje + " <=========|+");
    }

    public void imprimirFicha() {
        imprimirCabecera("FICHA DEL HÉROE");
        imprimirMensaje("Nombre: " + personaje.getNombre());
        imprimirMensaje("Salud: " + personaje.getVida());
        imprimirMensaje("Inventario: ");
        for (String objeto : personaje.getInventario()) {
            imprimirMensaje("- " + objeto);
        }
    }

    public Heroe inicializarHeroe() {
        colorMensajes = GameConfig.COLOR_INFORMATIVO;
        imprimirLinea();
        imprimirCabecera("       BIENVENIDO AL REINO DE JAVA THE HUT       ");
        imprimirLinea();
        imprimirMensaje("Introduce el nombre de tu héroe: ");
        personaje = new Heroe(scanner.nextLine());
        imprimirCabecera("¡COMIENZA LA AVENTURA!");
        return personaje;
    }

    public boolean mostrarGameOver() {
        if (!personaje.estaVivo()) {
            colorMensajes = GameConfig.COLOR_ENEMIGO;
            imprimirCabecera(personaje.getNombre() + " ha sido derrotado! ");
        } else {
            colorMensajes = GameConfig.COLOR_JUGADOR;
            imprimirCabecera(personaje.getNombre() + " ha escapado con vida!");
        }
        colorMensajes = GameConfig.COLOR_INFORMATIVO;
        imprimirMensaje("Enemigos derrotados:" + personaje.enemigosDerrotados);
        imprimirCabecera("GAME OVER");
        // Opciones de reinicio
        colorMensajes = GameConfig.COLOR_OPCIONES;
        imprimirLinea();
        imprimirCabecera("¿Quieres empezar de nuevo?");
        imprimirMensaje(">1. Sí");
        imprimirMensaje(">2. No");
        boolean continuarJugando = (validarOpcion(2) == 1);
        scanner.nextLine();
        if (!continuarJugando)
            imprimirCabecera("¡HASTA OTRA!");
        return continuarJugando;
    }

    private int validarOpcion(int numValidas) {
        int opcion = 0;
        try {
            opcion = scanner.nextInt();
            if ((opcion > numValidas) || (opcion < 1)) {
                imprimirMensaje("Opción inválida. Elije un número entre 1 y " + numValidas);
                return validarOpcion(numValidas);
            }
            return opcion;
        } catch (InputMismatchException e) {
            imprimirMensaje("Opción inválida. Elije un número entre 1 y " + numValidas);
            scanner.next();
            return validarOpcion(numValidas);
        }
    }

    public void iniciarNuevoEncuentro(Enemigo nuevoEnemigo) {
        enemigoActual = nuevoEnemigo;
        imprimirCabecera("NUEVO ENCUENTRO");
        imprimirMensaje("\n¡Un " + enemigoActual.getNombre() + " salvaje aparece!");
    }

    public int mostrarOpcionesEncuentro() {
        colorMensajes = GameConfig.COLOR_OPCIONES;
        int numValidas = 2;
        imprimirMensaje("Qué quieres hacer?");
        imprimirMensaje(">1. Retirarte");
        imprimirMensaje(">2. Continuar sin usar poción");
        if (personaje.tienePociones()) {
            imprimirMensaje(">3. Continuar usando una poción");
            numValidas = 3;
        }
        return validarOpcion(numValidas);
    }

    public void mostrarDanioAlEnemigo(int danio) {
        colorMensajes = GameConfig.COLOR_JUGADOR;
        imprimirMensaje(colorMensajes + "\n¡" + personaje.getNombre() + " ataca al " + enemigoActual.getNombre()
                + " con la espada y le hace " + danio + " de daño!");
    }

    public void mostrarDanioAlHeroe(int danioTemporal) {
        colorMensajes = GameConfig.COLOR_ENEMIGO;
        imprimirMensaje("Vida del " + enemigoActual.getNombre() + ": " + enemigoActual.getVida() + " HP");
        imprimirMensaje(enemigoActual.getNombre() + " ataca a " + personaje.getNombre()
                + " y le hace " + danioTemporal + " de daño!");
        imprimirMensaje("Vida de " + personaje.getNombre() + ": " + personaje.getVida() + " HP");
    }

    public void mostrarLoot(String objetoLoot) {
        imprimirMensaje(personaje.getNombre() + " ha derrotado al " + enemigoActual.getNombre()
                + "! El enemigo suelta un objeto.");
        imprimirMensaje("¡Has conseguido " + objetoLoot + "! Se añade a tu inventario.");
    }

    public void finalizarCombatLoop() {
        try {
            Thread.sleep(GameConfig.TIEMPO_ESPERA);
        } catch (InterruptedException e) {
            // Aquí no hace falta hacer nada
        }
    }
}
