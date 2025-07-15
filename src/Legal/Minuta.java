package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Minuta {

    private int idDocumento;
    private String notariaAsociada;
    private double montoTotal;
    private String formaDePago;
    private String constanciaDeAcabados;

    public Minuta(int idDocumento, String notariaAsociada, double montoTotal, String formaDePago, String constanciaDeAcabados) {
        this.idDocumento = idDocumento;
        this.notariaAsociada = notariaAsociada;
        this.montoTotal = montoTotal;
        this.formaDePago = formaDePago;
        this.constanciaDeAcabados = constanciaDeAcabados;
    }

    public int getIdDocumento() { return idDocumento; }
    public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }
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

    public static void insertar(Connection conn, Minuta minuta) {
        String sql = "INSERT INTO Minuta (IDDOCUMENTO, NOTARIAASOCIADA, MONTOTOTAL, FORMADEPAGO, CONSTANCIADEACABADOS) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minuta.getIdDocumento());
            pstmt.setString(2, minuta.getNotariaAsociada());
            pstmt.setDouble(3, minuta.getMontoTotal());
            pstmt.setString(4, minuta.getFormaDePago());
            pstmt.setString(5, minuta.getConstanciaDeAcabados());
            pstmt.executeUpdate();
            System.out.println("Nueva minuta insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la minuta: " + e.getMessage());
        }
    }

    public static Minuta buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Minuta WHERE IDDOCUMENTO = ?";
        Minuta minuta = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    minuta = new Minuta(
                        rs.getInt("IDDOCUMENTO"),
                        rs.getString("NOTARIAASOCIADA"),
                        rs.getDouble("MONTOTOTAL"),
                        rs.getString("FORMADEPAGO"),
                        rs.getString("CONSTANCIADEACABADOS")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la minuta: " + e.getMessage());
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
                    rs.getInt("IDDOCUMENTO"),
                    rs.getString("NOTARIAASOCIADA"),
                    rs.getDouble("MONTOTOTAL"),
                    rs.getString("FORMADEPAGO"),
                    rs.getString("CONSTANCIADEACABADOS")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las minutas: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Minuta WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Minuta con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna minuta con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la minuta: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Minuta minuta) {
        String sql = "UPDATE Minuta SET NOTARIAASOCIADA = ?, MONTOTOTAL = ?, FORMADEPAGO = ?, CONSTANCIADEACABADOS = ? WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, minuta.getNotariaAsociada());
            pstmt.setDouble(2, minuta.getMontoTotal());
            pstmt.setString(3, minuta.getFormaDePago());
            pstmt.setString(4, minuta.getConstanciaDeAcabados());
            pstmt.setInt(5, minuta.getIdDocumento());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Minuta actualizada correctamente.");
            } else {
                System.out.println("No se encontró una minuta con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la minuta: " + e.getMessage());
        }
    }
}