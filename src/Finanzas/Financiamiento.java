package Finanzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Financiamiento {

    private int idFinanciamiento;
    private String entidadFinanciera;
    private double cuotaMensual;
    private Date fechaAprobacion;
    private int idAsesorCrediticio;
    private int idCliente;

    public Financiamiento(int idFinanciamiento, String entidadFinanciera, double cuotaMensual, Date fechaAprobacion, int idAsesorCrediticio, int idCliente) {
        this.idFinanciamiento = idFinanciamiento;
        this.entidadFinanciera = entidadFinanciera;
        this.cuotaMensual = cuotaMensual;
        this.fechaAprobacion = fechaAprobacion;
        this.idAsesorCrediticio = idAsesorCrediticio;
        this.idCliente = idCliente;
    }

    public int getIdFinanciamiento() { return idFinanciamiento; }
    public void setIdFinanciamiento(int idFinanciamiento) { this.idFinanciamiento = idFinanciamiento; }
    public String getEntidadFinanciera() {  return entidadFinanciera;   }
    public void setEntidadFinanciera(String entidadFinanciera) {    this.entidadFinanciera = entidadFinanciera; }
    public double getCuotaMensual() {   return cuotaMensual;    }
    public void setCuotaMensual(double cuotaMensual) {  this.cuotaMensual = cuotaMensual;   }
    public Date getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(Date fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }
    public int getIdAsesorCrediticio() {    return idAsesorCrediticio;  }
    public void setIdAsesorCrediticio(int idAsesorCrediticio) { this.idAsesorCrediticio = idAsesorCrediticio;   }
    public int getIdCliente() { return idCliente;   }
    public void setIdCliente(int idCliente) {   this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Financiamiento{" + "idFinanciamiento=" + idFinanciamiento + ", entidad='" + entidadFinanciera + "'}";
    }

    public static void insertar(Connection conn, Financiamiento financiamiento) {
        String sql = "INSERT INTO Financiamiento (IDFINANCIAMIENTO, ENTIDADFINANCIERA, CUOTAMENSUAL, FECHAAPROBACION, IDASESORCREDITICIO, IDCLIENTE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, financiamiento.getIdFinanciamiento());
            pstmt.setString(2, financiamiento.getEntidadFinanciera());
            pstmt.setDouble(3, financiamiento.getCuotaMensual());
            pstmt.setDate(4, new java.sql.Date(financiamiento.getFechaAprobacion().getTime()));
            pstmt.setInt(5, financiamiento.getIdAsesorCrediticio());
            pstmt.setInt(6, financiamiento.getIdCliente());
            pstmt.executeUpdate();
            System.out.println("Nuevo financiamiento insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el financiamiento: " + e.getMessage());
        }
    }

    public static Financiamiento buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Financiamiento WHERE IDFINANCIAMIENTO = ?";
        Financiamiento financiamiento = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    financiamiento = new Financiamiento(
                        rs.getInt("IDFINANCIAMIENTO"),
                        rs.getString("ENTIDADFINANCIERA"),
                        rs.getDouble("CUOTAMENSUAL"),
                        rs.getDate("FECHAAPROBACION"),
                        rs.getInt("IDASESORCREDITICIO"),
                        rs.getInt("IDCLIENTE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el financiamiento: " + e.getMessage());
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
                    rs.getInt("IDFINANCIAMIENTO"),
                    rs.getString("ENTIDADFINANCIERA"),
                    rs.getDouble("CUOTAMENSUAL"),
                    rs.getDate("FECHAAPROBACION"),
                    rs.getInt("IDASESORCREDITICIO"),
                    rs.getInt("IDCLIENTE")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los financiamientos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Financiamiento WHERE IDFINANCIAMIENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Financiamiento con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún financiamiento con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el financiamiento: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Financiamiento financiamiento) {
        String sql = "UPDATE Financiamiento SET ENTIDADFINANCIERA = ?, CUOTAMENSUAL = ?, FECHAAPROBACION = ?, IDASESORCREDITICIO = ?, IDCLIENTE = ? WHERE IDFINANCIAMIENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, financiamiento.getEntidadFinanciera());
            pstmt.setDouble(2, financiamiento.getCuotaMensual());
            pstmt.setDate(3, new java.sql.Date(financiamiento.getFechaAprobacion().getTime()));
            pstmt.setInt(4, financiamiento.getIdAsesorCrediticio());
            pstmt.setInt(5, financiamiento.getIdCliente());
            pstmt.setInt(6, financiamiento.getIdFinanciamiento());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Financiamiento actualizado correctamente.");
            } else {
                System.out.println("No se encontró un financiamiento con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el financiamiento: " + e.getMessage());
        }
    }
}

