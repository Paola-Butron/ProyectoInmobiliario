package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {

    private int idVenta;
    private Date fechaDeVenta;
    private double montoTotal;
    private String estadoVenta;
    private int idCliente;

    public Venta(int idVenta, Date fechaDeVenta, double montoTotal, String estadoVenta, int idCliente) {
        this.idVenta = idVenta;
        this.fechaDeVenta = fechaDeVenta;
        this.montoTotal = montoTotal;
        this.estadoVenta = estadoVenta;
        this.idCliente = idCliente;
    }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public Date getFechaDeVenta() { return fechaDeVenta; }
    public void setFechaDeVenta(Date fechaDeVenta) { this.fechaDeVenta = fechaDeVenta; }
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
    public String getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(String estadoVenta) { this.estadoVenta = estadoVenta; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Venta{" + "idVenta=" + idVenta + ", fechaDeVenta=" + fechaDeVenta + ", montoTotal=" + montoTotal + ", estadoVenta='" + estadoVenta + "'}";
    }

    public static void insertar(Connection conn, Venta nuevaVenta) {
        String sql = "INSERT INTO Venta (IDVENTA, FECHADEVENTA, MONTOTOTAL, ESTADOVENTA, IDCLIENTE) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaVenta.getIdVenta());
            pstmt.setDate(2, new java.sql.Date(nuevaVenta.getFechaDeVenta().getTime()));
            pstmt.setDouble(3, nuevaVenta.getMontoTotal());
            pstmt.setString(4, nuevaVenta.getEstadoVenta());
            pstmt.setInt(5, nuevaVenta.getIdCliente());
            pstmt.executeUpdate();
            System.out.println("Nueva venta insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la venta: " + e.getMessage());
        }
    }

    public static Venta buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Venta WHERE IDVENTA = ?";
        Venta venta = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    venta = new Venta(
                        rs.getInt("IDVENTA"),
                        rs.getDate("FECHADEVENTA"),
                        rs.getDouble("MONTOTOTAL"),
                        rs.getString("ESTADOVENTA"),
                        rs.getInt("IDCLIENTE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la venta: " + e.getMessage());
        }
        return venta;
    }

    public static List<Venta> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Venta ORDER BY IDVENTA";
        List<Venta> listaVentas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venta venta = new Venta(
                    rs.getInt("IDVENTA"),
                    rs.getDate("FECHADEVENTA"),
                    rs.getDouble("MONTOTOTAL"),
                    rs.getString("ESTADOVENTA"),
                    rs.getInt("IDCLIENTE")
                );
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las ventas: " + e.getMessage());
        }
        return listaVentas;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Venta WHERE IDVENTA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Venta con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna venta con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la venta: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Venta ventaActualizada) {
        String sql = "UPDATE Venta SET FECHADEVENTA = ?, MONTOTOTAL = ?, ESTADOVENTA = ?, IDCLIENTE = ? WHERE IDVENTA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(ventaActualizada.getFechaDeVenta().getTime()));
            pstmt.setDouble(2, ventaActualizada.getMontoTotal());
            pstmt.setString(3, ventaActualizada.getEstadoVenta());
            pstmt.setInt(4, ventaActualizada.getIdCliente());
            pstmt.setInt(5, ventaActualizada.getIdVenta());
            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Venta actualizada correctamente.");
            } else {
                System.out.println("No se encontró una venta con ese ID para actualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la venta: " + e.getMessage());
        }
    }
}
