package dao;

import conexion.ConexionH2;
import dto.PartidaDTO;
import dto.RankingDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAOJDBC implements PartidaDAO {

    @Override
    public boolean guardar(PartidaDTO partida) {
        String sql = "INSERT INTO partidas (jugador_id, palabra, errores, resultado, puntaje) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionH2.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, partida.obtenerJugadorId());
            stmt.setString(2, partida.obtenerPalabra());
            stmt.setInt(3, partida.obtenerErrores());
            stmt.setString(4, partida.obtenerResultado());
            stmt.setInt(5, partida.obtenerPuntaje());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<PartidaDTO> obtenerHistorialPorJugador(int jugadorId) {
        List<PartidaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM partidas WHERE jugador_id = ? ORDER BY fecha DESC";
        try (Connection conn = ConexionH2.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jugadorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PartidaDTO p = new PartidaDTO(
                        rs.getInt("jugador_id"),
                        rs.getString("palabra"),
                        rs.getInt("errores"),
                        rs.getString("resultado"),
                        rs.getInt("puntaje")
                );
                p.establecerId(rs.getInt("id"));
                p.establecerFecha(rs.getTimestamp("fecha").toLocalDateTime());
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<RankingDTO> obtenerRanking() {
        List<RankingDTO> ranking = new ArrayList<>();
        String sql = "SELECT j.nombre, p.puntaje, p.errores, p.fecha "
                + "FROM partidas p JOIN jugadores j ON p.jugador_id = j.id "
                + "ORDER BY p.puntaje DESC, p.fecha ASC LIMIT 10";

        try (Connection conn = ConexionH2.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int pos = 1;
            while (rs.next()) {
                ranking.add(new RankingDTO(
                        pos++,
                        rs.getString("nombre"),
                        rs.getInt("puntaje"),
                        rs.getInt("errores"),
                        rs.getTimestamp("fecha").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }
}