public enum ColorTerminal {

    // Estas son las distintas opciones que yo he pensado, pero vaya, 
    // vosotros elegida las vuestras
    INFORMACION("\u001B[36m"),
    FICHA("\u001B[94m"),
    JUGADOR("\u001B[32m"),
    ENEMIGO("\u001B[31m"),
    OPCION("\u001B[33m"),
    ERROR("\u001B[91m");

    private static final String RESET = "\u001B[0m";
    private final String codigo;

    ColorTerminal(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Imprime un mensaje con este color sin añadir un salto de línea.
     *
     * @param mensaje texto que se mostrará
     */
    public void imprimir(String mensaje) {
        System.out.print(codigo + mensaje + RESET);
    }

    /**
     * Imprime un mensaje con este color y añade un salto de línea.
     *
     * @param mensaje texto que se mostrará
     */
    public void imprimirLinea(String mensaje) {
        System.out.println(codigo + mensaje + RESET);
    }

    /**
     * Imprime texto formateado con este color.
     *
     * @param formato formato compatible con {@link java.io.PrintStream#printf(String, Object...)}
     * @param argumentos valores usados para completar el formato
     */
    public void imprimirFormato(String formato, Object... argumentos) {
        System.out.print(codigo);
        System.out.printf(formato, argumentos);
        System.out.print(RESET);
    }
}
