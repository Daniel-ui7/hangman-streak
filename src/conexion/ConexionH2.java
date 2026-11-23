package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionH2 {

    private static final String URL = "jdbc:h2:./data/hangman_db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void inicializarBaseDeDatos() {
        String sqlJugadores = "CREATE TABLE IF NOT EXISTS jugadores ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nombre VARCHAR(100) UNIQUE NOT NULL"
                + ");";

        String sqlPalabras = "CREATE TABLE IF NOT EXISTS palabras ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "palabra VARCHAR(100) NOT NULL, "
                + "categoria VARCHAR(50), "
                + "dificultad VARCHAR(50)"
                + ");";

        String sqlPartidas = "CREATE TABLE IF NOT EXISTS partidas ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "jugador_id INT, "
                + "palabra VARCHAR(100), "
                + "errores INT, "
                + "resultado VARCHAR(20), "
                + "puntaje INT, "
                + "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (jugador_id) REFERENCES jugadores(id)"
                + ");";

        try (Connection conn = obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlJugadores);
            stmt.execute(sqlPalabras);
            stmt.execute(sqlPartidas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}