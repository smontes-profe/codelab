import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ranking {

    private static final String URL = "jdbc:mysql://localhost:3306/codelabs";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";
    private static final String SQL_TOP10="SELECT jugador, personaje, enemigos_derrotados FROM ranking ORDER BY enemigos_derrotados DESC LIMIT 10";
    private static final String SQL_INSERT = "INSERT INTO ranking (jugador, personaje, enemigos_derrotados) VALUES (?, ?, ?)";

    /**
     * Consulta los diez mejores resultados.
     *
     * @return resultados ordenados de mayor a menor
     * @throws SQLException si falla la conexión o la consulta
     */
    public ArrayList<EntradaRanking> consultarRanking() throws SQLException {
        ArrayList<EntradaRanking> entradas = new ArrayList<>();
        String consulta = SQL_TOP10;
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                entradas.add(new EntradaRanking(resultado.getString("jugador"),
                        resultado.getString("personaje"), resultado.getInt("enemigos_derrotados")));
            }
        }
        return entradas;
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
        String consulta = SQL_INSERT;
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, jugador);
            sentencia.setString(2, personaje);
            sentencia.setInt(3, enemigosDerrotados);
            sentencia.executeUpdate();
        }
    }
}
