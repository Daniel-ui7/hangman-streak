
import dto.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PalabraDTOTest {

    @Test
    void testCreacionYModificacion() {
        PalabraDTO palabra = new PalabraDTO(10, "AHORCADO", "General", "Fácil");

        assertEquals(10, palabra.obtenerId());
        assertEquals("AHORCADO", palabra.obtenerPalabra());
        assertEquals("General", palabra.obtenerCategoria());
        assertEquals("Fácil", palabra.obtenerDificultad());

        palabra.establecerPalabra("JAVA");
        palabra.setDificultad("Media");

        assertEquals("JAVA", palabra.obtenerPalabra());
        assertEquals("Media", palabra.obtenerDificultad());
    }
    @Test
    void testConstructorVacioYModificacionesIndividuales() {
        PalabraDTO palabra = new PalabraDTO();
        palabra.establecerId(5);
        palabra.establecerPalabra("DESARROLLO");
        palabra.establecerCategoria("Sistemas");
        palabra.setDificultad("Difícil");

        assertEquals(5, palabra.obtenerId());
        assertEquals("DESARROLLO", palabra.obtenerPalabra());
        assertEquals("Sistemas", palabra.obtenerCategoria());
        assertEquals("Difícil", palabra.obtenerDificultad());
    }

    @Test
    void testConstructorSinId() {
        PalabraDTO palabra = new PalabraDTO("PROGRAMACION", "Tecnología", "Media");

        assertEquals(0, palabra.obtenerId(), "El ID no asignado debe ser 0");
        assertEquals("PROGRAMACION", palabra.obtenerPalabra());
        assertEquals("Tecnología", palabra.obtenerCategoria());
        assertEquals("Media", palabra.obtenerDificultad());
    }

    @Test
    void testLongitudYValoresDePalabra() {
        PalabraDTO palabra = new PalabraDTO("JAVA", "General", "Fácil");

        assertEquals(4, palabra.obtenerPalabra().length());
        assertTrue(palabra.obtenerPalabra().equals(palabra.obtenerPalabra().toUpperCase()), "La palabra en el DTO debe mantenerse en mayúsculas");
    }
}