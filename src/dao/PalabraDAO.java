package dao;

import dto.PalabraDTO;
import java.util.List;

public interface PalabraDAO {
    PalabraDTO obtenerAlAzar(String categoria, String dificultad);
    boolean agregar(PalabraDTO palabra);
    List<PalabraDTO> listar();

}
