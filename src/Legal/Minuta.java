/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Minuta. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Minuta {

    // --- Atributos que coinciden con la tabla MINUTA ---
    private long idDocumento; // Clave Primaria y Foránea
    private String notariaAsociada;
    private double montoTotal;
    private String formaDePago;
    private String constanciaDeAcabados;

    // --- Constructor ---
    public Minuta(long idDocumento, String notariaAsociada, double montoTotal, String formaDePago, String constanciaDeAcabados) {
        this.idDocumento = idDocumento;
        this.notariaAsociada = notariaAsociada;
        this.montoTotal = montoTotal;
        this.formaDePago = formaDePago;
        this.constanciaDeAcabados = constanciaDeAcabados;
    }

    // --- Getters y Setters ---
    public long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(long idDocumento) { this.idDocumento = idDocumento; }
    public String getNotariaAsociada() { return notariaAsociada; }
    public void setNotariaAsociada(String notariaAsociada) { this.notariaAsociada = notariaAsociada; }
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
    public String getFormaDePago() { return formaDePago; }
    public void setFormaDePago(String formaDePago) { this.formaDePago = formaDePago; }
    public String getConstanciaDeAcabados() { return constanciaDeAcabados; }
    public void setConstanciaDeAcabados(String constanciaDeAcabados) { this.constanciaDeAcabados = constanciaDeAcabados; }

    @Override
    public String toString() {
        return "Minuta{" + "idDocumento=" + idDocumento + ", montoTotal=" + montoTotal + "}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Minuta minuta) {
        String sql = "INSERT INTO Minuta (IDDOCUMENTO, NOTARIAASOCIADA, MONTOTOTAL, FORMADEPAGO, CONSTANCIADEACABADOS) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, minuta.getIdDocumento());
            pstmt.setString(2, minuta.getNotariaAsociada());
            pstmt.setDouble(3, minuta.getMontoTotal());
            pstmt.setString(4, minuta.getFormaDePago());
            pstmt.setString(5, minuta.getConstanciaDeAcabados());
            pstmt.executeUpdate();
            System.out.println("✅ Nueva minuta insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar la minuta: " + e.getMessage());
        }
    }

    public static Minuta buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Minuta WHERE IDDOCUMENTO = ?";
        Minuta minuta = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    minuta = new Minuta(
                        rs.getLong("IDDOCUMENTO"),
                        rs.getString("NOTARIAASOCIADA"),
                        rs.getDouble("MONTOTOTAL"),
                        rs.getString("FORMADEPAGO"),
                        rs.getString("CONSTANCIADEACABADOS")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar la minuta: " + e.getMessage());
        }
        return minuta;
    }

    public static List<Minuta> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Minuta ORDER BY IDDOCUMENTO";
        List<Minuta> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Minuta(
                    rs.getLong("IDDOCUMENTO"),
                    rs.getString("NOTARIAASOCIADA"),
                    rs.getDouble("MONTOTOTAL"),
                    rs.getString("FORMADEPAGO"),
                    rs.getString("CONSTANCIADEACABADOS")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todas las minutas: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Minuta WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Minuta con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ninguna minuta con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar la minuta: " + e.getMessage());
        }
    }
}






