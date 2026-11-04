package dao;

import dto.PartidaDTO;
import dto.RankingDTO;
import java.util.List;
public interface PartidaDAO {
    boolean guardar(PartidaDTO partida);
    List<PartidaDTO> obtenerHistorialPorJugador(int jugadorId);
    List<RankingDTO> obtenerRanking();
}
