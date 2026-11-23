
import dao.*;
import conexion.ConexionH2;
import dto.PalabraDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PalabraDAOJDBCTest {

    private PalabraDAO palabraDAO;

    @BeforeEach
    void setUp() {
        ConexionH2.inicializarBaseDeDatos();
        palabraDAO = new PalabraDAOJDBC();
    }

    @Test
    void testAgregarYObtenerAlAzarPorCategoria() {
        String catUnica = "CatTest_" + System.currentTimeMillis();
        PalabraDTO p = new PalabraDTO("PRUEBA", catUnica, "Fácil");

        assertTrue(palabraDAO.agregar(p));
        PalabraDTO obtenida = palabraDAO.obtenerAlAzar(catUnica, "Fácil");

        assertNotNull(obtenida);
        assertEquals("PRUEBA", obtenida.obtenerPalabra());
        assertEquals(catUnica, obtenida.obtenerCategoria());
    }
}