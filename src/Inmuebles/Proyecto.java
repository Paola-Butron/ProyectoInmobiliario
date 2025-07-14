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
import java.util.Date;
import java.util.List;

/**
 * Representa la entidad Proyecto. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Proyecto {

    // --- Atributos que coinciden con la tabla PROYECTO ---
    private long idProyecto;
    private String nombre;
    private String ubicacion;
    private Date fechaInicio;
    private Date fechaEntrega;
    private String estado;
    private long idConstructora; // Clave Foránea

    // --- Constructor ---
    public Proyecto(long idProyecto, String nombre, String ubicacion, Date fechaInicio, Date fechaEntrega, String estado, long idConstructora) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.idConstructora = idConstructora;
    }

    // --- Getters y Setters ---
    public long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(long idProyecto) { this.idProyecto = idProyecto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(Date fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public long getIdConstructora() { return idConstructora; }
    public void setIdConstructora(long idConstructora) { this.idConstructora = idConstructora; }

    @Override
    public String toString() {
        return "Proyecto{" + "idProyecto=" + idProyecto + ", nombre='" + nombre + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Proyecto proyecto) {
        String sql = "INSERT INTO Proyecto (IDPROYECTO, NOMBRE, UBICACION, FECHAINICIO, FECHAENTREGA, ESTADO, IDCONSTRUCTORA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, proyecto.getIdProyecto());
            pstmt.setString(2, proyecto.getNombre());
            pstmt.setString(3, proyecto.getUbicacion());
            pstmt.setDate(4, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            pstmt.setDate(5, new java.sql.Date(proyecto.getFechaEntrega().getTime()));
            pstmt.setString(6, proyecto.getEstado());
            pstmt.setLong(7, proyecto.getIdConstructora());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo proyecto insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el proyecto: " + e.getMessage());
        }
    }

    public static Proyecto buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Proyecto WHERE IDPROYECTO = ?";
        Proyecto proyecto = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    proyecto = new Proyecto(
                        rs.getLong("IDPROYECTO"),
                        rs.getString("NOMBRE"),
                        rs.getString("UBICACION"),
                        rs.getDate("FECHAINICIO"),
                        rs.getDate("FECHAENTREGA"),
                        rs.getString("ESTADO"),
                        rs.getLong("IDCONSTRUCTORA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el proyecto: " + e.getMessage());
        }
        return proyecto;
    }

    public static List<Proyecto> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Proyecto ORDER BY IDPROYECTO";
        List<Proyecto> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Proyecto(
                    rs.getLong("IDPROYECTO"),
                    rs.getString("NOMBRE"),
                    rs.getString("UBICACION"),
                    rs.getDate("FECHAINICIO"),
                    rs.getDate("FECHAENTREGA"),
                    rs.getString("ESTADO"),
                    rs.getLong("IDCONSTRUCTORA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los proyectos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Proyecto WHERE IDPROYECTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Proyecto con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún proyecto con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el proyecto: " + e.getMessage());
        }
    }
}






