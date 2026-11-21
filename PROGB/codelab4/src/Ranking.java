import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ranking {

    // Datos de la base de datos local; cambia el puerto si MySQL usa otro.
    private static final String URL = "jdbc:mysql://localhost:3306/codelabs";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";
    private static final String SQL_SELECT="SELECT jugador, personaje, enemigos_derrotados FROM ranking ORDER BY enemigos_derrotados DESC LIMIT 10";
    private static final String SQL_INSERT = "INSERT INTO ranking (jugador, personaje, enemigos_derrotados) VALUES (?, ?, ?)";

    /**
     * Muestra los diez mejores resultados si hay registros guardados.
     *
     * @throws SQLException si falla la conexión o la consulta
     */
    public void mostrarRanking() throws SQLException {
        // Ordenamos de mayor a menor y pedimos como máximo diez resultados.
        String consulta = SQL_SELECT;

        // try-with-resources cierra automáticamente la conexión, la consulta y el resultado.
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            // next() avanza al primer registro y devuelve false si no hay ninguno.
            if (resultado.next()) {
                ColorTerminal.INFORMACION.imprimirLinea("════════════ RANKING ════════════");
                ColorTerminal.INFORMACION.imprimirFormato("%-20s %-20s %s%n",
                        "Jugador", "Personaje", "Enemigos derrotados");

                // Ya estamos en el primer registro, así que lo mostramos antes de avanzar.
                do {
                    String jugador = resultado.getString("jugador");
                    String personaje = resultado.getString("personaje");
                    int enemigosDerrotados = resultado.getInt("enemigos_derrotados");
                    ColorTerminal.INFORMACION.imprimirFormato("%-20s %-20s %d%n",
                            jugador, personaje, enemigosDerrotados);
                } while (resultado.next());
            }
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
        // Los signos de interrogación son espacios para los valores que añadiremos después.
        String consulta = SQL_INSERT;

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            // PreparedStatement coloca cada valor en su posición, empezando por la 1.
            sentencia.setString(1, jugador);
            sentencia.setString(2, personaje);
            sentencia.setInt(3, enemigosDerrotados);
            sentencia.executeUpdate();
        }
    }
}
