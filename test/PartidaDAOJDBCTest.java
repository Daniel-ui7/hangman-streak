
import dao.*;
import conexion.ConexionH2;
import dto.JugadorDTO;
import dto.PartidaDTO;
import dto.RankingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PartidaDAOJDBCTest {

    private PartidaDAO partidaDAO;
    private JugadorDAO jugadorDAO;

    @BeforeEach
    void setUp() {
        ConexionH2.inicializarBaseDeDatos();
        partidaDAO = new PartidaDAOJDBC();
        jugadorDAO = new JugadorDAOJDBC();
    }

    @Test
    void testGuardarPartidaYObtenerRankingOrdenado_RN05() {
        JugadorDTO j = new JugadorDTO("RankUser_" + System.currentTimeMillis());
        jugadorDAO.guardar(j);

        PartidaDTO pBaja = new PartidaDTO(j.obtenerId(), "SOL", 4, "VICTORIA", 170);
        PartidaDTO pAlta = new PartidaDTO(j.obtenerId(), "ARQUITECTURA", 0, "VICTORIA", 340);

        assertTrue(partidaDAO.guardar(pBaja));
        assertTrue(partidaDAO.guardar(pAlta));

        List<RankingDTO> ranking = partidaDAO.obtenerRanking();
        assertFalse(ranking.isEmpty());

        for (int i = 0; i < ranking.size() - 1; i++) {
            assertTrue(ranking.get(i).obtenerPuntaje() >= ranking.get(i + 1).obtenerPuntaje());
        }
    }
}