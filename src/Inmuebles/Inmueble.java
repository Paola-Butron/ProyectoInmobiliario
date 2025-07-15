package Inmuebles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Inmueble {

    private int idInmueble;
    private double area;
    private double precio;
    private String estado;
    private String tipo;
    private int idProyecto;

    public Inmueble(int idInmueble, double area, double precio, String estado, String tipo, int idProyecto) {
        this.idInmueble = idInmueble;
        this.area = area;
        this.precio = precio;
        this.estado = estado;
        this.tipo = tipo;
        this.idProyecto = idProyecto;
    }

    public int getIdInmueble() { return idInmueble; }
    public void setIdInmueble(int idInmueble) { this.idInmueble = idInmueble; }
    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getIdProyecto() { return idProyecto; }
    public void setIdProyecto(int idProyecto) { this.idProyecto = idProyecto; }

    @Override
    public String toString() {
        return "Inmueble{" + "idInmueble=" + idInmueble + ", tipo='" + tipo + "', precio=" + precio + "}";
    }

    public static void insertar(Connection conn, Inmueble inmueble) {
        String sql = "INSERT INTO Inmueble (IDINMUEBLE, AREA, PRECIO, ESTADO, TIPO, IDPROYECTO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, inmueble.getIdInmueble());
            pstmt.setDouble(2, inmueble.getArea());
            pstmt.setDouble(3, inmueble.getPrecio());
            pstmt.setString(4, inmueble.getEstado());
            pstmt.setString(5, inmueble.getTipo());
            pstmt.setInt(6, inmueble.getIdProyecto());
            pstmt.executeUpdate();
            System.out.println("Nuevo inmueble insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el inmueble: " + e.getMessage());
        }
    }

    public static Inmueble buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Inmueble WHERE IDINMUEBLE = ?";
        Inmueble inmueble = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inmueble = new Inmueble(
                        rs.getInt("IDINMUEBLE"),
                        rs.getDouble("AREA"),
                        rs.getDouble("PRECIO"),
                        rs.getString("ESTADO"),
                        rs.getString("TIPO"),
                        rs.getInt("IDPROYECTO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el inmueble: " + e.getMessage());
        }
        return inmueble;
    }

    public static List<Inmueble> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Inmueble ORDER BY IDINMUEBLE";
        List<Inmueble> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Inmueble(
                    rs.getInt("IDINMUEBLE"),
                    rs.getDouble("AREA"),
                    rs.getDouble("PRECIO"),
                    rs.getString("ESTADO"),
                    rs.getString("TIPO"),
                    rs.getInt("IDPROYECTO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los inmuebles: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Inmueble WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Inmueble con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún inmueble con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el inmueble: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Inmueble inmueble) {
        String sql = "UPDATE Inmueble SET AREA = ?, PRECIO = ?, ESTADO = ?, TIPO = ?, IDPROYECTO = ? WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, inmueble.getArea());
            pstmt.setDouble(2, inmueble.getPrecio());
            pstmt.setString(3, inmueble.getEstado());
            pstmt.setString(4, inmueble.getTipo());
            pstmt.setInt(5, inmueble.getIdProyecto());
            pstmt.setInt(6, inmueble.getIdInmueble());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Inmueble actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún inmueble con ID " + inmueble.getIdInmueble() + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el inmueble: " + e.getMessage());
        }
    }
}