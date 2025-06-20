package com.mycompany.tpi.controlador;

import com.mycompany.tpi.modelo.Equipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public List<Equipo> obtenerTodos() {
        List<Equipo> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM equipos ORDER BY posicion_liga_anterior ASC")) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int posicion = rs.getInt("posicion_liga_anterior");
                lista.add(new Equipo(nombre, posicion));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
        }
        return lista;
    }

    public void guardar(Equipo equipo) {
        String sql = "INSERT INTO equipos (nombre, posicion_liga_anterior) VALUES (?, ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, equipo.getNombre());
            ps.setInt(2, equipo.getPosicionLigaAnterior());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar equipo: " + e.getMessage());
        }
    }

    public void eliminarTodos() {
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM equipos");
        } catch (SQLException e) {
            System.out.println("Error al eliminar equipos: " + e.getMessage());
        }
    }
}
