package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Lead {

    private int idLead;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String estadoLead;
    private int idCanalEntrada;

    public Lead(int idLead, String nombreCompleto, String email, String telefono, String estadoLead, int idCanalEntrada) {
        this.idLead = idLead;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.estadoLead = estadoLead;
        this.idCanalEntrada = idCanalEntrada;
    }

    public int getIdLead() { return idLead; }
    public void setIdLead(int idLead) { this.idLead = idLead; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEstadoLead() { return estadoLead; }
    public void setEstadoLead(String estadoLead) { this.estadoLead = estadoLead; }
    public int getIdCanalEntrada() { return idCanalEntrada; }
    public void setIdCanalEntrada(int idCanalEntrada) { this.idCanalEntrada = idCanalEntrada; }

    @Override
    public String toString() {
        return "Lead{" + "idLead=" + idLead + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    public static void insertar(Connection conn, Lead lead) {
        String sql = "INSERT INTO Lead (IDLEAD, NOMBRECOMPLETO, EMAIL, TELEFONO, ESTADOLEAD, IDCANALENTRADA) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, lead.getIdLead());
            pstmt.setString(2, lead.getNombreCompleto());
            pstmt.setString(3, lead.getEmail());
            pstmt.setString(4, lead.getTelefono());
            pstmt.setString(5, lead.getEstadoLead());
            pstmt.setInt(6, lead.getIdCanalEntrada());
            pstmt.executeUpdate();
            System.out.println("Nuevo lead insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el lead: " + e.getMessage());
        }
    }

    public static Lead buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Lead WHERE IDLEAD = ?";
        Lead lead = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    lead = new Lead(
                        rs.getInt("IDLEAD"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONO"),
                        rs.getString("ESTADOLEAD"),
                        rs.getInt("IDCANALENTRADA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el lead: " + e.getMessage());
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
                    rs.getInt("IDLEAD"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO"),
                    rs.getString("ESTADOLEAD"),
                    rs.getInt("IDCANALENTRADA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los leads: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Lead WHERE IDLEAD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Lead con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún lead con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el lead: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Lead lead) {
        String sql = "UPDATE Lead SET NOMBRECOMPLETO = ?, EMAIL = ?, TELEFONO = ?, ESTADOLEAD = ?, IDCANALENTRADA = ? WHERE IDLEAD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lead.getNombreCompleto());
            pstmt.setString(2, lead.getEmail());
            pstmt.setString(3, lead.getTelefono());
            pstmt.setString(4, lead.getEstadoLead());
            pstmt.setInt(5, lead.getIdCanalEntrada());
            pstmt.setInt(6, lead.getIdLead());

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Lead actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún lead con ese ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el lead: " + e.getMessage());
        }
    }
}
