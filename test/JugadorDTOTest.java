
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dto.*;

class JugadorDTOTest {

    private JugadorDTO jugador;

    @BeforeEach
    void setUp() {
        jugador = new JugadorDTO(1, "Daniel");
    }

    @Test
    void testCreacionYGetters() {
        assertEquals(1, jugador.obtenerId());
        assertEquals("Daniel", jugador.obtenerNombre());
    }

    @Test
    void testSetters() {
        jugador.establecerId(2);
        jugador.establecerNombre("Alejandro");

        assertEquals(2, jugador.obtenerId());
        assertEquals("Alejandro", jugador.obtenerNombre());
    }

    @Test
    void testConstructorSoloNombre() {
        JugadorDTO nuevoJugador = new JugadorDTO("Carlos");
        assertEquals("Carlos", nuevoJugador.obtenerNombre());
        assertEquals(0, nuevoJugador.obtenerId()); // Valor por defecto
    }
}