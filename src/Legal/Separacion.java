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
import java.util.Date;
import java.util.List;

/**
 * Representa la entidad Separacion. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Separacion {

    // --- Atributos que coinciden con la tabla SEPARACION ---
    private long idDocumento; // Clave Primaria y Foránea
    private double montoSeparacion;
    private Date fechaPago;
    private String formaDePago;
    private String constanciaDeAcabados;

    // --- Constructor ---
    public Separacion(long idDocumento, double montoSeparacion, Date fechaPago, String formaDePago, String constanciaDeAcabados) {
        this.idDocumento = idDocumento;
        this.montoSeparacion = montoSeparacion;
        this.fechaPago = fechaPago;
        this.formaDePago = formaDePago;
        this.constanciaDeAcabados = constanciaDeAcabados;
    }

    // --- Getters y Setters ---
    public long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(long idDocumento) { this.idDocumento = idDocumento; }
    public double getMontoSeparacion() { return montoSeparacion; }
    public void setMontoSeparacion(double montoSeparacion) { this.montoSeparacion = montoSeparacion; }
    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }
    public String getFormaDePago() { return formaDePago; }
    public void setFormaDePago(String formaDePago) { this.formaDePago = formaDePago; }
    public String getConstanciaDeAcabados() { return constanciaDeAcabados; }
    public void setConstanciaDeAcabados(String constanciaDeAcabados) { this.constanciaDeAcabados = constanciaDeAcabados; }

    @Override
    public String toString() {
        return "Separacion{" + "idDocumento=" + idDocumento + ", montoSeparacion=" + montoSeparacion + "}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Separacion separacion) {
        String sql = "INSERT INTO Separacion (IDDOCUMENTO, MONTOSEPARACION, FECHAPAGO, FORMADEPAGO, CONSTANCIADEACABADOS) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, separacion.getIdDocumento());
            pstmt.setDouble(2, separacion.getMontoSeparacion());
            pstmt.setDate(3, new java.sql.Date(separacion.getFechaPago().getTime()));
            pstmt.setString(4, separacion.getFormaDePago());
            pstmt.setString(5, separacion.getConstanciaDeAcabados());
            pstmt.executeUpdate();
            System.out.println("✅ Nueva separación insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar la separación: " + e.getMessage());
        }
    }

    public static Separacion buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Separacion WHERE IDDOCUMENTO = ?";
        Separacion separacion = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    separacion = new Separacion(
                        rs.getLong("IDDOCUMENTO"),
                        rs.getDouble("MONTOSEPARACION"),
                        rs.getDate("FECHAPAGO"),
                        rs.getString("FORMADEPAGO"),
                        rs.getString("CONSTANCIADEACABADOS")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar la separación: " + e.getMessage());
        }
        return separacion;
    }

    public static List<Separacion> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Separacion ORDER BY IDDOCUMENTO";
        List<Separacion> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Separacion(
                    rs.getLong("IDDOCUMENTO"),
                    rs.getDouble("MONTOSEPARACION"),
                    rs.getDate("FECHAPAGO"),
                    rs.getString("FORMADEPAGO"),
                    rs.getString("CONSTANCIADEACABADOS")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todas las separaciones: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Separacion WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Separación con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ninguna separación con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar la separación: " + e.getMessage());
        }
    }
}
