import dto.RankingDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RankingDTOTest {

    @Test
    void testAsignacionRanking() {
        LocalDateTime ahora = LocalDateTime.now();
        RankingDTO ranking = new RankingDTO(1, "Daniel", 500, ahora);

        assertEquals(1, ranking.obtenerPosicion());
        assertEquals("Daniel", ranking.obtenerNombreJugador());
        assertEquals(500, ranking.obtenerPuntaje());
        assertEquals(ahora, ranking.obtenerFecha());
    }
    @Test
    void testConstructorVacioYSetters() {
        RankingDTO ranking = new RankingDTO();
        LocalDateTime ahora = LocalDateTime.now();

        ranking.establecerPosicion(1);
        ranking.establecerNombreJugador("Alejandro");
        ranking.establecerPuntaje(450);
        ranking.establecerFecha(ahora);

        assertEquals(1, ranking.obtenerPosicion());
        assertEquals("Alejandro", ranking.obtenerNombreJugador());
        assertEquals(450, ranking.obtenerPuntaje());
        assertEquals(ahora, ranking.obtenerFecha());
    }

    @Test
    void testPosicionYOrdenEnRanking() {
        RankingDTO primero = new RankingDTO(1, "Daniel", 500, LocalDateTime.now());
        RankingDTO segundo = new RankingDTO(2, "Carlos", 350, LocalDateTime.now());

        assertTrue(primero.obtenerPosicion() < segundo.obtenerPosicion(), "La posición 1 debe ser menor que la posición 2");
        assertTrue(primero.obtenerPuntaje() > segundo.obtenerPuntaje(), "El primer lugar debe tener mayor puntaje que el segundo");
    }

    @Test
    void testManejoDePuntajesNegativosOZero() {
        RankingDTO ranking = new RankingDTO(10, "JugadorNuevo", 0, LocalDateTime.now());

        assertEquals(0, ranking.obtenerPuntaje());
        assertEquals(10, ranking.obtenerPosicion());
    }
}