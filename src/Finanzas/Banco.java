package Finanzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Banco {

    private int idBanco;
    private String nombre;
    private String direccion;
    private String distrito;
    private String ruc;

    public Banco() {
    }

    public Banco(int idBanco, String nombre, String direccion, String distrito, String ruc) {
        this.idBanco = idBanco;
        this.nombre = nombre;
        this.direccion = direccion;
        this.distrito = distrito;
        this.ruc = ruc;
    }

    public int getIdBanco() { return idBanco; }
    public void setIdBanco(int idBanco) { this.idBanco = idBanco; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    @Override
    public String toString() {
        return "Banco{" + "idBanco=" + idBanco + ", nombre='" + nombre + "'}";
    }

    public static void insertar(Connection conn, Banco banco) {
        String sql = "INSERT INTO Banco (IDBANCO, NOMBRE, DIRECCION, DISTRITO, RUC) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, banco.getIdBanco());
            pstmt.setString(2, banco.getNombre());
            pstmt.setString(3, banco.getDireccion());
            pstmt.setString(4, banco.getDistrito());
            pstmt.setString(5, banco.getRuc());
            pstmt.executeUpdate();
            System.out.println("Nuevo banco insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el banco: " + e.getMessage());
        }
    }

    public static Banco buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Banco WHERE IDBANCO = ?";
        Banco banco = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    banco = new Banco(
                        rs.getInt("IDBANCO"),
                        rs.getString("NOMBRE"),
                        rs.getString("DIRECCION"),
                        rs.getString("DISTRITO"),
                        rs.getString("RUC")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el banco: " + e.getMessage());
        }
        return banco;
    }

    public static List<Banco> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Banco ORDER BY IDBANCO";
        List<Banco> listaBancos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Banco banco = new Banco(
                    rs.getInt("IDBANCO"),
                    rs.getString("NOMBRE"),
                    rs.getString("DIRECCION"),
                    rs.getString("DISTRITO"),
                    rs.getString("RUC")
                );
                listaBancos.add(banco);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los bancos: " + e.getMessage());
        }
        return listaBancos;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Banco WHERE IDBANCO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Banco con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún banco con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el banco: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Banco banco) {
        String sql = "UPDATE Banco SET NOMBRE = ?, DIRECCION = ?, DISTRITO = ?, RUC = ? WHERE IDBANCO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, banco.getNombre());
            pstmt.setString(2, banco.getDireccion());
            pstmt.setString(3, banco.getDistrito());
            pstmt.setString(4, banco.getRuc());
            pstmt.setInt(5, banco.getIdBanco());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Banco actualizado correctamente.");
            } else {
                System.out.println("No se encontró un banco con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println(" Error al actualizar el banco: " + e.getMessage());
        }
    }
}
