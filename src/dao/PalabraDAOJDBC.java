package dao;

import conexion.ConexionH2;
import dto.PalabraDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalabraDAOJDBC implements PalabraDAO {

    @Override
    public PalabraDTO obtenerAlAzar(String categoria, String dificultad) {
        boolean filtrarCategoria = categoria != null && !categoria.equalsIgnoreCase("Todas");
        String sql = filtrarCategoria
                ? "SELECT * FROM palabras WHERE categoria = ? ORDER BY RAND() LIMIT 1"
                : "SELECT * FROM palabras ORDER BY RAND() LIMIT 1";

        try (Connection conn = ConexionH2.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (filtrarCategoria) {
                stmt.setString(1, categoria);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PalabraDTO(
                        rs.getInt("id"),
                        rs.getString("palabra"),
                        rs.getString("categoria"),
                        rs.getString("dificultad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregar(PalabraDTO palabra) {
        String sql = "INSERT INTO palabras (palabra, categoria, dificultad) VALUES (?, ?, ?)";
        try (Connection conn = ConexionH2.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, palabra.obtenerPalabra().toUpperCase());
            stmt.setString(2, palabra.obtenerCategoria());
            stmt.setString(3, palabra.obtenerDificultad());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<PalabraDTO> listar() {
        List<PalabraDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM palabras";
        try (Connection conn = ConexionH2.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new PalabraDTO(
                        rs.getInt("id"),
                        rs.getString("palabra"),
                        rs.getString("categoria"),
                        rs.getString("dificultad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}