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
 * Representa la entidad Notaria. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Notaria {

    // --- Atributos que coinciden con la tabla NOTARIA ---
    private long idNotaria;
    private String nombre;
    private String ubicacion;
    private String numeroRegistro;
    private String notarioPrincipal;

    // --- Constructor ---
    // Único constructor que exige todos los datos para crear un objeto válido.
    public Notaria(long idNotaria, String nombre, String ubicacion, String numeroRegistro, String notarioPrincipal) {
        this.idNotaria = idNotaria;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.numeroRegistro = numeroRegistro;
        this.notarioPrincipal = notarioPrincipal;
    }

    // --- Getters y Setters ---
    public long getIdNotaria() { return idNotaria; }
    public void setIdNotaria(long idNotaria) { this.idNotaria = idNotaria; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    public String getNotarioPrincipal() { return notarioPrincipal; }
    public void setNotarioPrincipal(String notarioPrincipal) { this.notarioPrincipal = notarioPrincipal; }

    @Override
    public String toString() {
        return "Notaria{" + "idNotaria=" + idNotaria + ", nombre='" + nombre + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Notaria notaria) {
        String sql = "INSERT INTO Notaria (IDNOTARIA, NOMBRE, UBICACION, NUMEROREGISTRO, NOTARIOPRINCIPAL) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, notaria.getIdNotaria());
            pstmt.setString(2, notaria.getNombre());
            pstmt.setString(3, notaria.getUbicacion());
            pstmt.setString(4, notaria.getNumeroRegistro());
            pstmt.setString(5, notaria.getNotarioPrincipal());
            pstmt.executeUpdate();
            System.out.println("✅ Nueva notaría insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar la notaría: " + e.getMessage());
        }
    }

    public static Notaria buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Notaria WHERE IDNOTARIA = ?";
        Notaria notaria = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    notaria = new Notaria(
                        rs.getLong("IDNOTARIA"),
                        rs.getString("NOMBRE"),
                        rs.getString("UBICACION"),
                        rs.getString("NUMEROREGISTRO"),
                        rs.getString("NOTARIOPRINCIPAL")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar la notaría: " + e.getMessage());
        }
        return notaria;
    }

    public static List<Notaria> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Notaria ORDER BY IDNOTARIA";
        List<Notaria> listaNotarias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaNotarias.add(new Notaria(
                    rs.getLong("IDNOTARIA"),
                    rs.getString("NOMBRE"),
                    rs.getString("UBICACION"),
                    rs.getString("NUMEROREGISTRO"),
                    rs.getString("NOTARIOPRINCIPAL")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todas las notarías: " + e.getMessage());
        }
        return listaNotarias;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Notaria WHERE IDNOTARIA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Notaría con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ninguna notaría con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar la notaría: " + e.getMessage());
        }
    }
}
