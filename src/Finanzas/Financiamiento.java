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
import java.util.Date;
import java.util.List;

/**
 * Representa la entidad Financiamiento. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Financiamiento {

    // --- Atributos que coinciden con la tabla FINANCIAMIENTO ---
    private long idFinanciamiento;
    private String entidadFinanciera;
    private double cuotaMensual;
    private Date fechaAprobacion;
    private long idAsesorCrediticio;
    private long idCliente;

    // --- Constructor ---
    public Financiamiento(long idFinanciamiento, String entidadFinanciera, double cuotaMensual, Date fechaAprobacion, long idAsesorCrediticio, long idCliente) {
        this.idFinanciamiento = idFinanciamiento;
        this.entidadFinanciera = entidadFinanciera;
        this.cuotaMensual = cuotaMensual;
        this.fechaAprobacion = fechaAprobacion;
        this.idAsesorCrediticio = idAsesorCrediticio;
        this.idCliente = idCliente;
    }

    // --- Getters y Setters ---
    public long getIdFinanciamiento() { return idFinanciamiento; }
    public void setIdFinanciamiento(long idFinanciamiento) { this.idFinanciamiento = idFinanciamiento; }
    public String getEntidadFinanciera() { return entidadFinanciera; }
    public void setEntidadFinanciera(String entidadFinanciera) { this.entidadFinanciera = entidadFinanciera; }
    public double getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(double cuotaMensual) { this.cuotaMensual = cuotaMensual; }
    public Date getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(Date fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }
    public long getIdAsesorCrediticio() { return idAsesorCrediticio; }
    public void setIdAsesorCrediticio(long idAsesorCrediticio) { this.idAsesorCrediticio = idAsesorCrediticio; }
    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Financiamiento{" + "idFinanciamiento=" + idFinanciamiento + ", entidad='" + entidadFinanciera + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Financiamiento financiamiento) {
        String sql = "INSERT INTO Financiamiento (IDFINANCIAMIENTO, ENTIDADFINANCIERA, CUOTAMENSUAL, FECHAAPROBACION, IDASESORCREDITICIO, IDCLIENTE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, financiamiento.getIdFinanciamiento());
            pstmt.setString(2, financiamiento.getEntidadFinanciera());
            pstmt.setDouble(3, financiamiento.getCuotaMensual());
            pstmt.setDate(4, new java.sql.Date(financiamiento.getFechaAprobacion().getTime()));
            pstmt.setLong(5, financiamiento.getIdAsesorCrediticio());
            pstmt.setLong(6, financiamiento.getIdCliente());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo financiamiento insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el financiamiento: " + e.getMessage());
        }
    }

    public static Financiamiento buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Financiamiento WHERE IDFINANCIAMIENTO = ?";
        Financiamiento financiamiento = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    financiamiento = new Financiamiento(
                        rs.getLong("IDFINANCIAMIENTO"),
                        rs.getString("ENTIDADFINANCIERA"),
                        rs.getDouble("CUOTAMENSUAL"),
                        rs.getDate("FECHAAPROBACION"),
                        rs.getLong("IDASESORCREDITICIO"),
                        rs.getLong("IDCLIENTE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el financiamiento: " + e.getMessage());
        }
        return financiamiento;
    }

    public static List<Financiamiento> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Financiamiento ORDER BY IDFINANCIAMIENTO";
        List<Financiamiento> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Financiamiento(
                    rs.getLong("IDFINANCIAMIENTO"),
                    rs.getString("ENTIDADFINANCIERA"),
                    rs.getDouble("CUOTAMENSUAL"),
                    rs.getDate("FECHAAPROBACION"),
                    rs.getLong("IDASESORCREDITICIO"),
                    rs.getLong("IDCLIENTE")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los financiamientos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Financiamiento WHERE IDFINANCIAMIENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Financiamiento con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún financiamiento con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el financiamiento: " + e.getMessage());
        }
    }
}






