/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Finanzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Tasador. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Tasador {

    // --- Atributos que coinciden con la tabla TASADOR ---
    private long idTasador;
    private String nombreCompleto;
    private String registro;
    private String telefono;
    private String correo;
    private long idBanco; // Clave Foránea

    // --- Constructor ---
    public Tasador(long idTasador, String nombreCompleto, String registro, String telefono, String correo, long idBanco) {
        this.idTasador = idTasador;
        this.nombreCompleto = nombreCompleto;
        this.registro = registro;
        this.telefono = telefono;
        this.correo = correo;
        this.idBanco = idBanco;
    }

    // --- Getters y Setters ---
    public long getIdTasador() { return idTasador; }
    public void setIdTasador(long idTasador) { this.idTasador = idTasador; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public long getIdBanco() { return idBanco; }
    public void setIdBanco(long idBanco) { this.idBanco = idBanco; }

    @Override
    public String toString() {
        return "Tasador{" + "idTasador=" + idTasador + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Tasador tasador) {
        String sql = "INSERT INTO Tasador (IDTASADOR, NOMBRECOMPLETO, REGISTRO, TELEFONO, CORREO, IDBANCO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, tasador.getIdTasador());
            pstmt.setString(2, tasador.getNombreCompleto());
            pstmt.setString(3, tasador.getRegistro());
            pstmt.setString(4, tasador.getTelefono());
            pstmt.setString(5, tasador.getCorreo());
            pstmt.setLong(6, tasador.getIdBanco());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo tasador insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el tasador: " + e.getMessage());
        }
    }

    public static Tasador buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Tasador WHERE IDTASADOR = ?";
        Tasador tasador = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tasador = new Tasador(
                        rs.getLong("IDTASADOR"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("REGISTRO"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getLong("IDBANCO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el tasador: " + e.getMessage());
        }
        return tasador;
    }

    public static List<Tasador> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Tasador ORDER BY IDTASADOR";
        List<Tasador> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Tasador(
                    rs.getLong("IDTASADOR"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("REGISTRO"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getLong("IDBANCO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los tasadores: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Tasador WHERE IDTASADOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Tasador con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún tasador con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el tasador: " + e.getMessage());
        }
    }
}
