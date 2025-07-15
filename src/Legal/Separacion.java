package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Separacion {

    private int idDocumento; 
    private double montoSeparacion;
    private Date fechaPago;
    private String formaDePago;
    private String constanciaDeAcabados;

    public Separacion(int idDocumento, double montoSeparacion, Date fechaPago, String formaDePago, String constanciaDeAcabados) {
        this.idDocumento = idDocumento;
        this.montoSeparacion = montoSeparacion;
        this.fechaPago = fechaPago;
        this.formaDePago = formaDePago;
        this.constanciaDeAcabados = constanciaDeAcabados;
    }

    public int getIdDocumento() { return idDocumento; }
    public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }
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

    public static void insertar(Connection conn, Separacion separacion) {
        String sql = "INSERT INTO Separacion (IDDOCUMENTO, MONTOSEPARACION, FECHAPAGO, FORMADEPAGO, CONSTANCIADEACABADOS) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, separacion.getIdDocumento());
            pstmt.setDouble(2, separacion.getMontoSeparacion());
            pstmt.setDate(3, new java.sql.Date(separacion.getFechaPago().getTime()));
            pstmt.setString(4, separacion.getFormaDePago());
            pstmt.setString(5, separacion.getConstanciaDeAcabados());
            pstmt.executeUpdate();
            System.out.println("Nueva separación insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la separación: " + e.getMessage());
        }
    }

    public static Separacion buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Separacion WHERE IDDOCUMENTO = ?";
        Separacion separacion = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    separacion = new Separacion(
                        rs.getInt("IDDOCUMENTO"),
                        rs.getDouble("MONTOSEPARACION"),
                        rs.getDate("FECHAPAGO"),
                        rs.getString("FORMADEPAGO"),
                        rs.getString("CONSTANCIADEACABADOS")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la separación: " + e.getMessage());
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
                    rs.getInt("IDDOCUMENTO"),
                    rs.getDouble("MONTOSEPARACION"),
                    rs.getDate("FECHAPAGO"),
                    rs.getString("FORMADEPAGO"),
                    rs.getString("CONSTANCIADEACABADOS")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las separaciones: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Separacion WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Separación con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna separación con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la separación: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Separacion s) {
        String sql = "UPDATE Separacion SET MONTOSEPARACION = ?, FECHAPAGO = ?, FORMADEPAGO = ?, CONSTANCIADEACABADOS = ? WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, s.getMontoSeparacion());
            pstmt.setDate(2, new java.sql.Date(s.getFechaPago().getTime()));
            pstmt.setString(3, s.getFormaDePago());
            pstmt.setString(4, s.getConstanciaDeAcabados());
            pstmt.setInt(5, s.getIdDocumento());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Separación actualizada correctamente.");
            } else {
                System.out.println("No se encontró una separación con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la separación: " + e.getMessage());
        }
    }
}