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
 * Representa la entidad AsesorCrediticio. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class AsesorCrediticio {

    // --- Atributos que coinciden con la tabla ASESORCREDITICIO ---
    private long idAsesorCrediticio;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;
    private long idBanco; // Clave Foránea

    // --- Constructor ---
    public AsesorCrediticio(long idAsesorCrediticio, String nombreCompleto, String dni, String telefono, String correo, long idBanco) {
        this.idAsesorCrediticio = idAsesorCrediticio;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.idBanco = idBanco;
    }

    // --- Getters y Setters ---
    public long getIdAsesorCrediticio() { return idAsesorCrediticio; }
    public void setIdAsesorCrediticio(long idAsesorCrediticio) { this.idAsesorCrediticio = idAsesorCrediticio; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public long getIdBanco() { return idBanco; }
    public void setIdBanco(long idBanco) { this.idBanco = idBanco; }

    @Override
    public String toString() {
        return "AsesorCrediticio{" + "idAsesorCrediticio=" + idAsesorCrediticio + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, AsesorCrediticio asesor) {
        String sql = "INSERT INTO AsesorCrediticio (IDASESORCREDITICIO, NOMBRECOMPLETO, DNI, TELEFONO, CORREO, IDBANCO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, asesor.getIdAsesorCrediticio());
            pstmt.setString(2, asesor.getNombreCompleto());
            pstmt.setString(3, asesor.getDni());
            pstmt.setString(4, asesor.getTelefono());
            pstmt.setString(5, asesor.getCorreo());
            pstmt.setLong(6, asesor.getIdBanco());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo asesor crediticio insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el asesor crediticio: " + e.getMessage());
        }
    }

    public static AsesorCrediticio buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM AsesorCrediticio WHERE IDASESORCREDITICIO = ?";
        AsesorCrediticio asesor = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    asesor = new AsesorCrediticio(
                        rs.getLong("IDASESORCREDITICIO"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("DNI"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getLong("IDBANCO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el asesor crediticio: " + e.getMessage());
        }
        return asesor;
    }

    public static List<AsesorCrediticio> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM AsesorCrediticio ORDER BY IDASESORCREDITICIO";
        List<AsesorCrediticio> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new AsesorCrediticio(
                    rs.getLong("IDASESORCREDITICIO"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("DNI"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getLong("IDBANCO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los asesores crediticios: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM AsesorCrediticio WHERE IDASESORCREDITICIO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Asesor crediticio con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún asesor crediticio con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el asesor crediticio: " + e.getMessage());
        }
    }
}
