package Inmuebles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proyecto {

    private int idProyecto;
    private String nombre;
    private String ubicacion;
    private Date fechaInicio;
    private Date fechaEntrega;
    private String estado;
    private int idConstructora;

    public Proyecto(int idProyecto, String nombre, String ubicacion, Date fechaInicio, Date fechaEntrega, String estado, int idConstructora) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.idConstructora = idConstructora;
    }

    public int getIdProyecto() { return idProyecto; }
    public void setIdProyecto(int idProyecto) { this.idProyecto = idProyecto; }
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
    public int getIdConstructora() { return idConstructora; }
    public void setIdConstructora(int idConstructora) { this.idConstructora = idConstructora; }

    @Override
    public String toString() {
        return "Proyecto{" + "idProyecto=" + idProyecto + ", nombre='" + nombre + "'}";
    }

    public static void insertar(Connection conn, Proyecto proyecto) {
        String sql = "INSERT INTO Proyecto (IDPROYECTO, NOMBRE, UBICACION, FECHAINICIO, FECHAENTREGA, ESTADO, IDCONSTRUCTORA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, proyecto.getIdProyecto());
            pstmt.setString(2, proyecto.getNombre());
            pstmt.setString(3, proyecto.getUbicacion());
            pstmt.setDate(4, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            pstmt.setDate(5, new java.sql.Date(proyecto.getFechaEntrega().getTime()));
            pstmt.setString(6, proyecto.getEstado());
            pstmt.setInt(7, proyecto.getIdConstructora());
            pstmt.executeUpdate();
            System.out.println("Nuevo proyecto insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el proyecto: " + e.getMessage());
        }
    }

    public static Proyecto buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Proyecto WHERE IDPROYECTO = ?";
        Proyecto proyecto = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    proyecto = new Proyecto(
                        rs.getInt("IDPROYECTO"),
                        rs.getString("NOMBRE"),
                        rs.getString("UBICACION"),
                        rs.getDate("FECHAINICIO"),
                        rs.getDate("FECHAENTREGA"),
                        rs.getString("ESTADO"),
                        rs.getInt("IDCONSTRUCTORA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el proyecto: " + e.getMessage());
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
                    rs.getInt("IDPROYECTO"),
                    rs.getString("NOMBRE"),
                    rs.getString("UBICACION"),
                    rs.getDate("FECHAINICIO"),
                    rs.getDate("FECHAENTREGA"),
                    rs.getString("ESTADO"),
                    rs.getInt("IDCONSTRUCTORA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los proyectos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Proyecto WHERE IDPROYECTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Proyecto con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún proyecto con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el proyecto: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Proyecto proyecto) {
        String sql = "UPDATE Proyecto SET NOMBRE = ?, UBICACION = ?, FECHAINICIO = ?, FECHAENTREGA = ?, ESTADO = ?, IDCONSTRUCTORA = ? WHERE IDPROYECTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getUbicacion());
            pstmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            pstmt.setDate(4, new java.sql.Date(proyecto.getFechaEntrega().getTime()));
            pstmt.setString(5, proyecto.getEstado());
            pstmt.setInt(6, proyecto.getIdConstructora());
            pstmt.setInt(7, proyecto.getIdProyecto());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Proyecto actualizado correctamente.");
            } else {
                System.out.println("No se encontró un proyecto con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el proyecto: " + e.getMessage());
        }
    }
}