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

/**
 * Representa la entidad Inmuebles. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Inmueble {

    // --- Atributos que coinciden con la tabla INMUEBLE ---
    private long idInmueble;
    private double area;
    private double precio;
    private String estado;
    private String tipo;
    private long idProyecto; // Clave Foránea

    // --- Constructor ---
    public Inmueble(long idInmueble, double area, double precio, String estado, String tipo, long idProyecto) {
        this.idInmueble = idInmueble;
        this.area = area;
        this.precio = precio;
        this.estado = estado;
        this.tipo = tipo;
        this.idProyecto = idProyecto;
    }

    // --- Getters y Setters ---
    public long getIdInmuebles() { return idInmueble; }
    public void setIdInmuebles(long idInmuebles) { this.idInmueble = idInmuebles; }
    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(long idProyecto) { this.idProyecto = idProyecto; }

    @Override
    public String toString() {
        return "Inmueble{" + "idInmueble=" + idInmueble + ", tipo='" + tipo + ", precio=" + precio + "'}";
    }

    public static void insertar(Connection conn, Inmueble inmueble) {
        String sql = "INSERT INTO Inmueble (IDINMUEBLE, AREA, PRECIO, ESTADO, TIPO, IDPROYECTO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, inmueble.getIdInmuebles());
            pstmt.setDouble(2, inmueble.getArea());
            pstmt.setDouble(3, inmueble.getPrecio());
            pstmt.setString(4, inmueble.getEstado());
            pstmt.setString(5, inmueble.getTipo());
            pstmt.setLong(6, inmueble.getIdProyecto());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo inmueble insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el inmueble: " + e.getMessage());
        }
    }

    public static Inmueble buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Inmueble WHERE IDINMUEBLE = ?";
        Inmueble inmueble = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inmueble = new Inmueble(
                        rs.getLong("IDINMUEBLE"),
                        rs.getDouble("AREA"),
                        rs.getDouble("PRECIO"),
                        rs.getString("ESTADO"),
                        rs.getString("TIPO"),
                        rs.getLong("IDPROYECTO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el inmueble: " + e.getMessage());
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
                    rs.getLong("IDINMUEBLE"),
                    rs.getDouble("AREA"),
                    rs.getDouble("PRECIO"),
                    rs.getString("ESTADO"),
                    rs.getString("TIPO"),
                    rs.getLong("IDPROYECTO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los inmuebles: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Inmueble WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Inmueble con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún inmueble con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el inmueble: " + e.getMessage());
        }
    }
    
    public static void actualizar(Connection conn, Inmueble inmuebleActualizado) {
    String sql = "UPDATE Inmueble SET AREA = ?, PRECIO = ?, ESTADO = ?, TIPO = ?, IDPROYECTO = ? WHERE IDINMUEBLE = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setDouble(1, inmuebleActualizado.getArea());
        pstmt.setDouble(2, inmuebleActualizado.getPrecio());
        pstmt.setString(3, inmuebleActualizado.getEstado());
        pstmt.setString(4, inmuebleActualizado.getTipo());
        pstmt.setLong(5, inmuebleActualizado.getIdProyecto());
        pstmt.setLong(6, inmuebleActualizado.getIdInmuebles()); 

        int filasAfectadas = pstmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("✅ Inmueble actualizado correctamente.");
        } else {
            System.out.println("ℹ️ No se encontró ningún inmueble con ID " + inmuebleActualizado.getIdInmuebles() + ".");
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar el inmueble: " + e.getMessage());
    }
}
}






