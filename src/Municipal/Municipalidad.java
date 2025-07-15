package Municipal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Municipalidad {

    private int idMunicipalidad;
    private String nombre;
    private String distrito;
    private String direccion;
    private String areaEncargada;
    private String serviciosRelacionados;

    public Municipalidad(int idMunicipalidad, String nombre, String distrito, String direccion, String areaEncargada, String serviciosRelacionados) {
        this.idMunicipalidad = idMunicipalidad;
        this.nombre = nombre;
        this.distrito = distrito;
        this.direccion = direccion;
        this.areaEncargada = areaEncargada;
        this.serviciosRelacionados = serviciosRelacionados;
    }

    public int getIdMunicipalidad() { return idMunicipalidad; }
    public void setIdMunicipalidad(int idMunicipalidad) { this.idMunicipalidad = idMunicipalidad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getAreaEncargada() { return areaEncargada; }
    public void setAreaEncargada(String areaEncargada) { this.areaEncargada = areaEncargada; }
    public String getServiciosRelacionados() { return serviciosRelacionados; }
    public void setServiciosRelacionados(String serviciosRelacionados) { this.serviciosRelacionados = serviciosRelacionados; }

    @Override
    public String toString() {
        return "Municipalidad{" + "idMunicipalidad=" + idMunicipalidad + ", nombre='" + nombre + "'}";
    }

    public static void insertar(Connection conn, Municipalidad municipalidad) {
        String sql = "INSERT INTO Municipalidad (IDMUNICIPALIDAD, NOMBRE, DISTRITO, DIRECCION, AREAENCARGADA, SERVICIOSRELACIONADOS) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, municipalidad.getIdMunicipalidad());
            pstmt.setString(2, municipalidad.getNombre());
            pstmt.setString(3, municipalidad.getDistrito());
            pstmt.setString(4, municipalidad.getDireccion());
            pstmt.setString(5, municipalidad.getAreaEncargada());
            pstmt.setString(6, municipalidad.getServiciosRelacionados());
            pstmt.executeUpdate();
            System.out.println("Nueva municipalidad insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la municipalidad: " + e.getMessage());
        }
    }

    public static Municipalidad buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Municipalidad WHERE IDMUNICIPALIDAD = ?";
        Municipalidad municipalidad = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    municipalidad = new Municipalidad(
                        rs.getInt("IDMUNICIPALIDAD"),
                        rs.getString("NOMBRE"),
                        rs.getString("DISTRITO"),
                        rs.getString("DIRECCION"),
                        rs.getString("AREAENCARGADA"),
                        rs.getString("SERVICIOSRELACIONADOS")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la municipalidad: " + e.getMessage());
        }
        return municipalidad;
    }

    public static List<Municipalidad> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Municipalidad ORDER BY IDMUNICIPALIDAD";
        List<Municipalidad> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Municipalidad(
                    rs.getInt("IDMUNICIPALIDAD"),
                    rs.getString("NOMBRE"),
                    rs.getString("DISTRITO"),
                    rs.getString("DIRECCION"),
                    rs.getString("AREAENCARGADA"),
                    rs.getString("SERVICIOSRELACIONADOS")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las municipalidades: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Municipalidad WHERE IDMUNICIPALIDAD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Municipalidad con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ninguna municipalidad con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar la municipalidad: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Municipalidad municipalidad) {
        String sql = "UPDATE Municipalidad SET NOMBRE = ?, DISTRITO = ?, DIRECCION = ?, AREAENCARGADA = ?, SERVICIOSRELACIONADOS = ? WHERE IDMUNICIPALIDAD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, municipalidad.getNombre());
            pstmt.setString(2, municipalidad.getDistrito());
            pstmt.setString(3, municipalidad.getDireccion());
            pstmt.setString(4, municipalidad.getAreaEncargada());
            pstmt.setString(5, municipalidad.getServiciosRelacionados());
            pstmt.setInt(6, municipalidad.getIdMunicipalidad());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Municipalidad actualizada correctamente.");
            } else {
                System.out.println("No se encontró una municipalidad con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la municipalidad: " + e.getMessage());
        }
    }
}