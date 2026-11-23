import logica.*;
import dto.*;
import dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JuegoLogicaTest {

    private JugadorDTO jugador;
    private PalabraDTO palabra;
    private JuegoLogica juego;

    @BeforeEach
    void setUp() {
        jugador = new JugadorDTO(1, "Daniel");
        palabra = new PalabraDTO(1, "JAVA", "Programación", "Fácil");
        juego = new JuegoLogica(jugador, palabra);
    }

    @Test
    void testPalabraEnmascaradaInicial() {
        assertEquals("_ _ _ _", juego.obtenerPalabraEnmascarada());
    }

    @Test
    void testIntentarLetraCorrectaEInsensibleAMayusculas() {
        assertTrue(juego.intentarLetra('j'));
        assertEquals("J _ _ _", juego.obtenerPalabraEnmascarada());
        assertEquals(0, juego.obtenerErrores());
    }

    @Test
    void testIntentarLetraIncorrectaIncrementaErrores() {
        assertFalse(juego.intentarLetra('Z'));
        assertEquals(1, juego.obtenerErrores());
    }

    @Test
    void testLetraRepetidaNoSumaErrorAdicional() {
        juego.intentarLetra('Z');
        assertFalse(juego.intentarLetra('Z'));
        assertEquals(1, juego.obtenerErrores());
    }

    @Test
    void testVictoriaYCalculoPuntaje_RN02() {
        // Palabra: JAVA (4 letras), 1 error ('X') -> 100 + (6 - 1)*20 + 4*10 = 240
        juego.intentarLetra('X');
        juego.intentarLetra('J');
        juego.intentarLetra('A');
        juego.intentarLetra('V');

        assertTrue(juego.estaTerminada());
        assertTrue(juego.esVictoria());
        assertEquals(240, juego.calcularPuntaje());
    }

    @Test
    void testDerrotaAlLlegarA6ErroresYPuntajeCero_RN01_RN03() {
        char[] fallos = {'B', 'C', 'D', 'E', 'F', 'G'};
        for (char c : fallos) {
            juego.intentarLetra(c);
        }

        assertTrue(juego.estaTerminada());
        assertFalse(juego.esVictoria());
        assertEquals(6, juego.obtenerErrores());
        assertEquals(0, juego.calcularPuntaje());
    }
}