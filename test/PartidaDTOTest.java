import dto.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PartidaDTOTest {

    @Test
    void testCreacionPartidaDTO() {
        PartidaDTO partida = new PartidaDTO(1, "JAVA", 2, "VICTORIA", 220);

        assertEquals(1, partida.obtenerJugadorId());
        assertEquals("JAVA", partida.obtenerPalabra());
        assertEquals(2, partida.obtenerErrores());
        assertEquals("VICTORIA", partida.obtenerResultado());
        assertEquals(220, partida.obtenerPuntaje());
        assertNotNull(partida.obtenerFecha(), "La fecha debe asignarse automáticamente al instanciar");
    }
    @Test
    void testConstructorVacioYSettersCompletos() {
        PartidaDTO partida = new PartidaDTO();
        LocalDateTime fechaPrueba = LocalDateTime.now();

        partida.establecerId(10);
        partida.establecerJugadorId(2);
        partida.establecerPalabra("COMPUTADORA");
        partida.establecerErrores(4);
        partida.establecerResultado("DERROTA");
        partida.establecerPuntaje(0);
        partida.establecerFecha(fechaPrueba);

        assertEquals(10, partida.obtenerId());
        assertEquals(2, partida.obtenerJugadorId());
        assertEquals("COMPUTADORA", partida.obtenerPalabra());
        assertEquals(4, partida.obtenerErrores());
        assertEquals("DERROTA", partida.obtenerResultado());
        assertEquals(0, partida.obtenerPuntaje());
        assertEquals(fechaPrueba, partida.obtenerFecha());
    }

    @Test
    void testPuntajeCeroEnDerrota() {
        PartidaDTO partida = new PartidaDTO(1, "ALGORITMO", 6, "DERROTA", 0);

        assertEquals("DERROTA", partida.obtenerResultado());
        assertEquals(0, partida.obtenerPuntaje());
        assertEquals(6, partida.obtenerErrores());
    }

    @Test
    void testModificacionDeFechaPersonalizada() {
        PartidaDTO partida = new PartidaDTO(1, "JAVA", 0, "VICTORIA", 300);
        LocalDateTime fechaAnterior = LocalDateTime.now().minusDays(1);

        partida.establecerFecha(fechaAnterior);
        assertEquals(fechaAnterior, partida.obtenerFecha());
        assertTrue(partida.obtenerFecha().isBefore(LocalDateTime.now()));
    }
}