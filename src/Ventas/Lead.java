/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Lead. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Lead {

    // --- Atributos que coinciden con la tabla LEAD ---
    private long idLead;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String estadoLead;
    private long idCanalEntrada; // Clave Foránea

    // --- Constructor ---
    public Lead(long idLead, String nombreCompleto, String email, String telefono, String estadoLead, long idCanalEntrada) {
        this.idLead = idLead;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.estadoLead = estadoLead;
        this.idCanalEntrada = idCanalEntrada;
    }

    // --- Getters y Setters ---
    public long getIdLead() { return idLead; }
    public void setIdLead(long idLead) { this.idLead = idLead; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEstadoLead() { return estadoLead; }
    public void setEstadoLead(String estadoLead) { this.estadoLead = estadoLead; }
    public long getIdCanalEntrada() { return idCanalEntrada; }
    public void setIdCanalEntrada(long idCanalEntrada) { this.idCanalEntrada = idCanalEntrada; }

    @Override
    public String toString() {
        return "Lead{" + "idLead=" + idLead + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Lead lead) {
        String sql = "INSERT INTO Lead (IDLEAD, NOMBRECOMPLETO, EMAIL, TELEFONO, ESTADOLEAD, IDCANALENTRADA) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, lead.getIdLead());
            pstmt.setString(2, lead.getNombreCompleto());
            pstmt.setString(3, lead.getEmail());
            pstmt.setString(4, lead.getTelefono());
            pstmt.setString(5, lead.getEstadoLead());
            pstmt.setLong(6, lead.getIdCanalEntrada());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo lead insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el lead: " + e.getMessage());
        }
    }

    public static Lead buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Lead WHERE IDLEAD = ?";
        Lead lead = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    lead = new Lead(
                        rs.getLong("IDLEAD"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONO"),
                        rs.getString("ESTADOLEAD"),
                        rs.getLong("IDCANALENTRADA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el lead: " + e.getMessage());
        }
        return lead;
    }

    public static List<Lead> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Lead ORDER BY IDLEAD";
        List<Lead> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Lead(
                    rs.getLong("IDLEAD"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO"),
                    rs.getString("ESTADOLEAD"),
                    rs.getLong("IDCANALENTRADA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los leads: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Lead WHERE IDLEAD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Lead con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún lead con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el lead: " + e.getMessage());
        }
    }
}


