package dao;

import dto.JugadorDTO;
import java.util.List;

public interface JugadorDAO {
    boolean guardar (JugadorDTO jugador);
    JugadorDTO buscarPorNombre(String nombre);
    List<JugadorDTO>listarTodos();
}
