import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ranking {

    // Datos de la base de datos local; cambia el puerto si MySQL usa otro.
    private static final String URL = "jdbc:mysql://localhost:3306/codelabs";
    private static final String USUARIO = "root";
    //Si usáis xampp tenemos que cambiar usuario t pass por root y ""
    private static final String CONTRASENA = "";
    // Las consultas que vamos a hacer contra la base de datos
    // para obtener el top10
    private static final String SQL_TOP10="SELECT jugador, personaje, enemigos_derrotados FROM ranking ORDER BY enemigos_derrotados DESC LIMIT 10";
    // y para insertar un nuevo record
    // Los signos de interrogación son espacios para los valores que añadiremos después.
    private static final String SQL_INSERT = "INSERT INTO ranking (jugador, personaje, enemigos_derrotados) VALUES (?, ?, ?)";
    // podemos copiar y pegar las consultas con valores de prueba
    // en phpmyadmin para comprobar que son correctas

    /**
     * Muestra los diez mejores resultados si hay registros guardados.
     *
     * @throws SQLException si falla la conexión o la consulta
     */
    public void mostrarRanking() throws SQLException {
        //seleccionamos la consulta SELECT del top 10
        String consulta = SQL_TOP10;
        // Aquí con este try establecemos la conexión con la URL, USUARIO 
        // y CONTRASENA establecidos. Si fallara, 
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            // no sé si habéis visto PreparedStatement
            // pero es la manera más moderna de realizar consultas sobre BBDD
            // de forma segura evitando SQLInjection
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            //recogemos el resultado en un ResultSet, que no es más que una tabla de datos
            ResultSet resultado = sentencia.executeQuery()) {
            // next() avanza al primer registro y devuelve false si no hay ninguno.
            if (resultado.next()) {
                ColorTerminal.INFORMACION.imprimirLinea("════════════ RANKING ════════════");
                // Dibujamos los bordes y dejamos un ancho fijo para cada columna.
                ColorTerminal.INFORMACION.imprimirLinea("┌──────────────────────┬──────────────────────┬─────────────────────┐");
                ColorTerminal.INFORMACION.imprimirFormato("│ %-20s │ %-20s │ %-19s │%n",
                        "Jugador", "Personaje", "Enemigos derrotados");
                ColorTerminal.INFORMACION.imprimirLinea("├──────────────────────┼──────────────────────┼─────────────────────┤");

                // como hay al menos un registro (si no, no habria entrado en el if)
                // hago al menos una iteración del bucle
                // para cada iteración, resultado sería una "fila"
                // o registro de la tabla revuelta
                do {
                    // recogemos cada "columna" de la fila, accediendo 
                    // a su nombre en la tabla de la BBDD
                    // y las guardamos en variables temporales
                    // para imprimirlas en el ranking
                    String jugador = resultado.getString("jugador");
                    String personaje = resultado.getString("personaje");
                    int enemigosDerrotados = resultado.getInt("enemigos_derrotados");
                    ColorTerminal.INFORMACION.imprimirFormato("│ %-20s │ %-20s │ %19d │%n",
                            jugador, personaje, enemigosDerrotados);
                } while (resultado.next());
                //ya no quedan registros, pintamos el final de la tabla
                ColorTerminal.INFORMACION.imprimirLinea("└──────────────────────┴──────────────────────┴─────────────────────┘");
            }
        }catch (SQLException e) {
            //si falla la conexión o la consulta, lanzamos la excepción para que
            //la gestione quien llame a este método
            throw e;
        }
    }

    /**
     * Guarda el resultado de una partida en la tabla de ranking.
     *
     * @param jugador nombre del jugador
     * @param personaje nombre del personaje
     * @param enemigosDerrotados cantidad de enemigos derrotados
     * @throws SQLException si falla la conexión o la inserción
     */
    public void guardarRecord(String jugador, String personaje, int enemigosDerrotados) throws SQLException {
        //usamos la constante del SQL para el INSERT que tenemos lista
        String consulta = SQL_INSERT;
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            // PreparedStatement coloca cada valor en su posición (de las "?"),
            // empezando por la 1.
            sentencia.setString(1, jugador);
            sentencia.setString(2, personaje);
            sentencia.setInt(3, enemigosDerrotados);
            sentencia.executeUpdate();
        }
    }
}
