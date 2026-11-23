package logica;

import dao.*;
import dto.*;

import java.util.List;

public class GestorJuego {
    private JugadorDAO jugadorDAO;
    private PalabraDAO palabraDAO;
    private PartidaDAO partidaDAO;

    public GestorJuego() {
        this.jugadorDAO = new JugadorDAOJDBC();
        this.palabraDAO = new PalabraDAOJDBC();
        this.partidaDAO = new PartidaDAOJDBC();
    }

    public JugadorDTO registrarJugador(String nombre) {
        JugadorDTO existente = jugadorDAO.buscarPorNombre(nombre);
        if (existente != null) {
            return existente;
        }
        JugadorDTO nuevo = new JugadorDTO(nombre);
        jugadorDAO.guardar(nuevo);
        return nuevo;
    }

    public PalabraDTO seleccionarPalabra(String categoria, String dificultad) {
        return palabraDAO.obtenerAlAzar(categoria, dificultad);
    }

    public PartidaDTO finalizarPartida(JuegoLogica juego) {
        String resultado = juego.esVictoria() ? "VICTORIA" : "DERROTA";
        int puntaje = juego.calcularPuntaje();

        PartidaDTO partida = new PartidaDTO(
                juego.getJugadorActual().obtenerId(),
                juego.getPalabraSecreta().obtenerPalabra(),
                juego.obtenerErrores(),
                resultado,
                puntaje
        );
        partidaDAO.guardar(partida);
        return partida;
    }

    public List<RankingDTO> verRanking() {
        return partidaDAO.obtenerRanking();
    }

    public List<PartidaDTO> verHistorial(int jugadorId) {
        return partidaDAO.obtenerHistorialPorJugador(jugadorId);
    }

    public PalabraDAO getPalabraDAO() {
        return palabraDAO;
    }
}