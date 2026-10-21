import java.util.Scanner;

public class Juego {
    public static void main(String[] args) {

        final int DIFICULTAD_DIFICIL = 1;
        final int DIFICULTAD_MEDIA = 2;
        final int DIFICULTAD_FACIL = 3;

        final int DANIO_MINIMO_DIFICIL = 10;
        final int DANIO_MAXIMO_DIFICIL = 50;
        final int DANIO_MINIMO_MEDIO = 30;
        final int DANIO_MAXIMO_MEDIO = 110;
        final int DANIO_MINIMO_FACIL = 50;
        final int DANIO_MAXIMO_FACIL = 100;

        final int VIDA_MAXIMA_DIFICIL = 50;
        final int VIDA_MAXIMA_MEDIA = 100;
        final int VIDA_MAXIMA_FACIL = 150;
        final int POCIONES_DIFICIL = 3;
        final int POCIONES_MEDIA = 3;
        final int POCIONES_FACIL = 5;
        final int CURACION_DIFICIL = 35;
        final int CURACION_MEDIA = 55;
        final int CURACION_FACIL = 75;
        final String NOMBRE_DIFICULTAD_DIFICIL = "Difícil";
        final String NOMBRE_DIFICULTAD_MEDIA = "Medio";
        final String NOMBRE_DIFICULTAD_FACIL = "Fácil";
        int vidaMaxima;
        int numeroPociones;
        int curacionPocion;
        int danioMinimo;
        int danioMaximo;
        int dificultad;
        String nombreDificultad;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escribe el nombre de tu personaje:");
        String nombrePersonaje = scanner.nextLine();
        System.out.println("Selecciona la dificultad de la partida:");
        System.out.println("--------------------");
        System.out.println("1. " + NOMBRE_DIFICULTAD_DIFICIL);
        System.out.println("2. " + NOMBRE_DIFICULTAD_MEDIA);
        System.out.println("3. " + NOMBRE_DIFICULTAD_FACIL);
        System.out.println("--------------------");

        if (!scanner.hasNextInt()) {
            System.out.println("Debes introducir un número entero.");
            scanner.close();
            return;
        } else {
            dificultad = scanner.nextInt();
            if (dificultad < DIFICULTAD_DIFICIL || dificultad > DIFICULTAD_FACIL) {
                System.out.println("Opción no válida");
                scanner.close();
                return;
            }
        }
        //
        // He dejado la solución con if, pero también se puede hacer con un while
        // para que el usuario tenga varias oportunidades de introducir un número
        // válido.
        // while (!scanner.hasNextInt()) {
        // System.out.println("Debes introducir un número entero.");
        // scanner.next();
        // }
        // dificultad = scanner.nextInt();
        // if (dificultad < DIFICULTAD_DIFICIL || dificultad > DIFICULTAD_FACIL) {
        // System.out.println("Opción no válida");
        // scanner.close();
        // return;
        // }

        // Mantener juntos los valores de cada dificultad evita configurarlos en varios
        // sitios.

        switch (dificultad) {
            case DIFICULTAD_DIFICIL:
                vidaMaxima = VIDA_MAXIMA_DIFICIL;
                numeroPociones = POCIONES_DIFICIL;
                curacionPocion = CURACION_DIFICIL;
                danioMinimo = DANIO_MINIMO_DIFICIL;
                danioMaximo = DANIO_MAXIMO_DIFICIL;
                nombreDificultad = NOMBRE_DIFICULTAD_DIFICIL;
                break;
            case DIFICULTAD_MEDIA:
                vidaMaxima = VIDA_MAXIMA_MEDIA;
                numeroPociones = POCIONES_MEDIA;
                curacionPocion = CURACION_MEDIA;
                danioMinimo = DANIO_MINIMO_MEDIO;
                danioMaximo = DANIO_MAXIMO_MEDIO;
                nombreDificultad = NOMBRE_DIFICULTAD_MEDIA;
                break;
            default:
                vidaMaxima = VIDA_MAXIMA_FACIL;
                numeroPociones = POCIONES_FACIL;
                curacionPocion = CURACION_FACIL;
                danioMinimo = DANIO_MINIMO_FACIL;
                danioMaximo = DANIO_MAXIMO_FACIL;
                nombreDificultad = NOMBRE_DIFICULTAD_FACIL;
        }

        int vidaActual = vidaMaxima;
        // El +1 incluye el daño máximo; sin él, ese valor no podría salir.
        int danio = (int) (Math.random() * (danioMaximo - danioMinimo + 1)) + danioMinimo;

        System.out.println("-----------------");
        System.out.println("Nombre: " + nombrePersonaje);
        System.out.println("Vida: " + vidaActual);
        System.out.println("Pociones: " + numeroPociones);
        System.out.println("Dificultad: " + nombreDificultad);

        System.out.println("...Entrando al mundo");
        System.out.println("Exploras una cueva y aparece un Trasgo, te ataca y te hace " + danio + " de daño");

        vidaActual = vidaActual - danio;

        if (vidaActual > 0) {
            System.out.println("Tu vida se ha quedado en " + vidaActual);
            System.out.println("Sigues en pie");
        } else {
            System.out.println("Game Over");
        }

        // Aplicar la poción en un solo bloque evita repetir y descoordinar esta lógica.
        if (vidaActual > 0 && vidaActual + curacionPocion < vidaMaxima) {
            if (numeroPociones > 0) {
                System.out.println("Tomando poción");
                vidaActual = vidaActual + curacionPocion;
                numeroPociones--;
            } else {
                System.out.println("No tienes pociones disponibles");
            }
        }

        System.out.println("Tu vida actual es = " + vidaActual);
        System.out.println("En tu inventario tienes " + numeroPociones + " pociones");

        if (vidaActual > 0) {
            System.out.println("Atacas al Trasgo con un golpe crítico y lo matas");
            System.out.println("Continúas con tu viaje...");
        }

        scanner.close();
    }
}
