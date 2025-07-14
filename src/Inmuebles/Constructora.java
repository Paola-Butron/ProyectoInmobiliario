/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inmuebles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Constructora {

    // --- Atributos que coinciden con la tabla CONSTRUCTORA ---
    private long idConstructora;
    private String nombreConstructora;
    private String ruc;
    private String gerenteGeneral;
    private String direccionFiscal;
    private String telefono;
    private String email;

    // --- Constructor ---
    // Único constructor que exige todos los datos para crear un objeto válido.
    public Constructora(long idConstructora, String nombreConstructora, String ruc, String gerenteGeneral, String direccionFiscal, String telefono, String email) {
        this.idConstructora = idConstructora;
        this.nombreConstructora = nombreConstructora;
        this.ruc = ruc;
        this.gerenteGeneral = gerenteGeneral;
        this.direccionFiscal = direccionFiscal;
        this.telefono = telefono;
        this.email = email;
    }

    // --- Getters y Setters ---
    public long getIdConstructora() { return idConstructora; }
    public void setIdConstructora(long idConstructora) { this.idConstructora = idConstructora; }
    public String getNombreConstructora() { return nombreConstructora; }
    public void setNombreConstructora(String nombreConstructora) { this.nombreConstructora = nombreConstructora; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getGerenteGeneral() { return gerenteGeneral; }
    public void setGerenteGeneral(String gerenteGeneral) { this.gerenteGeneral = gerenteGeneral; }
    public String getDireccionFiscal() { return direccionFiscal; }
    public void setDireccionFiscal(String direccionFiscal) { this.direccionFiscal = direccionFiscal; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Constructora{" + "idConstructora=" + idConstructora + ", nombre='" + nombreConstructora + "'}";
    }


    public static void insertar(Connection conn, Constructora constructora) {
        String sql = "INSERT INTO Constructora (IDCONSTRUCTORA, NOMBRECONSTRUCTORA, RUC, GERENTEGENERAL, DIRECCIONFISCAL, TELEFONO, EMAIL) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, constructora.getIdConstructora());
            pstmt.setString(2, constructora.getNombreConstructora());
            pstmt.setString(3, constructora.getRuc());
            pstmt.setString(4, constructora.getGerenteGeneral());
            pstmt.setString(5, constructora.getDireccionFiscal());
            pstmt.setString(6, constructora.getTelefono());
            pstmt.setString(7, constructora.getEmail());
            pstmt.executeUpdate();
            System.out.println("✅ Nueva constructora insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar la constructora: " + e.getMessage());
        }
    }

    public static Constructora buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Constructora WHERE IDCONSTRUCTORA = ?";
        Constructora constructora = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    constructora = new Constructora(
                        rs.getLong("IDCONSTRUCTORA"),
                        rs.getString("NOMBRECONSTRUCTORA"),
                        rs.getString("RUC"),
                        rs.getString("GERENTEGENERAL"),
                        rs.getString("DIRECCIONFISCAL"),
                        rs.getString("TELEFONO"),
                        rs.getString("EMAIL")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar la constructora: " + e.getMessage());
        }
        return constructora;
    }

    public static List<Constructora> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Constructora ORDER BY IDCONSTRUCTORA";
        List<Constructora> listaConstructoras = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaConstructoras.add(new Constructora(
                    rs.getLong("IDCONSTRUCTORA"),
                    rs.getString("NOMBRECONSTRUCTORA"),
                    rs.getString("RUC"),
                    rs.getString("GERENTEGENERAL"),
                    rs.getString("DIRECCIONFISCAL"),
                    rs.getString("TELEFONO"),
                    rs.getString("EMAIL")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todas las constructoras: " + e.getMessage());
        }
        return listaConstructoras;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Constructora WHERE IDCONSTRUCTORA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Constructora con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ninguna constructora con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar la constructora: " + e.getMessage());
        }
    }
    
    public static void actualizar(Connection conn, Constructora c) {
    String sql = "UPDATE Constructora SET nombreConstructora = ?, ruc = ?, gerenteGeneral = ?, direccionFiscal = ?, telefono = ?, email = ? WHERE IDCONSTRUCTORA = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, c.getNombreConstructora());
        pstmt.setString(2, c.getRuc());
        pstmt.setString(3, c.getGerenteGeneral());
        pstmt.setString(4, c.getDireccionFiscal());
        pstmt.setString(5, c.getTelefono());
        pstmt.setString(6, c.getEmail());
        pstmt.setLong(7, c.getIdConstructora());

        int filasAfectadas = pstmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("✅ Constructora con ID " + c.getIdConstructora() + " actualizada correctamente.");
        } else {
            System.out.println("ℹ️ No se encontró ninguna constructora con ID " + c.getIdConstructora() + ".");
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar la constructora: " + e.getMessage());
    }
    }
}
