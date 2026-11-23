package dao;

import conexion.ConexionH2;
import dto.JugadorDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAOJDBC implements JugadorDAO {

    @Override
    public boolean guardar(JugadorDTO jugador) {
        String sql = "INSERT INTO jugadores (nombre) VALUES (?)";
        try (Connection conn = ConexionH2.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, jugador.obtenerNombre());
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    jugador.establecerId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public JugadorDTO buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM jugadores WHERE nombre = ?";
        try (Connection conn = ConexionH2.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JugadorDTO(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<JugadorDTO> listarTodos() {
        List<JugadorDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM jugadores";
        try (Connection conn = ConexionH2.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new JugadorDTO(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}