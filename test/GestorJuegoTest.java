import logica.*;
import conexion.ConexionH2;
import dto.JugadorDTO;
import dto.PalabraDTO;
import dto.PartidaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestorJuegoTest {

    private GestorJuego gestor;

    @BeforeEach
    void setUp() {
        ConexionH2.inicializarBaseDeDatos();
        gestor = new GestorJuego();
    }

    @Test
    void testRegistrarJugadorUnico() {
        String nombreUnico = "Player_" + System.currentTimeMillis();
        JugadorDTO j1 = gestor.registrarJugador(nombreUnico);
        JugadorDTO j2 = gestor.registrarJugador(nombreUnico);

        assertNotNull(j1);
        assertTrue(j1.obtenerId() > 0);
        assertEquals(j1.obtenerId(), j2.obtenerId());
    }

    @Test
    void testFinalizarPartidaGuardaEnHistorial() {
        String nombreUnico = "Tester_" + System.currentTimeMillis();
        JugadorDTO jugador = gestor.registrarJugador(nombreUnico);
        PalabraDTO palabra = new PalabraDTO("TEST", "General", "Fácil");

        JuegoLogica juego = new JuegoLogica(jugador, palabra);
        juego.intentarLetra('T');
        juego.intentarLetra('E');
        juego.intentarLetra('S');

        PartidaDTO guardada = gestor.finalizarPartida(juego);
        assertEquals("VICTORIA", guardada.obtenerResultado());
        assertEquals(260, guardada.obtenerPuntaje());
        assertFalse(gestor.verHistorial(jugador.obtenerId()).isEmpty());
    }
}